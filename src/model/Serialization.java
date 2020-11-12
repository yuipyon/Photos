package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import app.User;

public class Serialization {
	
	static String curr_user_filepath = "user_data/curr_user.txt";
	static String usernames_filepath = "user_data/usernames.ser";
	

	public static void storeCurrentUser(User curr_user) throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(curr_user_filepath));
		oos.writeObject(curr_user);
		oos.close();
	}
	
	public static User readCurrentUser() throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(curr_user_filepath));
		User curr_user = (User)ois.readObject(); 
		return curr_user;
	}
	
	public static void storeUserList(ArrayList<User>userList) throws FileNotFoundException, IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(usernames_filepath));
		oos.writeObject(userList);
		oos.close();
	}
	
	public static ArrayList<User> readUserList() throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(usernames_filepath));
		ArrayList<User> userList = (ArrayList<User>)ois.readObject(); 
		return userList;
	}
}
