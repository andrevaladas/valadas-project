package com.chronosystems;

import java.util.Date;

import javax.ws.rs.core.MediaType;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;

import com.chronosystems.entity.Device;
import com.chronosystems.entity.Location;
import com.chronosystems.entity.util.EntityUtils;

public class RestServiceApp {

	private static Device getDefaultEntity() {
		final Device entity = new Device();
        entity.setLogin("andrevaladas");
        entity.setName("André Valadas");

        final Location location = new Location();
		location.setDevice(entity);
		location.setLatitude(-(Math.random()*100));
		location.setLongitude(-Math.random()*100);
		location.setTimeline(new Date());
		entity.getLocations().add(location);
        return entity;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PostMethod postMethod = null;
        GetMethod getMethod = null;
        try {
            /** find device by login */
        	HttpClient client = new HttpClient();
            getMethod = new GetMethod("http://localhost/android/rest/location/search/device/andrevaladas");
            int executeMethod = client.executeMethod(getMethod);

            System.out.println(HttpStatus.getStatusText(executeMethod));
            final Device result = (Device) EntityUtils.getDevice(getMethod.getResponseBodyAsString());
            // printAll founded devices and locations
            EntityUtils.printAll(result);


            /** POST */
            postMethod = new PostMethod("http://localhost/android/rest/location/device/save");
            // create requestEntity
            final RequestEntity reqEntity = new ByteArrayRequestEntity(EntityUtils.toXml(getDefaultEntity()).toString().getBytes(), MediaType.APPLICATION_XML);
            postMethod.setRequestEntity(reqEntity);
            client = new HttpClient();
            executeMethod = client.executeMethod(postMethod);

            System.out.println(HttpStatus.getStatusText(executeMethod));
            final Location location = (Location) EntityUtils.getLocation(postMethod.getResponseBodyAsString());
            // printAll the last location 
            EntityUtils.print(location);
        } catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
			}
			if (getMethod != null) {
				getMethod.releaseConnection();
			}
		}
	}
}
