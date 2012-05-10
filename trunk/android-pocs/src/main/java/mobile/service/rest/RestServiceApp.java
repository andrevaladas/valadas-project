package mobile.service.rest;

import java.util.Date;

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
import com.chronosystems.entity.util.XMLParser;

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
	public static void main(final String[] args) {
		PostMethod postMethod = null;
		GetMethod getMethod = null;
		try {
			final HttpClient client = new HttpClient();

			/** GET find device by login */
			getMethod = new GetMethod("http://localhost:8888/android-service/rest/device/search/andrevaladas");
			getMethod.setRequestHeader("Accept-Charset", "UTF-8");
			getMethod.setRequestHeader("Content-Type", "text/xml; charset=UTF-8");
			//getMethod.addRequestHeader("Content-Type", "charset=UTF-8");
			int executeMethod = client.executeMethod(getMethod);

			System.out.println(HttpStatus.getStatusText(executeMethod));
			//final Entity result = XMLParser.parseXML(EncodingUtil.getString(getMethod.getResponseBody(), "UTF-8"), Entity.class);
			//printAll founded devices and locations
			//XMLParser.printAll(result);

			/** POST */
			postMethod = new PostMethod("http://localhost:8888/android-service/rest/device/login");
			// create requestEntity
			final RequestEntity reqEntity = new ByteArrayRequestEntity(XMLParser.parseXML(getDefaultEntity()).toString().getBytes("UTF-8"), "text/xml");
			postMethod.setRequestEntity(reqEntity);
			postMethod.setRequestHeader("Content-Type", "text/xml; charset=UTF-8");
			executeMethod = client.executeMethod(postMethod);

			System.out.println(HttpStatus.getStatusText(executeMethod));
			final Entity saveResult = XMLParser.parseXML(EncodingUtil.getString(postMethod.getResponseBody(), "UTF-8"), Entity.class);
			// printAll the last location
			XMLParser.printAll(saveResult);
		} catch (final Exception e) {
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
