package com.chronosystems.ws.rest;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.chronosystems.entity.Device;
import com.chronosystems.entity.Entity;
import com.chronosystems.entity.Location;
import com.chronosystems.entity.util.XMLParser;
import com.chronosystems.model.service.DeviceService;

@Path("/location")
public class WSLocation {

	@POST
	@Path("/checkin")
	@Produces(MediaType.TEXT_XML)
	public Response login(final String xml) {
		final Device filter = XMLParser.parseXML(xml, Device.class);
		final Device device = new DeviceService().find(filter.getId());

		final Location checkin = filter.getLocations().get(0);
		checkin.setTimeline(checkin.getTimeline());//update from xml
		checkin.setDevice(device);
		device.addLocation(checkin);
		new DeviceService().save(device);

		return Response.ok(XMLParser.parseXML(new Entity(device))).build();
	}
}