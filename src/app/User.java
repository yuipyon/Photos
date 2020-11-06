package app;

import java.io.Serializable;

public class User implements Serializable{
	
	private String username; 
	
	public User(String username) {
		this.username = username;
	}
	
	public String getUserName() {
		return username;
	}
	
	public String toString() {
		return username;
	}
}
