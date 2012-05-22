package com.chronosystems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chronosystems.entity.Entity;
import com.chronosystems.entity.Relationship;
import com.chronosystems.entity.util.XMLParser;
import com.chronosystems.service.RelationshipService;

@Controller
@RequestMapping("/relationship")
public class RelationshipController {

	@Autowired
	private RelationshipService relationshipService;

	@RequestMapping(value="/follow", method = RequestMethod.POST)
	public @ResponseBody String follow(@RequestBody final String xml) {
		final Relationship follow = XMLParser.parseXML(xml, Relationship.class);
		relationshipService.save(follow);
		return XMLParser.parseXML(new Entity());
	}
}