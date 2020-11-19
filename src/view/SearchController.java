package view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Serialization;

/**
 * @author Karun Kanda
 * @author Yulin Ni
 */

/**
 * The Search Controller controls all the behaviors of the screen and button in the search photos screen.
 */
public class SearchController implements Serializable {
	
	/**
	 * Stores two options: and or or.
	 */
	@FXML ComboBox andOr;
	
	/**
	 * Displays the first tag.
	 */
	@FXML Text tag1;
	
	/**
	 * Displays the second tag.
	 */
	@FXML Text tag2;
	
	/**
	 * Displays the results from the date search or tag search.
	 */
	@FXML ListView searchResults;
	
	/**
	 * Enable the user to pick a ending date.
	 */
	@FXML DatePicker dateTo;
	
	/**
	 * Enables the user to pick a start date.
	 */
	@FXML DatePicker dateFrom;
	
	/**
	 * Enables the user to add a tag.
	 */
	@FXML Button addTagButton;
	
	/**
	 * Enables the user to remove a tag.
	 */
	@FXML Button removeTagButton;
	
	/**
	 * Enables the user to search upon a tag search or date search.
	 */
	@FXML Button searchButton;
	
	/**
	 * Enables the user to create an album based on the photo result.
	 */
	@FXML Button createAlbumButton;
	
	/**
	 * Enables the user to input a tag type.
	 */
	@FXML TextField tType;
	
	/**
	 * Enables the user to input a tag value.
	 */
	@FXML TextField tValue;
	
	/**
	 * Enables the user to go back to the previous screen. 
	 */
	@FXML Button backButton;
	
	/**
	 * Stage stage is used to store the primaryStage.
	 */
	Stage stage;
	
	/**
	 * Serialization serialController creates a serialization object to store data.
	 */
	Serialization serialController = new Serialization();
	
	/**
	 * Stores the choices for the ComboBox.
	 */
	ArrayList<String> choices = new ArrayList<String>();
	
	/**
	 * Stores the value that is choosen from the combo box.
	 */
	String item = null;
	
	/**
	 * Stores the starting date.
	 */
	LocalDate to = null; 
	
	/**
	 * Stores the ending date.
	 */
	LocalDate from = null;
	
	/**
	 * Formats the localdate to mm/dd/yyyy.
	 */
	DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	
	/**
	 * Stores the current user that is in the session.
	 */
	User curr_user = null;
	
	/**
	 * Stores the photo search result list in an observable list.
	 */
	ObservableList<Photo> photos = FXCollections.observableArrayList();
	
	/**
	 * Stores the search result.
	 */
	ArrayList<Photo> photoList = new ArrayList<Photo>();
	
	
	/**
	 * Enables the behavior to go back to the previous screen.
	 * @param e
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void back(ActionEvent e) throws FileNotFoundException, IOException, ClassNotFoundException {
		FXMLLoader loader = new FXMLLoader();
		serialController.storeUserList(UserController.userList);
        loader.setLocation(getClass().getResource("user_dashboard.fxml"));
        AnchorPane root = (AnchorPane) loader.load();
        UserController controller = loader.getController();
        controller.start(stage);
        stage.setScene(new Scene(root, 800, 600));
        stage.setTitle("User Dashboard");
        stage.show();
	}
	
	
	/**
	 * Adds a tag.
	 * @param e
	 */
	public void addTag(ActionEvent e) {
		String type = tType.getText();
		String value = tValue.getText();
		Tag newTag = new Tag(new TagType(type, false), value);
		if (tag1.getText().isBlank()) {
			tag1.setText(newTag.toString());
		}
		else {
			tag2.setText(newTag.toString());
		}
		tType.setText("");
		tValue.setText("");
	}
	
