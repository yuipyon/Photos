package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
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

public class UserController implements Serializable{
	
	@FXML TextField name;
	@FXML TextField date;
	@FXML TextField numPhotos;
	@FXML Button searchButton;
	@FXML Button deleteButton;
	@FXML Button createButton;
	@FXML Button openButton;
	@FXML Button renameButton;
	@FXML Button logoutButton;
	@FXML ListView albumList;
	
	
	private ObservableList<Album> albums = FXCollections.observableArrayList();
	private ArrayList<User> userList = new ArrayList<User>();
	private ArrayList<Album> albumLists = new ArrayList<Album>();
	Serialization serialController = new Serialization();
	User curr_user;
	Stage mainStage;
	
	public void logout(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("Login.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		mainStage.setTitle("Login");
		mainStage.setScene(new Scene(root, 621, 424));
		mainStage.setResizable(true);
		mainStage.show();
	}
	
	private boolean albumExist(Album album, ArrayList<Album> albumList) {
		if(albumList.isEmpty()) {
			return false;
		}
		for(int i = 0; i <= albumList.size() - 1; i++) {
			if(albumList.get(i).equals(album)) {
				return true;
			}
		}
		return false;
	}
	
	private ArrayList<User> updateAlbum(User user, ArrayList<User> userList){
		for(int i = 0; i <= userList.size() - 1; i++) {
			if(userList.get(i).equals(user)) {
				userList.remove(i);
			}
		}
		userList.add(user);
		return userList;
	}
	
	public void create(ActionEvent e) throws ParseException, FileNotFoundException, IOException, ClassNotFoundException {
		
		System.out.println("Current User: " + curr_user);
		
		String albumName = name.getText();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		Date date1 = dateFormat.parse(date.getText());
		int numPhotos1 = Integer.parseInt(numPhotos.getText());
		Tag newTag = new Tag("", "");
		Album newAlbum = new Album(albumName, numPhotos1, date1, newTag);
		
		boolean albumExists = albumExist(newAlbum, albumLists);
		System.out.println(albumExists);
		
		if(albumExists == true) {
			throw new IllegalArgumentException("This album exists");
		} else {
			albumLists.add(newAlbum);
			curr_user.albums = albumLists;
			albums = FXCollections.observableList(albumLists);
			albumList.setItems(albums);
			
			userList = updateAlbum(curr_user, userList);
			serialController.storeUserList(userList);
		}
				
	}
	
	public void search(ActionEvent e) {}
	
	public void delete(ActionEvent e) throws ClassNotFoundException, IOException {
		int selectedIndex = albumList.getSelectionModel().getSelectedIndex();
		if(selectedIndex != -1) {
			Album albumToRemove = (Album) albumList.getSelectionModel().getSelectedItem();
			System.out.println(albumToRemove);
			int newSelectedIndex = (selectedIndex == albumList.getItems().size() - 1) ? selectedIndex - 1 : selectedIndex;
			albumLists.remove(selectedIndex);
			albums = FXCollections.observableList(albumLists);
			albumList.setItems(albums);
			
		}
		curr_user.albums = albumLists;
		userList = updateAlbum(curr_user, userList);
		serialController.storeUserList(userList);
	}
	
	public void rename(ActionEvent e) throws FileNotFoundException, IOException {
		int selectedIndex = albumList.getSelectionModel().getSelectedIndex();
		if(selectedIndex != -1) {
			Album albumToRename = (Album) albumList.getSelectionModel().getSelectedItem();
			System.out.println(albumToRename);
			int newSelectedIndex = (selectedIndex == albumList.getItems().size() - 1) ? selectedIndex - 1 : selectedIndex;
			TextInputDialog dialog = new TextInputDialog(albumToRename.toString());
			dialog.initOwner(mainStage); 
			dialog.setTitle("Album Information");
			dialog.setHeaderText("Selected Album: " + albumToRename.toString());
			dialog.setContentText("Enter album name: ");
			
			Optional<String> result = dialog.showAndWait();
			if(result.isPresent()) { 
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
	
	public void open(ActionEvent e) throws IOException, ClassNotFoundException {
		int selectedIndex = albumList.getSelectionModel().getSelectedIndex();
		if(selectedIndex != -1) {
			Album curr_album = (Album) albumList.getSelectionModel().getSelectedItem();
			System.out.println(curr_album);
			int newSelectedIndex = (selectedIndex == albumList.getItems().size() - 1) ? selectedIndex - 1 : selectedIndex;
			serialController.storeCurrentAlbum(curr_album);
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("Album_Display.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			mainStage.setTitle("Album Display");
			mainStage.setScene(new Scene(root, 621, 424));
			mainStage.setResizable(true);
			mainStage.show();
			
		}	
	}
	
	public void start(Stage primaryStage) throws FileNotFoundException, IOException, ClassNotFoundException{
		
		mainStage = primaryStage;
		
		File file = new File("user_data/usernames.ser");
		if(file.length() == 0) {
			// do nothing
		} else {
			userList = serialController.readUserList();
		} 
		
		curr_user = serialController.readCurrentUser();
		
		for(int i = 0; i <= userList.size() - 1; i++) {
			if(userList.get(i).equals(curr_user)) {
				curr_user = userList.get(i);
			}
		}
		
		if(curr_user.albums == null) {
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
