package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Serialization;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import app.Album;
import app.Tag;
import app.User;

/**
 * @author Karun Kanda
 * @author Yulin Ni
 */

/**
 * The UserController controls all the behaviors of the screen and button in the
 * User Dashboard.
 */
public class UserController implements Serializable {

	/**
	 * TextField name enables the user to input a name for the album they are
	 * creating.
	 */
	@FXML
	TextField name;

	/**
	 * TextField date enables the user to input the date of the album (MM-dd-yyyy)
	 */
	@FXML
	TextField date;

	/**
	 * TextField numPhotos enables the user to input the number of photos in the
	 * album.
	 */
	@FXML
	TextField numPhotos;

	/**
	 * Button searchButton enables the user to search for the photos in an album.
	 */
	@FXML
	Button searchButton;

	/**
	 * Button deleteButton enables the user to delete an album.
	 */
	@FXML
	Button deleteButton;

	/**
	 * Button createButton enables the user to create an album.
	 */
	@FXML
	Button createButton;

	/**
	 * Button openButton enables the user to open an album.
	 */
	@FXML
	Button openButton;

	/**
	 * Button renameButton enables the user to rename an Album that is present in
	 * their list.
	 */
	@FXML
	Button renameButton;

	/**
	 * Button logoutButton enables the user to logout of their session.
	 */
	@FXML
	Button logoutButton;

	/**
	 * ListView albumList displays the albums for the current user logged in.
	 */
	@FXML
	ListView albumList;

	/**
	 * OberservableList<Album> albums create a list that can be displayed in the
	 * ListView.
	 */
	private ObservableList<Album> albums = FXCollections.observableArrayList();

	/**
	 * ArrayList<User> users holds the list of Users stored in usernames.ser
	 */
	public static ArrayList<User> userList = new ArrayList<User>();

	/**
	 * ArrayList<Album> albumLists holds the albums for the user.
	 */
	private ArrayList<Album> albumLists = new ArrayList<Album>();

	/**
	 * Creates a instance of the Serialization model to create serializable data to
	 * use between controllers.
	 */
	Serialization serialController = new Serialization();

	/**
	 * User curr_user holds the information of the current user.
	 */
	User curr_user;

	/**
	 * Stage mainStage is used to switch between scenes in one stage.
	 */
	Stage mainStage;

