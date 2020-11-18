package view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import app.Album;
import app.Photo;
import app.User;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Serialization;

public class PhotoController {
	
	@FXML ImageView PhotoView;
	@FXML Button backButton;
	@FXML Button forwardButton;
	@FXML Button GoBack;
	@FXML Text caption;
	@FXML Text date;
	@FXML Text tags;
	
	Stage mainStage;
	User curr_user;
	Album curr_album;
	Photo curr_photo;
	Serialization serialController = new Serialization();
	ArrayList<Photo> photos = new ArrayList<Photo>();
	int counter;
	
	public void goBack(ActionEvent e) {
		counter--; 
		if (counter >= 0) {
			PhotoView.setImage(new Image(photos.get(counter).filepath));
		}
		else 
			counter++; 
	}
	
	public void goForward(ActionEvent e) {
		counter++;
		if (counter < photos.size()) {
			PhotoView.setImage(new Image(photos.get(counter).filepath));
		}
		else 
			counter--;
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
	 * Enables the user to add a tag.
	 * @param e
	 */
	public void addTag(ActionEvent e) {
		String newTag = "";
		TextInputDialog dialog = new TextInputDialog();
		dialog.initOwner(mainStage);
		dialog.setTitle("Add tag");
		dialog.setHeaderText("Choose an existing tag or input a new tag name.");
		dialog.setContentText("New caption:");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			System.out.println(result.get());
			curr_photo.caption = null;
			curr_photo.caption = "\"" + result.get() + "\"";
			photos.add(curr_photo);
			
		}
	}

	//	public void addTag(ActionEvent e) {
//		String newTag = "";
//		TextInputDialog dialog = new TextInputDialog();
//		dialog.initOwner(mainStage);
//		dialog.setTitle("Add tag");
//		dialog.setHeaderText("Choose an existing tag or input a new tag name.");
//		dialog.setContentText("New caption:");
//		Optional<String> result = dialog.showAndWait();
//		if (result.isPresent()) {
//			photo.caption = "\"" + result.get() + "\"";
//			photoList.set(selectedIndex, photo);
//			photos = FXCollections.observableList(photoList);
//			albumsView.setItems(photos);
//		}
//	}

	
	/**
	 * start is what will occur upon starting the user dashboard scene.
	 * 
	 * @param primaryStage
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */

	public void start(Stage primaryStage, int selectedIndex, Photo p) throws FileNotFoundException, IOException, ClassNotFoundException {
		mainStage = primaryStage;
		curr_user = serialController.readCurrentUser();
		counter = selectedIndex;
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
		
		for(int i = 0; i<= curr_album.photos.size() - 1; i++) {
			if(curr_album.photos.get(i).equals(curr_photo)) {
				curr_photo = curr_album.photos.get(i);
				break;
			}
		}
		
		PhotoView.setImage(new Image(p.filepath));
		caption.setText("\"" + p.caption + "\"");
		date.setText(p.getDate(p.date));
//		tags.setText(p.tags);
		primaryStage.setOnCloseRequest(event -> {
			try {
				Serialization.storeUserList(UserController.userList);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
}
