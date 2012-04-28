package com.chronosystems.service.rest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.httpclient.HttpStatus;

import com.chronosystems.entity.Device;
import com.chronosystems.entity.Location;
import com.chronosystems.entity.util.EntityUtils;
import com.chronosystems.model.service.DeviceService;

//http://localhost:8080/android/rest/location/hello%20world
@Path("/location")
public class DeviceRestService {

	@GET
	@Path("/{param}")
	public Response printMessage(@PathParam("param") String msg) {
		String result = "Device Rest Service teste: " + msg;
		return Response.ok().entity(result).build();
	}

	@GET
	@Path("/search/device/{login}")
	@Produces(MediaType.TEXT_XML)
	public Response deviceSearch(@PathParam("login") String login) {
		final Device entity = new DeviceService().find(login);//search
		if (entity != null) {
			return Response.ok(EntityUtils.toXml(entity)).build();
		}
		//notFound
		return Response.status(HttpStatus.SC_NO_CONTENT).build();
	}
	
	@GET
	@Path("/search/json/{login}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response jsonSearch(@PathParam("login") String login) {
		final Device entity = new DeviceService().find(login);//search
		return Response.ok(entity).build();
	}

	@POST
	@Path("/device/save")
	@Produces(MediaType.TEXT_XML)
	public Response save(final String xml) {
		try {
			final Device entity = EntityUtils.getDevice(xml);//convert to bean
			final Device device = new DeviceService().find(entity.getLogin());//verify if exists
			if (device != null) {
				entity.setId(device.getId());//update id
				for (final Location location : entity.getLocations()) {
					location.setDevice(entity);//update references
				}
			}

			new DeviceService().save(entity);//save
			return Response.ok(EntityUtils.toXml(entity.getLocations().get(0))).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//error
		return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
	}
}