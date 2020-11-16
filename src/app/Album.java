package app;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author Karun
 * @author Yulin Ni
 */

/**
 * The Album class stores information related to the User's album
 */

public class Album implements Serializable{
	
	/**
	 * final long serialVersionUID makes the Album class serializable.
	 */
	static final long serialVersionUID = -1456323770103513090L;
	
	/**
	 * String name stores the name of the album.
	 */
	String name; 
	
	/**
	 * int numPhotos stores the number of photos in the album.
	 */
	int numPhotos; 
	
	/**
	 * Date dateRange stores the date range that the album is based around.
	 */
	Date dateRange; 
	
	/**
	 * Album creates a new instance of Album. 
	 * @param name
	 * @param numPhotos
	 * @param dateRange
	 */
	public Album(String name, int numPhotos, Date dateRange) {
		this.name = name;
		this.numPhotos = numPhotos;
		this.dateRange = dateRange;
	}
	
	/**
	 * getName returns the name of the album.
	 * @return String
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * setName sets the name of the album.
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * getPhotoCount returns the number of photos in that album.
	 * @return int
	 */
	public int getPhotoCount() {
		return numPhotos;
	}
	
	/**
	 * setPhotoCount sets the number of photos in the album.
	 * @param count
	 */
	public void setPhotoCount(int count) {
		this.numPhotos = count;
	}
	
	/**
	 * getDateRange returns the date range of the album.
	 * @return Date
	 */
	public Date getDateRange() {
		return dateRange;
	}
	
	/**
	 * setDateRange sets the date range of an album.
	 * @param dateRange
	 */
	public void setDateRange(Date dateRange) {
		this.dateRange = dateRange;
	}
	
	/**
	 * Returns the name of the album.
	 */
	public String toString() {
		return name;
	}
	
	/**
	 * Overridden version of equals(Object o) to find if two albums are equal.
	 */
	@Override
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
		
		Album a = (Album)o;
		return name.equals(a.name) && numPhotos == (a.numPhotos) && dateRange.equals(a.dateRange);
	}
	
}
