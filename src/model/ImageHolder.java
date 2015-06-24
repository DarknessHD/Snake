package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.imageio.ImageIO;

/**
 * @author Stefan Kameter, Eric Armbruster
 * @version 24.06.2015
 */
public class ImageHolder {
	
	private static Map<String, BufferedImage> imageHolder = new HashMap<>();
	
	/**
	 * Returns the BufferedImage under the key in the HashMap if available, otherwise tries to load it.
	 * 
	 * @param file 
	 * 			the path to the file of the image you want to load
	 * @param key
	 * 			the key under which the image gets saved 
	 * @return the image
	 */
	public static BufferedImage getImage(String file, String key) {
		Objects.requireNonNull(key);
		BufferedImage image = imageHolder.get(file);
		
		if(image == null) {
			image = loadImage(file);
			if(image != null)
				imageHolder.put(key, image);
		}
		
		return image;
	}
	
	/**
	 * Returns the BufferedImage under the key in the HashMap if available, otherwise returns null.
	 * 
	 * @param key
	 * 			the key under which the image gets saved 
	 * @return the image
	 */
	public static BufferedImage getImage(String key) {
		return getImage(null, key);
	}
	
	/**
	 * Loads a BufferedImage from the file system.
	 * 
	 * @param file
	 * 			the path to the file to load
	 * @return the image
	 */
	public static BufferedImage loadImage(String file) {
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(new File(file));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		return image;
	}

}