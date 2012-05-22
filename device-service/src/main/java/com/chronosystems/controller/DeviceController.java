package com.chronosystems.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chronosystems.entity.Device;
import com.chronosystems.entity.Entity;
import com.chronosystems.entity.util.XMLParser;
import com.chronosystems.service.DeviceService;

@Controller
@RequestMapping("/device")
public class DeviceController {

	@Autowired
	private DeviceService deviceService;

	@RequestMapping(value="/get/{message}", method = RequestMethod.GET)
	public @ResponseBody String get(@PathVariable final String message) {
		return "Spring MVC "+message;
	}

	@RequestMapping(value="/login", method = RequestMethod.POST)
	public @ResponseBody String login(@RequestBody final String xml) {
		final Device filter = XMLParser.parseXML(xml, Device.class);
		final Device device = deviceService.find(filter.getEmail(), filter.getPassword());
		if(device == null) {
			return XMLParser.parseXML(new Entity());
		}
		return XMLParser.parseXML(new Entity(device));
	}

	@RequestMapping(value="/register", method = RequestMethod.POST)
	public @ResponseBody String register(@RequestBody final String xml) {
		final Device entity = XMLParser.parseXML(xml, Device.class);
		final Long rowCount = deviceService.rowCount(entity.getEmail());
		if (rowCount == 0) {
			deviceService.save(entity);//save
			entity.setDatecreated(new Date());
			return XMLParser.parseXML(new Entity(entity));
		}
		//conflict
		return XMLParser.parseXML(new Entity());
	}

	@RequestMapping(value="/search", method = RequestMethod.POST)
	public @ResponseBody String search(@RequestBody final String xml) {
		final Entity entity = XMLParser.parseXML(xml, Entity.class);
		final Entity result = deviceService.search(entity);
		return XMLParser.parseXML(result);
	}

	@RequestMapping(value="/findFollowing", method = RequestMethod.POST)
	public @ResponseBody String findFollowing(@RequestBody final String xml) {
		final Entity entity = XMLParser.parseXML(xml, Entity.class);
		final Entity result = deviceService.findFollowing(entity);
		return XMLParser.parseXML(result);
	}
}
