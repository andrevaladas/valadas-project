package com.chronosystems.controller;

import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chronosystems.form.Contact;
import com.chronosystems.service.DeviceService;

@Controller
@RequestMapping("/device")
public class DeviceController {

	@Autowired
	private DeviceService deviceService;

	@RequestMapping(value="/get/{name}", method = RequestMethod.GET)
	public @ResponseBody String getMovie(@PathVariable final String name) {
		return "Spring MVC "+name;
	}

	@RequestMapping("/index")
	public String listContacts(final Map<String, Object> map) {

		map.put("contact", new Contact());
		map.put("contactList", deviceService.listContact());

		return "contact";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addContact(@ModelAttribute("contact") final
			Contact contact, final BindingResult result) {

		deviceService.addContact(contact);

		return "redirect:/index";
	}

	@RequestMapping("/delete/{contactId}")
	public String deleteContact(@PathVariable("contactId") final
			Integer contactId) {

		deviceService.removeContact(contactId);

		return "redirect:/index";
	}
}
