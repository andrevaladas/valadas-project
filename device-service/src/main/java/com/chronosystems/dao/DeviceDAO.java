package com.chronosystems.dao;

import java.util.List;

import com.chronosystems.form.Contact;


public interface DeviceDAO {
	
	public void addContact(Contact contact);
	public List<Contact> listContact();
	public void removeContact(Integer id);
}
