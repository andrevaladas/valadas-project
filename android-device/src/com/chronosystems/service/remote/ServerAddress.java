package com.chronosystems.service.remote;

public enum ServerAddress {
	//REMOTE("http://10.0.2.2:8888/android-service/rest");
	//REMOTE("http://192.168.0.101:8888/android-service/rest");
	REMOTE("http://10.0.2.2:8888/android-spring");
	//REMOTE("http://chronosystems.jelastic.servint.net/android-service/rest");

	private String url;

	private ServerAddress(final String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}
}
