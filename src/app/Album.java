package app;

import java.util.Calendar;

public class Album {
	
	String name; int numPhotos; String dateRange;
	
	public Album(String name, int numPhotos, String dateRange) {
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
	
	public String getDateRange() {
		return dateRange;
	}
}
