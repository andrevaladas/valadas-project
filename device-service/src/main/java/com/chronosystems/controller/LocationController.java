package com.chronosystems.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chronosystems.entity.Device;
import com.chronosystems.entity.Entity;
import com.chronosystems.entity.Location;
import com.chronosystems.entity.util.XMLParser;
import com.chronosystems.service.DeviceService;
import com.chronosystems.service.LocationService;

@Controller
@RequestMapping("/location")
public class LocationController {

	@Autowired
	private DeviceService deviceService;

	@Autowired
	private LocationService locationService;

	@RequestMapping(value="/checkin", method = RequestMethod.POST)
	public @ResponseBody String checkin(@RequestBody final String xml) {
		final Device filter = XMLParser.parseXML(xml, Device.class);
		final Device device = deviceService.find(filter.getId());

		final Location locationChechin = filter.getLocations().get(0);
		locationChechin.setTimeline(locationChechin.getTimeline());//update from xml
		locationChechin.setDevice(device);
		locationService.save(locationChechin);

		return XMLParser.parseXML(new Entity());
	}

	@RequestMapping(value="/findLastLocations", method = RequestMethod.POST)
	public @ResponseBody String findLastLocations(@RequestBody final String xml) {
		final Device filter = XMLParser.parseXML(xml, Device.class);
		final List<Location> locations = locationService.findLastLocations(filter.getId());
		filter.setLocations(locations);
		return XMLParser.parseXML(new Entity(filter));
	}
}