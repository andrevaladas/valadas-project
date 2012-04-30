package mobile.model;

import java.util.Date;
import java.util.List;

import com.chronosystems.entity.Device;
import com.chronosystems.entity.Location;
import com.chronosystems.entity.util.XMLParser;
import com.chronosystems.model.service.DeviceService;

public class ModelDeviceApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final DeviceService service = new DeviceService();

		final String login = "android";
		Device entity = service.find(login);
		if (entity == null) {
			entity = new Device();
			entity.setEmail(login);
			entity.setName("Android User");
		}

		final Location location = new Location();
		location.setDevice(entity);
		location.setLatitude(-(Math.random()*100));
		location.setLongitude(-Math.random()*100);
		location.setTimeline(new Date());
		//entity.getLocations().add(location);
		service.save(entity);

		XMLParser.printAll(entity);
	}

	@SuppressWarnings("unused")
	private static void printAll(final DeviceService service) {
		System.out.println("-------------- PRINT ALL --------------");
		final List<Device> list = service.findAll();
		for (final Device entity : list) {
			XMLParser.printAll(entity);
		}
	}
}
