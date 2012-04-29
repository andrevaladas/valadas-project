package com.chronosystems.main;

import java.util.Date;

import javax.ws.rs.core.MediaType;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.util.EncodingUtil;

import com.chronosystems.entity.Device;
import com.chronosystems.entity.Entity;
import com.chronosystems.entity.Location;
import com.chronosystems.entity.util.EntityUtils;

public class RestServiceApp {

	private static Device getDefaultEntity() {
		final Device entity = new Device();
        entity.setEmail("andrevaladas");
        entity.setName("André Valadas");

        final Location location = new Location();
		location.setDevice(entity);
		location.setLatitude(-(Math.random()*100));
		location.setLongitude(-Math.random()*100);
		location.setTimeline(new Date());
		//entity.getLocations().add(location);
        return entity;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PostMethod postMethod = null;
        GetMethod getMethod = null;
        try {
        	final HttpClient client = new HttpClient();

            /** GET find device by login */
            getMethod = new GetMethod("http://localhost/android-service/rest/device/xml/search/andrevaladas");
            getMethod.setRequestHeader("Accept-Charset", "UTF-8");
            getMethod.setRequestHeader("Content-Type", "text/xml; charset=UTF-8");
            //getMethod.addRequestHeader("Content-Type", "charset=UTF-8");
            int executeMethod = client.executeMethod(getMethod);

            System.out.println(HttpStatus.getStatusText(executeMethod));
            final Entity result = (Entity) EntityUtils.getEntity(EncodingUtil.getString(getMethod.getResponseBody(), "UTF-8"));
            // printAll founded devices and locations
            EntityUtils.printAll(result);

            /** POST */
            postMethod = new PostMethod("http://localhost/android-service/rest/device/save");
            // create requestEntity
            final RequestEntity reqEntity = new ByteArrayRequestEntity(EntityUtils.toXml(getDefaultEntity()).toString().getBytes("UTF-8"), MediaType.APPLICATION_XML);
            postMethod.setRequestEntity(reqEntity);
            executeMethod = client.executeMethod(postMethod);

            System.out.println(HttpStatus.getStatusText(executeMethod));
            final Entity saveResult = (Entity) EntityUtils.getEntity(EncodingUtil.getString(postMethod.getResponseBody(), "UTF-8"));
            // printAll the last location 
            EntityUtils.printAll(saveResult);
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
