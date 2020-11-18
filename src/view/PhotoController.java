package view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import app.Album;
import app.Photo;
import app.TagType;
import app.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Serialization;

public class PhotoController {
	
	@FXML ImageView PhotoView;
	@FXML Button backButton;
	@FXML Button forwardButton;
	@FXML Button GoBack;
	@FXML Text caption;
	@FXML Text date;
	
	BorderPane root = new BorderPane();
	FlowPane fp = new FlowPane();
	
	Stage mainStage;
	User curr_user;
	Album curr_album;
	Photo curr_photo;
	Serialization serialController = new Serialization();
	ArrayList<Photo> photos = new ArrayList<Photo>();
	int counter;
	String type, value;
	ArrayList<TagType> rawTypes;
	ObservableList<String> types = FXCollections.observableArrayList();
	
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
	
	public void getTagTypeStrings(ArrayList<TagType> temp) {
		for (TagType t : temp) {
			types.add(t.toString());
		}
	}
	
	public void addTag(ActionEvent e) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Add new tag");

		alert.initModality(Modality.APPLICATION_MODAL);
		alert.initOwner(mainStage);

		DialogPane dialogPane = alert.getDialogPane();
		GridPane grid = new GridPane();
		ColumnConstraints graphicColumn = new ColumnConstraints();
		ColumnConstraints textColumn = new ColumnConstraints();
		grid.getColumnConstraints().setAll(graphicColumn, textColumn);
		
		Label l = new Label("Please select a tag type (or create your own custom tag type) and input its matching value.");
		ComboBox cb = new ComboBox(FXCollections.observableArrayList(curr_user.tagTypes));
		TextField tagValue = new TextField();
		tagValue.setPromptText("Enter tag value here.");
		grid.add(l, 1, 0);
		GridPane.setMargin(l, new Insets(5, 0, 5, 0));
		grid.add(cb, 1, 1);
		GridPane.setMargin(cb, new Insets(5, 0, 5, 0));
		grid.add(tagValue, 2, 1);
		GridPane.setMargin(cb, new Insets(5, 0, 5, 0));
		grid.setAlignment(Pos.CENTER);
		dialogPane.getButtonTypes().add(ButtonType.CANCEL);
		
		cb.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				type = arg0.getValue();
			}	   
	    });
		
		dialogPane.setHeader(grid);
		
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK) { 
			value = tagValue.getText();
		}
		else if(result.get() == ButtonType.CANCEL) {
			type = "";
			value = "";
		}
	}

	
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
		
		rawTypes = curr_user.tagTypes;
		getTagTypeStrings(rawTypes);
		
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
		PhotoView.setImage(new Image(p.filepath));
		caption.setText(p.caption);
		date.setText(p.getDate(p.date));

		fp.setHgap(10);
		fp.setVgap(15);
		root.setBottom(fp);
	}
}
