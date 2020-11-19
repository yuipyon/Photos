package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Scanner;

import app.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Serialization;

/**
 * @author Karun Kanda
 * @author Yulin Ni
 */

/**
 * The AdminController controls all the behaviors of the screen and button in the Admin Dashboard.
 */
public class AdminController {

	/**
	 * Button CreateButton enables the admin to create a user.
	 */
	@FXML Button CreateButton;
	
	/**
	 * Button DeleteButton enables the admin to delete a user.
	 */
	@FXML Button DeleteButton;
	
	/**
	 * Button ListButton enables the admin to list all the users that logged in to the application.
	 */
	@FXML Button ListButton;
	
	/**
	 * Button LogoutButton enables the admin to logout of their session.
	 */
	@FXML Button LogoutButton;
	
	/**
	 * ListView UserList display the users that logged into the application.
	 */
	@FXML ListView UserList;
	
	/**
	 * TextField usernameBox enables the admin to input a new username.
	 */
	@FXML TextField usernameBox;
	
	/**
	 * Stage stage is used to switch to a new scene in one stage.
	 */
	Stage stage;
	
	/**
	 * ObservableList<User> usernameList creates a list that can be displayed in the ListView.
	 */
	private ObservableList<User> usernameList = FXCollections.observableArrayList();
	
	/**
	 * ArrayList<User> users holds the list of Users stored in usernames.ser
	 */
	private ArrayList<User> users = new ArrayList<User>();
	
	/**
	 * Creates a instance of the Serialization model to create serializable data to use between controllers.
	 */
	Serialization serialController = new Serialization();
	
	/**
	 * logoutAction enables the user to logout from the session.
	 * @param event
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 */
	public void logoutAction(ActionEvent event) throws IOException, ClassNotFoundException {
		
		serialController.storeUserList(users);
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("Login.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		
		stage.close();
		
		LoginController lg = loader.getController();
		Stage ns = new Stage();
		
		lg.start(ns);
		ns.setTitle("Login");
		ns.setScene(new Scene(root, 449, 365));
		ns.setResizable(true);
		ns.show();
	}
	
	/**
	 * listAction enables the behavior for the admin to list all the users that logged into the application.
	 * @param event
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void listAction(ActionEvent event) throws ClassNotFoundException, IOException {
		users = serialController.readUserList();
		usernameList = FXCollections.observableList(users);
		UserList.setItems(usernameList);
	}
	
	/**
	 * userExists finds if the user exists in the list of users. 
	 * @param username
	 * @param userList
	 * @return
	 */
	private boolean userExists(User username, ArrayList<User> userList) {
		if(userList.isEmpty()) {
			return false;
		}
		for(int i = 0; i <= userList.size() - 1; i++) {
			if(userList.get(i).equals(username)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * createAction enables the behavior for the admin to create a new user.
	 * @param event
	 * @throws IOException
	 */
	public void createAction(ActionEvent event) throws IOException {
		String username = usernameBox.getText();
		User user = new User(username);
		if(userExists(user, users) == true) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Duplicate Entry");
			alert.setHeaderText("This album exists in your album.");
			alert.setContentText("Please input another album.");
			alert.showAndWait();
		} else {
			users.add(user);
			serialController.storeUserList(users);
		}
	}
	
	/**
	 * deleteAction enables the behavior for the admin to delete a user.
	 * @param event
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void deleteAction(ActionEvent event) throws FileNotFoundException, IOException {
		int selectedIndex = UserList.getSelectionModel().getSelectedIndex();
		if(selectedIndex != -1) {
			User userToRemove = (User) UserList.getSelectionModel().getSelectedItem();
			System.out.println(userToRemove);
			int newSelectedIndex = (selectedIndex == UserList.getItems().size() - 1) ? selectedIndex - 1 : selectedIndex;
			users.remove(selectedIndex);
			usernameList = FXCollections.observableList(users);
			UserList.setItems(usernameList);
			serialController.storeUserList(users);
		}
	}
	
	/**
	 * start is what will occur upon starting the admin dashboard scene.
	 * @param primaryStage
	 */
	public void start(Stage primaryStage){
		this.stage = primaryStage;
		primaryStage.setOnCloseRequest(event -> {
			try {
				Serialization.storeUserList(users);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

	}
		
}
