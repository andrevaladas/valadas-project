package com.chronosystems.ws.rest;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.chronosystems.entity.Entity;
import com.chronosystems.entity.Relationship;
import com.chronosystems.entity.util.XMLParser;
import com.chronosystems.model.service.RelationshipService;

@Path("/relationship")
public class WSRelationship {

	@POST
	@Path("/follow")
	@Produces(MediaType.TEXT_XML)
	public Response follow(final String xml) {
		final Relationship follow = XMLParser.parseXML(xml, Relationship.class);
		new RelationshipService().save(follow);
		return Response.ok(XMLParser.parseXML(new Entity())).build();
	}
}