package view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import app.Photo;
import app.User;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Serialization;

public class PhotoController {
	
	@FXML ImageView PhotoView;
	@FXML Button backButton;
	@FXML Button forwardButton;
	//This is to go back a screen
	@FXML Button GoBack;
	@FXML TextField captionBox;
	@FXML TextField dateBox;
	@FXML TextField tagBox;
	
	Stage mainStage;
	User curr_user;
	Serialization serialController = new Serialization();
	ArrayList<Photo> photos = new ArrayList<Photo>();
	int forwardCounter = 0;
	int backCounter = photos.size() - 1;
	
	public void goBack(ActionEvent e) {
		forwardCounter--;
		PhotoView.setImage(new Image(curr_user.albums.get(0).photos.get(forwardCounter).filepath));
	}
	
	public void goForward(ActionEvent e) {
		forwardCounter++;
		PhotoView.setImage(new Image(curr_user.albums.get(0).photos.get(forwardCounter).filepath));
	}
	
	public void backScreen(ActionEvent e) throws FileNotFoundException, ClassNotFoundException, IOException {
		Serialization.storeUserList(UserController.userList);
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("user_dashboard.fxml"));
		AnchorPane root = (AnchorPane) loader.load();
		UserController controller = loader.getController();
		controller.start(mainStage);
		mainStage.setScene(new Scene(root, 621, 424));
		mainStage.setTitle("User Dashboard");
		mainStage.show();
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

		mainStage = primaryStage;
		
		curr_user = serialController.readCurrentUser();

		for (int i = 0; i <= UserController.userList.size() - 1; i++) {
			if (UserController.userList.get(i).equals(curr_user)) {
				curr_user = UserController.userList.get(i);
			}
		}
		
		photos = curr_user.albums.get(0).photos;
		
		if(curr_user.getUserName().equals("stock")) {
			PhotoView.setImage(new Image(curr_user.albums.get(0).photos.get(0).filepath));
		}
		
	}

}
