package view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import app.Album;
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
	@FXML Button GoBack;
	@FXML TextField captionBox;
	@FXML TextField dateBox;
	@FXML TextField tagBox;
	
	Stage mainStage;
	User curr_user;
	Album curr_album;
	Serialization serialController = new Serialization();
	ArrayList<Photo> photos = new ArrayList<Photo>();
	int counter = 0;
	
	public void goBack(ActionEvent e) {
		if (counter > 0) {
			counter--;
			PhotoView.setImage(new Image(photos.get(counter).filepath));
		}
	}
	
	public void goForward(ActionEvent e) {
		if (counter < photos.size()) {
			counter++;
			PhotoView.setImage(new Image(photos.get(counter).filepath));
		}
	}
	
	public void backScreen(ActionEvent e) throws FileNotFoundException, ClassNotFoundException, IOException {
		Serialization.storeUserList(UserController.userList);
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("Album_Display.fxml"));
		AnchorPane root = (AnchorPane) loader.load();
		AlbumController controller = loader.getController();
		controller.start(mainStage);
		mainStage.setScene(new Scene(root, 600, 520));
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

	public void start(Stage primaryStage, int selectedIndex) throws FileNotFoundException, IOException, ClassNotFoundException {

		mainStage = primaryStage;
		
		curr_user = serialController.readCurrentUser();

		for (int i = 0; i <= UserController.userList.size() - 1; i++) {
			if (UserController.userList.get(i).equals(curr_user)) {
				curr_user = UserController.userList.get(i);
				break;
			}
		}
		curr_album = serialController.readCurrentAlbum();
		int index = 0;
		for (int i = 0; i < curr_user.albums.size(); i++) {
			if (curr_user.albums.get(i).equals(curr_album)) {
				index = i; 
				curr_album = curr_user.albums.get(index);
				break;
			}
		}
		photos = curr_album.photos;
		System.out.println(photos.size());
		PhotoView.setImage(new Image(photos.get(selectedIndex).filepath));
		
	}

}
