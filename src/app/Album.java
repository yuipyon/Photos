package app;

import java.util.Calendar;
import java.util.Date;

public class Album {
	
	String name; int numPhotos; Date dateRange;
	
	public Album(String name, int numPhotos, Date dateRange) {
		this.name = name;
		this.numPhotos = numPhotos;
		this.dateRange = dateRange;
	}
	
	public String getName() {
		return name;
	}
	
	public int getPhotoCount() {
		return numPhotos;
	}
	
	public Date getDateRange() {
		return dateRange;
	}
}
