package view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

import app.User;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Serialization;

public class AlbumController implements Serializable {
	
	Stage stage;
	@FXML Button add;
	@FXML Button delete; 
	@FXML Button moveCopy;
	@FXML Button recaption;
	@FXML ListView albumsView; 
	@FXML TextField photoName;
	@FXML TextField albumName;
	@FXML TextField caption;
	@FXML Button back;
	@FXML Button logout;
	
	User curr_user;
	
	
	public void add(ActionEvent e) {
		
	}
	
	public void delete(ActionEvent e) {}
	public void moveCopy(ActionEvent e) {}
	public void recaption(ActionEvent e) {}
	
	/**
	 * logout enables the user to logout from the session.
	 * @param event
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 */
	public void logout(ActionEvent e) throws ClassNotFoundException, IOException {
		Serialization.storeUserList(UserController.userList);
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("Login.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		stage.close();
		
		LoginController lg = loader.getController();
		Stage ns = new Stage();
		
		lg.start(ns);
		ns.setTitle("Login");
		ns.setScene(new Scene(root, 621, 424));
		ns.setResizable(true);
		ns.show();
	}
	
	public void back(ActionEvent e) throws IOException, ClassNotFoundException {
		FXMLLoader loader = new FXMLLoader();
		Parent parent = FXMLLoader.load(getClass().getResource("user_dashboard.fxml"));
		Scene scene = new Scene(parent);
		loader.setLocation(getClass().getResource("user_dashboard.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		UserController controller = loader.getController();
		controller.start(stage);
		stage.setScene(scene);
		stage.setTitle("User Dashboard");
		stage.show();
	}
	
	/**
	 * start is what will occur upon starting the admin dashboard scene.
	 * @param primaryStage
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws FileNotFoundException 
	 */
	public void start(Stage primaryStage) throws FileNotFoundException, ClassNotFoundException, IOException{
		this.stage = primaryStage;

		curr_user = Serialization.readCurrentUser();

		for (int i = 0; i <= UserController.userList.size() - 1; i++) {
			if (UserController.userList.get(i).equals(curr_user)) {
				curr_user = UserController.userList.get(i);
			}
		}

		if(curr_user.getUserName().equals("stock")) {
			
		}

	}
}