	/**
	 * logout enables the behavior to logout of the user's current session.
	 * 
	 * @param e
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void logout(ActionEvent e) throws IOException, ClassNotFoundException {
		serialController.storeUserList(userList);

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("Login.fxml"));
		AnchorPane root = (AnchorPane) loader.load();

		mainStage.close();

		LoginController lg = loader.getController();
		Stage ns = new Stage();

		lg.start(ns);
		ns.setTitle("Login");
		ns.setScene(new Scene(root, 449, 365));
		ns.setResizable(true);
		ns.show();
	}

	/**
	 * albumExists finds if an album exists in the album array list.
	 * 
	 * @param album
	 * @param albumList
	 * @return boolean
	 */
	private boolean albumExist(Album album, ArrayList<Album> albumList) {
		if (albumList.isEmpty()) {
			return false;
		}
		for (int i = 0; i <= albumList.size() - 1; i++) {
			if (albumList.get(i).equals(album)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * updateAlbum updates the album list when its modified of the user.
	 * 
	 * @param user
	 * @param userList
	 * @return ArrayList<User>
	 */
	private ArrayList<User> updateAlbum(User user, ArrayList<User> userList) {
		for (int i = 0; i <= userList.size() - 1; i++) {
			if (userList.get(i).equals(user)) {
				userList.remove(i);
			}
		}
		userList.add(user);
		return userList;
	}

	/**
	 * create enables the behavior to create a new album.
	 * 
	 * @param e
	 * @throws ParseException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void create(ActionEvent e)
			throws ParseException, FileNotFoundException, IOException, ClassNotFoundException {

		System.out.println("Current User: " + curr_user);

		String albumName = name.getText();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		Date date1 = null;
		try {
			date1 = dateFormat.parse(date.getText());
		} catch (ParseException e1) {
			date1 = null;
		}

		int numPhotos1 = 0;
		try {
			numPhotos1 = Integer.parseInt(numPhotos.getText());
		} catch (Exception e21) {
			numPhotos1 = 0;
		}

		Album newAlbum = new Album(albumName, numPhotos1, date1);

		boolean albumExists = albumExist(newAlbum, albumLists);
		System.out.println(albumExists);

		if (albumExists == true) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Duplicate album");
			alert.setHeaderText("A duplicate album entry was entered");
			alert.setContentText("Please include another non-duplicate album");
			alert.showAndWait();
		} else {
			if (albumName.length() == 0) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Invalid Input");
				alert.setHeaderText("No album name was given");
				alert.setContentText("Please include a valid album name");
				alert.showAndWait();
			} else {
				albumLists.add(newAlbum);
				curr_user.albums = albumLists;
				albums = FXCollections.observableList(albumLists);
				albumList.setItems(albums);
				userList = updateAlbum(curr_user, userList);
				serialController.storeUserList(userList);
			}
		}

	}

	/**
	 * search enables the behavior to search for a photo.
	 * 
	 * @param e
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void search(ActionEvent e) throws FileNotFoundException, ClassNotFoundException, IOException {
		int selectedIndex = albumList.getSelectionModel().getSelectedIndex();
		if (selectedIndex != -1) {
			Album curr_album = (Album) albumList.getSelectionModel().getSelectedItem();
			System.out.println(curr_album);
			int newSelectedIndex = (selectedIndex == albumList.getItems().size() - 1) ? selectedIndex - 1
					: selectedIndex;
			serialController.storeCurrentAlbum(curr_album);
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("photo view.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			
			PhotoController pc = loader.getController();
			pc.start(mainStage);
			
			mainStage.setTitle("Photos View");
			mainStage.setScene(new Scene(root, 700, 600));
			mainStage.setResizable(true);
			mainStage.show();

		}

	}

	/**
	 * delete enables the behavior to delete an album.
	 * 
	 * @param e
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void delete(ActionEvent e) throws ClassNotFoundException, IOException {
		int selectedIndex = albumList.getSelectionModel().getSelectedIndex();
		if (selectedIndex != -1) {
			Album albumToRemove = (Album) albumList.getSelectionModel().getSelectedItem();
			System.out.println(albumToRemove);
			int newSelectedIndex = (selectedIndex == albumList.getItems().size() - 1) ? selectedIndex - 1
					: selectedIndex;
			albumLists.remove(selectedIndex);
			albums = FXCollections.observableList(albumLists);
			albumList.setItems(albums);

		}
		curr_user.albums = albumLists;
		userList = updateAlbum(curr_user, userList);
		serialController.storeUserList(userList);
	}

	/**
	 * rename enables the behavior to rename an album (just the name).
	 * 
	 * @param e
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void rename(ActionEvent e) throws FileNotFoundException, IOException {
		int selectedIndex = albumList.getSelectionModel().getSelectedIndex();
		if (selectedIndex != -1) {
			Album albumToRename = (Album) albumList.getSelectionModel().getSelectedItem();
			System.out.println(albumToRename);
			int newSelectedIndex = (selectedIndex == albumList.getItems().size() - 1) ? selectedIndex - 1
					: selectedIndex;
			TextInputDialog dialog = new TextInputDialog(albumToRename.toString());
			dialog.initOwner(mainStage);
			dialog.initOwner(mainStage);
			dialog.setTitle("Album Information");
			dialog.setHeaderText("Selected Album: " + albumToRename.toString());
			dialog.setContentText("Enter album name: ");

			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()) {
				albumToRename.setName(result.get());
				albumLists.set(selectedIndex, albumToRename);
				albums = FXCollections.observableList(albumLists);
				albumList.setItems(albums);
			}

		}
		curr_user.albums = albumLists;
		userList = updateAlbum(curr_user, userList);
		serialController.storeUserList(userList);
	}

	/**
	 * open enables the behavior to open the contents of an album.
	 * 
	 * @param e
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void open(ActionEvent e) throws IOException, ClassNotFoundException {
		int selectedIndex = albumList.getSelectionModel().getSelectedIndex();
		if (selectedIndex != -1) {
			Album curr_album = (Album) albumList.getSelectionModel().getSelectedItem();
			System.out.println(curr_album);
			int newSelectedIndex = (selectedIndex == albumList.getItems().size() - 1) ? selectedIndex - 1
					: selectedIndex;
			serialController.storeCurrentAlbum(curr_album);
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("Album_Display.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			
			AlbumController ac = loader.getController();
			ac.start(mainStage);
			
			mainStage.setTitle("Album Display");
			mainStage.setScene(new Scene(root, 621, 424));
			mainStage.setResizable(true);
			mainStage.show();

		}


	}

	/**
	 * start is what will occur upon starting the user dashboard scene.
	 * 
	 * @param primaryStage
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */

	public void start(Stage primaryStage) throws FileNotFoundException, IOException, ClassNotFoundException {

		this.mainStage = primaryStage;

		File file = new File("user_data/usernames.ser");
		if (file.length() == 0) {
			// do nothing
		} else {
			userList = serialController.readUserList();
		}

		curr_user = serialController.readCurrentUser();

		for (int i = 0; i <= userList.size() - 1; i++) {
			if (userList.get(i).equals(curr_user)) {
				curr_user = userList.get(i);
			}
		}

		if (curr_user.albums == null) {
			albums = FXCollections.observableList(albumLists);
			albumList.setItems(albums);
		} else {
			albumLists = curr_user.albums;
			albums = FXCollections.observableList(albumLists);
			albumList.setItems(albums);
		}

		System.out.println(albumLists);

	}

}
