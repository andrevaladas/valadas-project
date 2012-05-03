package com.chronosystems.ws.rest;

import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.httpclient.HttpStatus;

import com.chronosystems.entity.Device;
import com.chronosystems.entity.Entity;
import com.chronosystems.entity.util.XMLParser;
import com.chronosystems.model.service.DeviceService;

@Path("/device")
public class WSDevice {

	@POST
	@Path("/login")
	@Produces(MediaType.TEXT_XML)
	public Response login(final String xml) {
		final Device filter = XMLParser.parseXML(xml, Device.class);//convert to bean
		final Device device = new DeviceService().find(filter.getEmail(), filter.getPassword());
		if(device != null) {
			return Response.ok(XMLParser.parseXML(new Entity(device))).build();
		}
		return Response.noContent().build();
	}

	@POST
	@Path("/register")
	@Produces(MediaType.TEXT_XML)
	public Response register(final String xml) {
		final Device entity = XMLParser.parseXML(xml, Device.class);//convert to bean
		final Long rowCount = new DeviceService().rowCount(entity.getEmail());
		if (rowCount == 0) {
			new DeviceService().save(entity);//save
			entity.setDatecreated(new Date());
			return Response.ok(XMLParser.parseXML(new Entity(entity))).build();
		}
		//conflict
		return Response.status(HttpStatus.SC_CONFLICT).build();
	}

	@GET
	@Path("/search/{email}")
	@Produces(MediaType.TEXT_XML)
	public Response search(@PathParam("email") final String email) {
		final Device device = new DeviceService().find(email);//search
		return Response.ok(XMLParser.parseXML(new Entity(device))).build();
	}

	@POST
	@Path("/findAll")
	@Produces(MediaType.TEXT_XML)
	public Response findAll() {
		final List<Device> list = new DeviceService().findAll();
		final Entity entity = new Entity();
		entity.setDevices(list);
		return Response.ok(XMLParser.parseXML(entity)).build();
	}
}