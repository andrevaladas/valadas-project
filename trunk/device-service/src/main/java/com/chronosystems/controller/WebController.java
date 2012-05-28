package com.chronosystems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chronosystems.service.LocationService;

@Controller
@RequestMapping
public class WebController {

	@Autowired
	private LocationService locationService;

	@RequestMapping("/map/{id}")
	public String map(@PathVariable("id") final Long id, final ModelMap model) {
		model.addAttribute("locationList", locationService.findLastLocations(id));
		return "map";
	}
}
