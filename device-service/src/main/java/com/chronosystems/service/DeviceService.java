package com.chronosystems.service;

import java.util.List;

import com.chronosystems.form.Contact;


public interface DeviceService {
	
	public void addContact(Contact contact);
	public List<Contact> listContact();
	public void removeContact(Integer id);
}
