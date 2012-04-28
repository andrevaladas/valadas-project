package com.chronosystems.service.app;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.chronosystems.service.rest.DeviceRestService;

public class DeviceApplication extends Application {
	private Set<Object> singletons = new HashSet<Object>();

	public DeviceApplication() {
		singletons.add(new DeviceRestService());
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}

	@Override
	public Set<Class<?>> getClasses() {
		// TODO Auto-generated method stub
		return null;
	}
}
