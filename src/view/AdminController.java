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
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Serialization;

public class AdminController {

	@FXML Button CreateButton;
	@FXML Button DeleteButton;
	@FXML Button ListButton;
	@FXML Button LogoutButton;
	@FXML ListView UserList;
	@FXML TextField usernameBox;
	
	Stage stage;
	
	private ObservableList<User> usernameList = FXCollections.observableArrayList();
	private ArrayList<User> users = new ArrayList<User>();
	Serialization serialController = new Serialization();
	
	// 1 problem encounters -> stage doesn't close
	public void logoutAction(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("Login.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		Stage s = new Stage();
		s.setTitle("Login");
		s.setScene(new Scene(root, 621, 424));
		s.setResizable(true);
		s.show();
	}
	
	public void listAction(ActionEvent event) throws ClassNotFoundException, IOException {
		users = serialController.readUserList();
		usernameList = FXCollections.observableList(users);
		UserList.setItems(usernameList);
	}
	
	public void createAction(ActionEvent event) throws IOException {
		String username = usernameBox.getText();
		User user = new User(username);
		users.add(user);
		serialController.storeUserList(users);
	}
	
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
	
	public void start(Stage primaryStage){
		this.stage = primaryStage;
	}
		
}
