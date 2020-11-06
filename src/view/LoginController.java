package view;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
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

public class LoginController implements Serializable{
	
	@FXML TextField UsernameBox;
	
	@FXML Button SubmitBox;
	
	Stage stage;
	
	ArrayList<User> userList = new ArrayList<User>();
	
	public void login(ActionEvent event) throws IOException {
		
		
		User falseUser = new User("admin");
		
		
		if(!UsernameBox.getText().equals("admin")) {
			User username = new User(UsernameBox.getText());
			userList.add(username);
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("user_data/usernames.ser"));
			oos.writeObject(userList);
			oos.close();
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("user_dashboard.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			
			Stage s = new Stage();
			
			s.setTitle("User Dashboard");
			s.setScene(new Scene(root, 800, 600));
			s.setResizable(true);
			s.show();
		} else {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("admin_dashboard.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			
			stage.setTitle("Admin Dashboard");
			stage.setScene(new Scene(root, 621, 424));
			stage.setResizable(false);
			stage.show();
		}
	}
	
	public void start(Stage primaryStage){
		stage = primaryStage;
	}
}
