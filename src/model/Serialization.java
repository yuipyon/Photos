package model;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import app.Album;
import app.User;

/**
 * @author Karun Kanda
 * @author Yulin Ni
 */

/**
 * The Serialization class handles storing the object data (the current album, the user arraylist and the current album)
 */

public class Serialization {
	
	/**
	 * String curr_user_filepath is the path where the current user will be stored.
	 */
	static String curr_user_filepath = "user_data/curr_user.txt";
	
	/**
	 * String usernames_filepath is the path where the array list of Users is going to be stored.
	 */
	static String usernames_filepath = "user_data/usernames.ser";
	
	/**
	 * String curr_album_filepath is the path where the current album will be stored.
	 */
	static String curr_album_filepath = "user_data/curr_album.txt";
	
	/**
	 * storeCurrentUser takes the current user as an argument and writes that information to the curr_user.txt file.
	 * @param curr_user
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static void storeCurrentUser(User curr_user) throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(curr_user_filepath));
		oos.writeObject(curr_user);
		oos.close();
	}
	
	/**
	 * readCurrentUser reads the current user from curr_user.txt and and writes that information to the User object and returns it.
	 * @return User
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static User readCurrentUser() throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(curr_user_filepath));
		User curr_user = (User)ois.readObject(); 
		return curr_user;
	}
	
	/**
	 * storeCurrentAlbum stores the current album into curr_album.txt and writes that information into curr_album.txt.
	 * @param curr_album
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static void storeCurrentAlbum(Album curr_album) throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(curr_album_filepath));
		oos.writeObject(curr_album);
		oos.close();
	}
	
	/**
	 * readCurrentAlbum reads the current album from curr_album.txt, stores that into a Album object and returns that. 
	 * @return Album
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Album readCurrentAlbum() throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(curr_album_filepath));
		Album curr_album = (Album)ois.readObject(); 
		return curr_album;
	}
	
	/**
	 * storeUserList stores the User arraylist into usernames.ser.
	 * @param userList
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void storeUserList(ArrayList<User>userList) throws FileNotFoundException, IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(usernames_filepath));
		oos.writeObject(userList);
		oos.close();
	}
	
	/**
	 * readUserList reads the user arraylist from usernames.ser and returns that to an ArrayList<User> object. 
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static ArrayList<User> readUserList() throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(usernames_filepath));
		ArrayList<User> userList = (ArrayList<User>)ois.readObject(); 
		return userList;
	}
}
