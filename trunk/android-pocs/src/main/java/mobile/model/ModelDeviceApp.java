package mobile.model;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

import com.chronosystems.entity.Device;
import com.chronosystems.entity.Entity;
import com.chronosystems.entity.Location;
import com.chronosystems.entity.util.XMLParser;
import com.chronosystems.model.service.DeviceService;

public class ModelDeviceApp {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		final DeviceService service = new DeviceService();

		final String login = "android";
		Device entity = service.find(login);
		if (entity == null) {
			entity = new Device();
		} else {
			saveImageToFile(entity.getImage());
		}
		entity.setEmail(login);
		//entity.setName("André Valadas");
		//entity.setPassword("123");
		//entity.setDatecreated(new Date());
		entity.setImage(getImageFromFile());

		final Location location = new Location();
		location.setDevice(entity);
		location.setLatitude(-29.7705);
		location.setLongitude(-51.1431);
		location.setTimeline(new Date());
		//entity.addLocation(location);

		final Location location2 = new Location();
		location2.setDevice(entity);
		location2.setLatitude(-29.7696);
		location2.setLongitude(-51.1391);
		location2.setTimeline(new Date());
		//entity.addLocation(location2);
		service.save(entity);

		XMLParser.printAll(entity);
	}

	private static byte[] getImageFromFile() {
		// save image into database
		//final File file = new File("C:/chinaValadas.jpg");
		final File file = new File("C:/android.png");
		final byte[] bFile = new byte[(int) file.length()];

		try {
			final FileInputStream fileInputStream = new FileInputStream(file);
			// convert file into array of bytes
			fileInputStream.read(bFile);
			fileInputStream.close();
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return bFile;
	}

	private static void saveImageToFile(final byte[] imgBytes) {
		if(imgBytes == null || imgBytes.length <1) {
			return;
		}
		try {
			final FileOutputStream fos = new FileOutputStream("C:/imagem_return.png");
			fos.write(imgBytes);
			final FileDescriptor fd = fos.getFD();
			fos.flush();
			fd.sync();
			fos.close();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private static void printAll(final DeviceService service) {
		System.out.println("-------------- PRINT ALL --------------");
		final Entity searchResult = service.search(new Entity());
		final List<Device> list = searchResult.getDevices();
		for (final Device entity : list) {
			XMLParser.printAll(entity);
		}
	}
}
