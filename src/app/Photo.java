package app;

import java.util.ArrayList;

import javafx.scene.image.Image;

/**
 * @author Karun Kanda
 * @author Yulin Ni
 */

/**
 * The Photo class contains all the information pertaining to a photo.
 */

public class Photo {

	/**
	 * Image photograph is where the image is going to be stored.
	 */
	private transient Image photo;
	
	/**
	 * String photoName is the name of the photo that is stored in photo.
	 */
	private String photoName;
	
	/**
	 * String caption is the caption of the photo.
	 */
	private String caption;
	
	/**
	 * ArrayList<Tag> tags contains the tags pertaining to the photo.
	 */
	private ArrayList<Tag> tags;
	
	public Photo(Image photo) {
		this.photo = photo;
		this.tags = new ArrayList<Tag>();
	}
	
	
	
}
