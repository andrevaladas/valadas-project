/**
 * 
 */
package mobile.xml.image;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.hibernate.Hibernate;
import org.simpleframework.xml.Element;

import com.chronosystems.entity.util.XMLParser;

/**
 * @author andrevaladas
 * 
 */
public class PictureEntity {

	@Override
	public String toString() {
		return "PictureEntity [image=" + Arrays.toString(image) + "]";
	}

	@Element
	private byte[] image;

	public BufferedImage getBImage() throws IOException {
		final InputStream in = new ByteArrayInputStream(image);
		return ImageIO.read(in);
	}

	public void setImage(final BufferedImage bImage) throws IOException {
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(bImage, "PNG" /* for instance */, out);
		image = out.toByteArray();
	}

	public byte[] getImage() {
		return this.image;
	}

	public void setImage(final byte[] image) {
		this.image = image;
	}

	@SuppressWarnings("unused")
	private void setBlob(final Blob imageBlob) {
		this.image = toByteArray(imageBlob);
	}

	private Blob getBlob() {
		return Hibernate.createBlob(this.image);
	}

	private byte[] toByteArray(final Blob fromImageBlob) {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			return toByteArrayImpl(fromImageBlob, baos);
		} catch (final Exception e) {
		}
		return null;
	}

	private byte[] toByteArrayImpl(final Blob fromImageBlob,
			final ByteArrayOutputStream baos) throws SQLException, IOException {
		final byte buf[] = new byte[4000];
		int dataSize;
		final InputStream is = fromImageBlob.getBinaryStream();

		try {
			while ((dataSize = is.read(buf)) != -1) {
				baos.write(buf, 0, dataSize);
			}
		} finally {
			if (is != null) {
				is.close();
			}
		}
		return baos.toByteArray();
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(final String[] args) throws IOException {
		// save image into database
		final File file = new File("C:\\imagem.png");
		final byte[] bFile = new byte[(int) file.length()];

		try {
			final FileInputStream fileInputStream = new FileInputStream(file);
			// convert file into array of bytes
			fileInputStream.read(bFile);
			fileInputStream.close();
		} catch (final Exception e) {
			e.printStackTrace();
		}

		final PictureEntity pictureEntity = new PictureEntity();
		pictureEntity.setImage(bFile);
		final Blob blob = pictureEntity.getBlob();
		final BufferedImage bImage = pictureEntity.getBImage();
		System.out.println("FEITO");

		try {
			final String xml = XMLParser.parseXML(pictureEntity);
			final PictureEntity obj = XMLParser.parseXML(xml, PictureEntity.class);
			System.out.println(obj.toString());
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

}
