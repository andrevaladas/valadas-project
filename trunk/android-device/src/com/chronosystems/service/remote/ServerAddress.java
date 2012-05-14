package com.chronosystems.service.remote;

public enum ServerAddress {
	//http://andrevaladas.appspot.com/rest/device/get/andrevaladas
	LOCAL("http://192.168.0.101:8888/android-service/rest"),
	//LOCAL("http://10.0.2.2:8888/android-service/rest"),
	//REMOTE("http://andrevaladas.appspot.com/rest");
	//REMOTE("http://192.168.0.101:8888/android-service/rest");
	REMOTE("http://chronosystems.jelastic.servint.net/android-service/rest");

	private String url;

	private ServerAddress(final String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}
}
