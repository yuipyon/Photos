package view;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
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

public class LoginController {
	
	@FXML TextField UsernameBox;
	
	@FXML Button SubmitBox;
	
	Stage stage;
	
	ArrayList<User> userList = new ArrayList<User>();
	
	public void login(ActionEvent event) throws IOException {
		
		
		User falseUser = new User("admin");
		
		
		if(!UsernameBox.getText().equals("admin")) {
			User username = new User(UsernameBox.getText());
			userList.add(username);
			try {
				FileWriter wr = new FileWriter("user_data/usernames.txt");
				for(User user: userList) {
					wr.write(user + "\n");
					wr.flush();
				}
				wr.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
			
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("user_dashboard.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			
			stage.setTitle("User Dashboard");
			stage.setScene(new Scene(root, 800, 600));
			stage.setResizable(true);
			stage.show();
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
