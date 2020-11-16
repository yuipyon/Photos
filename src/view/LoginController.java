package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import app.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Serialization;

public class LoginController implements Serializable{
	
	@FXML TextField UsernameBox;
	
	@FXML Button SubmitBox;
	
	Stage stage;
	
	ArrayList<User> userList = new ArrayList<User>();
	Serialization serialController = new Serialization();
	
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
	
	public void login(ActionEvent event) throws IOException, ClassNotFoundException {
		
		boolean userExist;
		
		if(!UsernameBox.getText().equals("admin")) {
			User username = new User(UsernameBox.getText());
			userExist = userExists(username, userList);
			System.out.println(userExist);
			
			if(userExist == true) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("user_dashboard.fxml"));
				AnchorPane root = (AnchorPane)loader.load();
				serialController.storeCurrentUser(username);
				//System.out.println((User)serialController.readCurrentUser());
				
				Stage s = new Stage();
				
				UserController userController = loader.getController();
				userController.start(s);
				
				
				s.setTitle("User Dashboard");
				s.setScene(new Scene(root, 800, 600));
				s.setResizable(true);
				s.show();
			} else {
				userList.add(username);
				serialController.storeUserList(userList);
				serialController.storeCurrentUser(username);
				System.out.println((User)serialController.readCurrentUser());
				
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("user_dashboard.fxml"));
				AnchorPane root = (AnchorPane)loader.load();
				
				Stage s = new Stage();
				
				UserController userController = loader.getController();
				userController.start(s);
				
				s.setTitle("User Dashboard");
				s.setScene(new Scene(root, 800, 600));
				s.setResizable(true);
				s.show();
			}
			
		} else {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("admin_dashboard.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			
			Stage s = new Stage();
			
			AdminController adminController = loader.getController();
			adminController.start(s);
			
			s.setTitle("Admin Dashboard");
			s.setScene(new Scene(root, 621, 424));
			s.setResizable(false);
			s.show();
		}
	}
	
	public void start(Stage primaryStage) throws FileNotFoundException, ClassNotFoundException, IOException{
		stage = primaryStage;
		File file = new File("user_data/usernames.ser");
		if(file.length() == 0) {
			
		} else {
			userList = serialController.readUserList();
		}
			
		System.out.println(userList);
	}
}