	/**
	 * Removes a tag.
	 * @param e
	 */
	public void removeTag(ActionEvent e) {
		if (tag1.getText().isBlank())
			tag2.setText("");
		else
			tag1.setText("");
	}
	
	
	/**
	 * Creates an album from the search results.
	 * @param e
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void createAlbum(ActionEvent e) throws FileNotFoundException, IOException {
		TextInputDialog dialog = new TextInputDialog();
		dialog.initOwner(stage);
		dialog.setTitle("Create Album");
		dialog.setHeaderText("Make a new Album.");
		dialog.setContentText("Enter album name: ");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			String albumName = result.get();
			Album newAlbum = new Album(albumName);
			boolean albumExists = UserController.albumExist(newAlbum, curr_user.albums);
			newAlbum.photos = photoList;
			//System.out.println(albumExists);

			if (albumExists == true) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Duplicate album");
				alert.setHeaderText("A duplicate album entry was entered");
				alert.setContentText("Please include another non-duplicate album");
				alert.showAndWait();
			} else {
				if (albumName.length() == 0) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Invalid Input");
					alert.setHeaderText("No album name was given");
					alert.setContentText("Please include a valid album name");
					alert.showAndWait();
				} else {
					newAlbum.startingDateRange = LocalDate.now();
					newAlbum.endingDateRange = LocalDate.now();
					curr_user.albums.add(newAlbum);
					UserController.userList = UserController.updateAlbum(curr_user, UserController.userList);
					serialController.storeUserList(UserController.userList);
				}
			}
		}
	}
	
	/**
	 * Creates a search result based on the date range inputted or tags.
	 * @param e
	 */
	public void search(ActionEvent e) {
		//System.out.println(to.format(dateFormatter) + " - " + from.format(dateFormatter));
		if(to != null && from != null) {
			for(int i = 0; i<=curr_user.albums.size() - 1; i++) {
				if(curr_user.albums.get(i).getDateRange().equals(from.format(dateFormatter) + " - " + to.format(dateFormatter))) {
					//System.out.println("match");
					photoList = curr_user.albums.get(i).photos;
				}
			}
			photos = FXCollections.observableList(photoList);
			searchResults.setItems(photos);
			searchResults.setCellFactory(param -> new ListCell<Photo>() {
	            private ImageView imageView = new ImageView();
	            @Override
	            public void updateItem(Photo name, boolean empty) {
	                super.updateItem(name, empty);
	                if (empty) {
	                    setText(null);
	                    setGraphic(null);
	                } else {
	                    for (Photo photo:photoList) {
	                    	imageView.setImage(new Image(name.filepath));
		                    imageView.setFitWidth(100);
		            	    imageView.setFitHeight(100);
	                    }
	                    setText(name.photoName);
	                    setGraphic(imageView);
	                }
	            }
	        });
			
		}
	}
	
	/**
	 * Stores the starting date.
	 * @param e
	 */
	public void dateFromAction(ActionEvent e) {
		if(dateFrom.getValue() != null) {
			from = dateFrom.getValue();
			//System.out.println(from);
		}
	}
	
	/**
	 * Stores the ending date.
	 * @param e
	 */
	public void ToDateAction(ActionEvent e) {
		if(dateTo.getValue() != null)
			to = dateTo.getValue();
			//System.out.println(to);
	}	
	
	/**
	 * start is what will occur upon starting the search photos scene.
	 * @param primaryStage
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws FileNotFoundException 
	 */
	public void start(Stage stage) throws FileNotFoundException, ClassNotFoundException, IOException {
		
		curr_user = Serialization.readCurrentUser();
		
		choices.add("and");
		choices.add("or");
		this.stage = stage;
		andOr.getItems().add(choices.get(0));
		andOr.getItems().add(choices.get(1));
		andOr.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				item = arg0.getValue();
			}
	    });
		
		for(int i = 0; i <= UserController.userList.size() - 1; i++) {
			if(UserController.userList.get(i).equals(curr_user)) {
				curr_user = UserController.userList.get(i);
			}
		}
		
		stage.setOnCloseRequest(event -> {
			try {
				Serialization.storeUserList(UserController.userList);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	
	
}

