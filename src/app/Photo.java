package app;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

import javafx.scene.image.Image;

/**
 * @author Karun Kanda
 * @author Yulin Ni
 */

/**
 * The Photo class contains all the information pertaining to a photo.
 */

public class Photo implements Serializable{
	
	static final long serialVersionUID = 6266501183382371389L;

	/**
	 * Image photograph is where the image is going to be stored.
	 */
	public transient Image photo;
	
	/**
	 * String photoName is the name of the photo that is stored in photo.
	 */
	public String photoName;
	
	public String filepath;
	
	/**
	 * String caption is the caption of the photo.
	 */
	public String caption;
	
	/**
	 * ArrayList<Tag> tags contains the tags pertaining to the photo.
	 */
	public ArrayList<Tag> tags;
	
	/**
	 * LocalDate date records the time in which the photo was taken - in other words, the last modification date. 
	 */
	
	public LocalDate date; 
	
	public Photo() {
		this.photo = null;
		this.tags = new ArrayList<Tag>();
	}
	
	public void setImage(Image image) {
		this.photo = image;
	}
	
	public String toString() {
		return photoName;
	}
	
	public boolean equals(Object o) {
		if(o == null) {
	         return false;
	    }
		
		if(this == o) {
	         return true;
	    }
		
		if(getClass() != o.getClass()) {
			return false;
		}
		
		Photo p = (Photo)o;
		return photoName.equals(p.photoName);
	}

	public LocalDate setDate(File file) throws IOException {
		Path path = Paths.get(file.getAbsolutePath());
		BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
		FileTime fileTime = attr.lastModifiedTime();
		LocalDate localDate = fileTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return localDate;
	}
	
	public String getDate(LocalDate date) {
		return date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
	}
	
	
}
