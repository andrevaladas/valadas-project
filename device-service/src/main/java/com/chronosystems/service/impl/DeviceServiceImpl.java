package com.chronosystems.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chronosystems.dao.DeviceDAO;
import com.chronosystems.form.Contact;
import com.chronosystems.service.DeviceService;


@Service
public class DeviceServiceImpl implements DeviceService {

	@Autowired
	private DeviceDAO deviceDAO;
	
	@Transactional
	public void addContact(Contact contact) {
		deviceDAO.addContact(contact);
	}

	@Transactional
	public List<Contact> listContact() {

		return deviceDAO.listContact();
	}

	@Transactional
	public void removeContact(Integer id) {
		deviceDAO.removeContact(id);
	}
}
