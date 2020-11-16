package view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

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
	
	public void add(ActionEvent e) {
		
	}
	public void delete(ActionEvent e) {}
	public void moveCopy(ActionEvent e) {}
	public void recaption(ActionEvent e) {}
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
}
