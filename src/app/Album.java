package app;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Album implements Serializable{
	
	String name; int numPhotos; Date dateRange; Tag tags;
	
	public Album(String name, int numPhotos, Date dateRange, Tag tags) {
		this.name = name;
		this.numPhotos = numPhotos;
		this.dateRange = dateRange;
		this.tags = tags;
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
	
	public Tag getTag() {
		return tags;
	}
	
	public String toString() {
		return name;
	}
	
}
