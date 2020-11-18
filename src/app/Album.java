package app;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
	 * ArrayList<Photo> photos stores the album's Photo objects. 
	 */
	public ArrayList<Photo> photos;
	
	/**
	 * int numPhotos stores the number of photos in the album.
	 */
	int numPhotos; 
	
	/**
	 * These LocalDate date range attributes store the date range that the album is based around.
	 */
	LocalDate startingDateRange;
	LocalDate endingDateRange; 
	
	/**
	 * DateTimeFormatter dateFormatter formats the date ranges from LocalDate format to MM/dd/yyyy format.
	 */
	private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	
	/**
	 * Album creates a new instance of Album. 
	 * @param name
	 * @param numPhotos
	 * @param dateRange
	 */
	public Album(String name) {
		this.name = name;
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
	public String getDateRange() {
		return startingDateRange.format(dateFormatter) + " - " + endingDateRange.format(dateFormatter);
	}
	
	public LocalDate getStartingDateRange() {
		LocalDate earliestDate = photos.get(0).date;
		for (int i = 1; i < photos.size(); i++) {
			LocalDate temp = photos.get(i).date;
			if (temp.compareTo(earliestDate) < 0) 
				earliestDate = temp;
		}
		return earliestDate;
	}
	
	public LocalDate getEndingDateRange() {
		LocalDate latestDate = photos.get(0).date;
		for (int i = 1; i < photos.size(); i++) {
			LocalDate temp = photos.get(i).date;
			if (temp.compareTo(latestDate) > 0) 
				latestDate = temp;
		}
		return latestDate;
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
		return name.equals(a.name);
	}
	
}
