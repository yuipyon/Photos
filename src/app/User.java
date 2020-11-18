package app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Karun Kanda
 * @author Yulin Ni
 */

/**
 * The User class stores the information related to the User.
 *
 */
public class User implements Serializable{
	
	/**
	 * final long serialVersionUID makes the User class serializable.
	 */
	static final long serialVersionUID = 5310952031916728350L;
	
	/**
	 * String username stores the user's username.
	 */
	private String username; 
	
	/**
	 * ArrayList<Album> contains the albums of the user. 
	 */
	public ArrayList<Album> albums; 
	
	/**
	 * Creates a new instance of User.
	 * @param username
	 */
	
	/**
	 * ArrayList<TagType> tagTypes stores the initial default tag type presets that is provided for each user, 
	 * which can be added with more custom-made tag types.
	 */
	public ArrayList<TagType> tagTypes = new ArrayList<TagType>();
	
	public User(String username) {
		this.username = username;
	}
	
	/**
	 * getUsername gets the name of the user.
	 * @return String
	 */
	public String getUserName() {
		return username;
	}
	
	/**
	 * getAlbum gets the album list of the user.
	 * @return ArrayList<Album>
	 */
	public ArrayList<Album> getAlbum() {
		return albums;
	}
	
	/**
	 * Returns the name of the user.
	 */
	public String toString() {
		return username;
	}
	
	/**
	 * Overridden version of equals(Object o) to find if two User objects are equal.
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
		
		User u = (User)o;
		return username.equals(u.username);
	}
}
