package app;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable{
	
	static final long serialVersionUID = 5310952031916728350L;
	
	private String username; 
	public ArrayList<Album> albums; 
	
	public User(String username) {
		this.username = username;
	}
	
	public String getUserName() {
		return username;
	}
	
	public ArrayList<Album> getAlbum() {
		return albums;
	}
	
	public String toString() {
		return username;
	}
	
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
