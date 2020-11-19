package view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import app.Album;
import app.Photo;
import app.Tag;
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
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Serialization;

/**
 * @author Karun Kanda
 * @author Yulin Ni
 *
 */

/**
 * PhotoController class controls all the behaviors of the screen and button in the photo view screen
 */
public class PhotoController {
	
	/**
	 * Displays the image.
	 */
	@FXML ImageView PhotoView;
	
	/**
	 * Enables the user to go back when they are viewing the images.
	 */
	@FXML Button backButton;
	
	/**
	 * Enables the user to go forward when they are viewing the images.
	 */
	@FXML Button forwardButton;
	
	/**
	 * Enables the user to go back to the previous screen.
	 */
	@FXML Button GoBack;
	
	/**
	 * Allows the user to view the caption on this photo.
	 */
	@FXML Text caption;
	
	/**
	 * Allows the user to view the date modified.
	 */
	@FXML Text date;

	@FXML Text tags;
	
	/**
	 * Stores the current stage.
	 */
	Stage mainStage;
	
	/**
	 * Stores the current user.
	 */
	User curr_user;
	
	/**
	 * Stores the current album.
	 */
	Album curr_album;
	
	/**
	 * Stores the current photo.
	 */
	Photo curr_photo;
	
	/**
	 * Creates a serialization instance.
	 */
	Serialization serialController = new Serialization();
	
	/**
	 * Stores the photos from the search result.
	 */
	ArrayList<Photo> photos = new ArrayList<Photo>();
	
	/**
	 * Used when going back and forward when viewing the photos.
	 */
	int counter;
	int ind;
	String multiplicity;
	TagType tagtype; 
	
	/**
	 * Stores the tag type.
	 */
	String type;
	
	/**
	 * Stores the tag value.
	 */
	String value;
	
	/**
	 * Stores the default tag types.
	 */
	ArrayList<TagType> rawTypes;
	
	/**
	 * Stores the types in an observable list.
	 */

	ObservableList<String> types = FXCollections.observableArrayList();
	
	/**
	 * Enables the behavior to view a previous photo.
	 * @param e
	 */
	public void goBack(ActionEvent e) {
		counter--; 
		if (counter >= 0) {
			PhotoView.setImage(new Image(photos.get(counter).filepath));
		}
		else 
			counter++; 
	}
	
	/**
	 * Enables the behavior to view a preceding photo.
	 * @param e
	 */
	public void goForward(ActionEvent e) {
		counter++;
		if (counter < photos.size()) {
			PhotoView.setImage(new Image(photos.get(counter).filepath));
		}
		else 
			counter--;
	}
	
	/**
	 * Enables the behavior to go back to the previous screen.
	 * @param e
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
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
	 * Recieves the tag types.
	 * @param temp
	 */
	public void getTagTypeStrings(ArrayList<TagType> temp) {
		for (TagType t : temp) {
			types.add(t.toString());
		}
	}
	/**
	 * Enables the user to add a tag.
	 * @param e
	 */
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
		ComboBox cb = new ComboBox(FXCollections.observableArrayList(types));
		TextField tagValue = new TextField();
		tagValue.setPromptText("Enter tag value here.");
		TextField customType = new TextField();
		customType.setPromptText("Enter custom tag type.");
		Text text = new Text("multiplicity (if creating new custom tag type):");
		
		ArrayList<String> mult = new ArrayList<String>(Arrays.asList("single", "multiple"));
		ObservableList<String> m = FXCollections.observableArrayList(mult);
		ComboBox multChoiceBox = new ComboBox(FXCollections.observableArrayList(m));
		
		grid.add(l, 1, 0);
		GridPane.setMargin(l, new Insets(5, 0, 5, 0));
		
		grid.add(cb, 1, 1);
		GridPane.setMargin(cb, new Insets(5, 0, 5, 0));
		
		grid.add(tagValue, 2, 1);
		GridPane.setMargin(tagValue, new Insets(5, 0, 5, 0));
		
		grid.add(customType, 2, 2);
		GridPane.setMargin(customType, new Insets(5, 0, 5, 0));
		
		grid.add(multChoiceBox, 2, 3);
		GridPane.setMargin(multChoiceBox, new Insets(5, 0, 5, 0));
		
