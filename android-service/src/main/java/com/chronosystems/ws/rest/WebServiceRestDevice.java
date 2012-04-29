package com.chronosystems.ws.rest;

import java.util.Date;

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
import com.chronosystems.entity.util.EntityUtils;
import com.chronosystems.model.service.DeviceService;

@Path("/device")
public class WebServiceRestDevice {

	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(final String xml) {
		final Device entity = EntityUtils.getDevice(xml);//convert to bean
		final Device device = new DeviceService().find(entity.getEmail(), entity.getPassword());
		if(device != null) {
			return Response.ok(new Entity(device)).build();
		}
		return Response.noContent().build();
	}

	@GET
	@Path("/search/{email}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response jsonSearch(@PathParam("email") String email) {
		final Device device = new DeviceService().find(email);//search
		return Response.ok(new Entity(device)).build();
	}

	@POST
	@Path("/register")
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(final String xml) {
		final Device entity = EntityUtils.getDevice(xml);//convert to bean
		final Long rowCount = new DeviceService().rowCount(entity.getEmail());
		if (rowCount == 0) {
			new DeviceService().save(entity);//save
			entity.setDatecreated(new Date());
			return Response.ok(new Entity(entity)).build();
		}
		//conflict
		return Response.status(HttpStatus.SC_CONFLICT).build();
	}
}