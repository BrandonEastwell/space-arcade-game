package utilities;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
//resources from lab 5 sounds and images
//https://moodle.essex.ac.uk/pluginfile.php/713935/mod_resource/content/4/lab5/lab5.html
public class ImageManager {

	// this may need modifying
	public final static String path = "images/";
	public final static String ext = ".png";

	public static Map<String, Image> images = new HashMap<String, Image>();

	public static Image getImage(String s) {
		return images.get(s);
	}

	public static Image loadImage(String fname) throws IOException {
		BufferedImage img = null;
		img = ImageIO.read(new File(path + fname + ext));
		images.put(fname, img);
		return img; 
	}
}