		grid.add(text, 1, 3);
		GridPane.setMargin(text, new Insets(5, 0, 5, 0));
		
		grid.setAlignment(Pos.CENTER);
		dialogPane.getButtonTypes().add(ButtonType.CANCEL);
		
		cb.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				type = arg0.getValue();
			}	   
	    });
		
		multChoiceBox.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				multiplicity = arg0.getValue();
			}	   
	    });
		dialogPane.setHeader(grid);
		
		boolean proceed = false;
		while(!proceed) {
			Optional<ButtonType> result = alert.showAndWait();
			if(result.get() == ButtonType.OK) {
				boolean proceed2 = false;
				if (type != null && !tagValue.getText().isBlank()) { // existing tag type
					proceed2 = checkValidTag(tagValue.getText());
				}
				else if (!customType.getText().isBlank() && !tagValue.getText().isBlank() && multiplicity != null) { //new tag type
					value = tagValue.getText(); 
					boolean trueMultiplicity = false; 
					//set type multiplicity
					if(multiplicity.equals("single"))
						trueMultiplicity = false; 
					else
						trueMultiplicity = true;
					tagtype = new TagType(customType.getText(), trueMultiplicity);
					curr_user.tagTypes.add(tagtype);
					proceed2 = true;
				}
				
				if (proceed2) { //create new tag
					Tag newTag = new Tag(tagtype, value);
					curr_photo.tags.add(newTag);
					proceed = true;
				}
				else {
					proceed = false;
					Alert a = new Alert(AlertType.INFORMATION);
					a.setTitle("Invalid input");
					a.setHeaderText("Invalid combinations inputted");
					a.setContentText("Please try again.");
					a.showAndWait();
				}
			}
			else if(result.get() == ButtonType.CANCEL) {
				type = "";
				value = "";
				proceed = true; 
			}
		}
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("photo view.fxml"));
		AnchorPane root = null;
		try {
			root = (AnchorPane) loader.load();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		PhotoController pc = loader.getController();
		try {
			pc.start(mainStage, ind, curr_photo);
		} catch (ClassNotFoundException | IOException ee) {
			// TODO Auto-generated catch block
			ee.printStackTrace();
		}
        Scene scene = new Scene(root,700,600);
        mainStage.setScene(scene);
        mainStage.setTitle("Photo View");
        mainStage.show();
	}
	
	public boolean checkValidTag(String tagValue) {
		boolean multiplicity = false;
		value = tagValue;
		//we have type, the String equivalent of the tag types present in the arraylist...
		for(TagType t : curr_user.tagTypes) { //get multiplicity from user's tagtype arraylist
			if (t.toString().equals(type)) {
				multiplicity = t.multiplicity;
				tagtype = t;
				break;
			}
		}
		if(!multiplicity) { //single multiplicity
			for(Tag t : curr_photo.tags) {
				if (t.getTagNameString().equals(type)) {
					Alert a = new Alert(AlertType.INFORMATION);
					a.setTitle("Invalid tag");
					a.setHeaderText("Only one tag of this type allowed.");
					a.setContentText("This photo already has a tag of this type. Please choose a different tag type.");
					a.showAndWait();
					return false; 
				}
			}
			return true;
		}
		else if (multiplicity) { //multiple multiplicity
			for(Tag t : curr_photo.tags) {
				if (t.getTagName().equals(type) && t.getTagValue().equals(value)) {
					Alert a = new Alert(AlertType.INFORMATION);
					a.setTitle("Duplicate tag");
					a.setHeaderText("Duplicate tag");
					a.setContentText("This photo already has this specific tag. Please choose another one.");
					a.showAndWait();
					return false; 
				}
			}
			return true; 
		}
		return false;
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
		ind = selectedIndex;
		for (int i = 0; i <= UserController.userList.size() - 1; i++) {
			if (UserController.userList.get(i).equals(curr_user)) {
				curr_user = UserController.userList.get(i);
				break;
			}
		}
		
		getTagTypeStrings(curr_user.tagTypes);
		
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
		
		curr_photo = p;
		
		PhotoView.setImage(new Image(p.filepath));
		caption.setText(p.caption);
		date.setText(p.getDate(p.date));
		tags.setText(p.printTags());
		
		
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
