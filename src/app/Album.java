package app;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Album implements Serializable{
	
	static final long serialVersionUID = -1456323770103513090L;
	
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
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getPhotoCount() {
		return numPhotos;
	}
	
	public void setPhotoCount(int count) {
		this.numPhotos = count;
	}
	
	public Date getDateRange() {
		return dateRange;
	}
	
	public void setDateRange(Date dateRange) {
		this.dateRange = dateRange;
	}
	
	public Tag getTag() {
		return tags;
	}
	
	public void setTag(Tag tag) {
		this.tags = tag;
	}	
	
	public String toString() {
		return name;
	}
	
}
