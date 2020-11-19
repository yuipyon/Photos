package view;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;

import app.Album;
import app.Photo;
import app.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Serialization;

/**
 * @author Karun Kanda
 * @author Yulin Ni
 *
 */

/**
 * The Album Controller class controls the behaviors of the screens and buttons in the Album Display.
 */
public class AlbumController implements Serializable {

	/**
	 * Stage stage is used to store the primaryStage.
	 */
	Stage stage;
	
	/**
	 * Button add enables the user to add a new photo.
	 */
	@FXML
	Button add;
	
	/**
	 * Button delete enables the user to delete an photo.
	 */
	@FXML
	Button delete;
	
	/**
	 * Button moveCopy enables the user to move/copy a photo.
	 */
	@FXML
	Button moveCopy;
	
	/**
	 * Button recaption enables the user to recaption a photo.
	 */
	@FXML
	Button recaption;
	
	/**
	 * ListView albumsView allows the user to view their photos in an album.
	 */
	@FXML
	ListView albumsView;
	
	/**
	 * Text albumTitle displays the album title.
	 */
	@FXML
	Text albumTitle;
	
	/**
	 * Button back enables the user to go back to the user dashboard.
	 */
	@FXML
	Button back;
	
	/**
	 * Button openButton enables the user to the user to open a photo.
	 */
	@FXML
	Button openButton;
	
	/**
	 * Button logout enables the user to logout of their session.
	 */
	@FXML
	Button logout;

	/**
	 * User curr_user stores the current user.
	 */
	User curr_user;
	
	/**
	 * ObservableList<Photo> photo stores the photo list in an observable list.
	 */
	ObservableList<Photo> photos = FXCollections.observableArrayList();
	
	/**
	 * Stores the list of photos in the choosen album.
	 */
	ArrayList<Photo> photoList = new ArrayList<Photo>();
	
	/**
	 * Serialization serialController creates a serialization object to store data.
	 */
	Serialization serialController = new Serialization();
	
	/**
	 * Album toAdd stores the album to add in the move/copy method.
	 */
	Album toAdd = null;
	
	/**
	 * Album curr_album stores the current album.
	 */
	Album curr_album;
	
	/**
	 * Updates the album information.
	 * @param curr_album
	 * @param albumList
	 * @return
	 */
	private ArrayList<Album> updateAlbumPt1(Album curr_album, ArrayList<Album> albumList) {
		for (int i = 0; i <= albumList.size() - 1; i++) {
			if (albumList.get(i).getName().equals(curr_album.getName())) {
				albumList.remove(i);
				break;
			}
		}
		albumList.add(curr_album);
		return albumList;
	}

	/**
	 * Adds a new photos directly from the user's computer.
	 * @param e
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void add(ActionEvent e) throws FileNotFoundException, IOException, ClassNotFoundException {
		Desktop desktop = Desktop.getDesktop();
		FileChooser fc = new FileChooser();
		fc.setTitle("Add photo");
		fc.setInitialDirectory(new File(System.getProperty("user.home")));
		fc.getExtensionFilters().addAll(new ExtensionFilter("All Images", "*.*"));
		File file = fc.showOpenDialog(stage);
        if (file != null) {
        	Photo newPhoto = new Photo();
        	newPhoto.filepath = "file:" + file.getAbsolutePath();
        	newPhoto.photoName = file.getName();
        	newPhoto.date = newPhoto.setDate(file);
        	
        	boolean exists = false;
        	for(int i = 0; i<= photoList.size() - 1; i++) {
        		if(photoList.get(i).filepath.equals(newPhoto.filepath)) {
        			exists = true;
        			break;
        		}
        	}
        	
        	if(exists == true) {
        		Alert alert = new Alert(AlertType.INFORMATION);
    			alert.setTitle("Photo already exists");
    			alert.setHeaderText("This photo already exists in this album");
    			alert.setContentText("Please choose another photo.");
    			alert.showAndWait();
        		photos = FXCollections.observableList(photoList);
    			albumsView.setItems(photos);
    			albumsView.setCellFactory(param -> new ListCell<Photo>() {
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
    			Album curr_album = Serialization.readCurrentAlbum();
    			curr_album.photos = photoList;
    			curr_album.getStartingDateRange();
    			curr_album.getEndingDateRange();
    			curr_user.albums = updateAlbumPt1(curr_album, curr_user.albums);
    			UserController.userList = UserController.updateAlbum(curr_user, UserController.userList);
    			Serialization.storeUserList(UserController.userList);
    			
    			photos = FXCollections.observableList(photoList);
    			albumsView.setItems(photos);
        	} else {
        		photoList.add(newPhoto);
            	photos = FXCollections.observableList(photoList);
    			albumsView.setItems(photos);
    			albumsView.setCellFactory(param -> new ListCell<Photo>() {
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
    			Album curr_album = Serialization.readCurrentAlbum();
    			curr_album.photos = photoList;
    			curr_album.getStartingDateRange();
    			curr_album.getEndingDateRange();
    			curr_user.albums = updateAlbumPt1(curr_album, curr_user.albums);
    			UserController.userList = UserController.updateAlbum(curr_user, UserController.userList);
    			Serialization.storeUserList(UserController.userList);
    			
    			photos = FXCollections.observableList(photoList);
    			albumsView.setItems(photos);
        	}
        	
        	
        }
	}

	/**
	 * Deletes a photo from the user's album.
	 * @param e
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void delete(ActionEvent e) throws FileNotFoundException, ClassNotFoundException, IOException {
		int selectedIndex = albumsView.getSelectionModel().getSelectedIndex();
		if (selectedIndex != -1) {
			Photo photoToView = (Photo) albumsView.getSelectionModel().getSelectedItem();
			photoList.remove(selectedIndex);
			if(photoList.size() == 0) {
				photos = FXCollections.observableList(photoList);
				albumsView.setItems(photos);
				Album curr_album = Serialization.readCurrentAlbum();
				curr_album.photos = photoList;
				curr_user.albums = updateAlbumPt1(curr_album, curr_user.albums);
				UserController.userList = UserController.updateAlbum(curr_user, UserController.userList);
				Serialization.storeUserList(UserController.userList);
			} else {
				photos = FXCollections.observableList(photoList);
				albumsView.setItems(photos);
				Album curr_album = Serialization.readCurrentAlbum();
				curr_album.photos = photoList;
				curr_album.getStartingDateRange();
				curr_album.getEndingDateRange();
				curr_user.albums = updateAlbumPt1(curr_album, curr_user.albums);
				UserController.userList = UserController.updateAlbum(curr_user, UserController.userList);
				Serialization.storeUserList(UserController.userList);
			}
		}
		
	}

	/**
	 * Moves or copies a photo into an album depending on the user's choice.
	 * @param e
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void moveCopy(ActionEvent e) throws FileNotFoundException, ClassNotFoundException, IOException {
		int selectedIndex = albumsView.getSelectionModel().getSelectedIndex();
		if (selectedIndex != -1) {
			Photo movePhoto = (Photo) albumsView.getSelectionModel().getSelectedItem();
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Move/Copy Action");

			alert.initModality(Modality.APPLICATION_MODAL);
			alert.initOwner(stage);

			DialogPane dialogPane = alert.getDialogPane();
			GridPane grid = new GridPane();
			ColumnConstraints graphicColumn = new ColumnConstraints();
			ColumnConstraints textColumn = new ColumnConstraints();
			grid.getColumnConstraints().setAll(graphicColumn, textColumn);
			
			Label l = new Label("Move/Copy to which album?");
			ComboBox cb = new ComboBox(FXCollections.observableArrayList(curr_user.albums));
			
			
			grid.add(l, 1, 0);
			GridPane.setMargin(l, new Insets(5, 0, 5, 0));
			grid.add(cb, 1, 1);
			GridPane.setMargin(cb, new Insets(5, 0, 5, 0));
			grid.setAlignment(Pos.CENTER);
			
			dialogPane.getButtonTypes().add(ButtonType.CANCEL);
			((Button) dialogPane.lookupButton(ButtonType.OK)).setText("Copy");
			((Button) dialogPane.lookupButton(ButtonType.CANCEL)).setText("Move");
			
			
			cb.valueProperty().addListener(new ChangeListener<Album>() {
				@Override
				public void changed(ObservableValue<? extends Album> arg0, Album arg1, Album arg2) {
					toAdd = arg0.getValue();
				}	   
		    });
			
			
			dialogPane.setHeader(grid);
		
			Optional<ButtonType> result = alert.showAndWait();
			if(result.get() == ButtonType.OK) { //copy function
				if(toAdd.photos.size() == 0) {
					toAdd.photos = new ArrayList<Photo>();
					toAdd.photos.add(movePhoto);
				} else {
					toAdd.photos.add(movePhoto);
				}
			} 
			
			if(result.get() == ButtonType.CANCEL) { //move function
				if(toAdd.photos.size() == 0) {
					toAdd.photos = new ArrayList<Photo>();
					toAdd.photos.add(movePhoto);
					photoList.remove(movePhoto);
					photos = FXCollections.observableList(photoList);
					albumsView.setItems(photos);
					Album curr_album = Serialization.readCurrentAlbum();
					curr_album.photos = photoList;
					curr_user.albums = updateAlbumPt1(curr_album, curr_user.albums);
					UserController.userList = UserController.updateAlbum(curr_user, UserController.userList);
					Serialization.storeUserList(UserController.userList);
					
				} else {
					toAdd.photos.add(movePhoto);
					photoList.remove(movePhoto);
					photos = FXCollections.observableList(photoList);
					albumsView.setItems(photos);
					Album curr_album = Serialization.readCurrentAlbum();
					curr_album.photos = photoList;
					curr_user.albums = updateAlbumPt1(curr_album, curr_user.albums);
					UserController.userList = UserController.updateAlbum(curr_user, UserController.userList);
					Serialization.storeUserList(UserController.userList);
				}
			}
		}

  
	}
	
	/**
	 * Recaptions a individual photo.
	 * @param e
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void recaption(ActionEvent e) throws FileNotFoundException, ClassNotFoundException, IOException {
		int selectedIndex = albumsView.getSelectionModel().getSelectedIndex();
		if (selectedIndex != -1) {
			Photo photo = (Photo) albumsView.getSelectionModel().getSelectedItem();
			TextInputDialog dialog = new TextInputDialog();
			dialog.initOwner(stage);
			dialog.setTitle("Recaption photo");
			dialog.setHeaderText("Input your desired caption.");
			dialog.setContentText("New caption:");
			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()) {
				photo.caption = "\"" + result.get() + "\"";
				photoList.set(selectedIndex, photo);
				photos = FXCollections.observableList(photoList);
				albumsView.setItems(photos);
				Album curr_album = Serialization.readCurrentAlbum();
				curr_album.photos = photoList;
				curr_user.albums = updateAlbumPt1(curr_album, curr_user.albums);
				UserController.userList = UserController.updateAlbum(curr_user, UserController.userList);
				Serialization.storeUserList(UserController.userList);
			}
		}
	}

	/**
	 * logout enables the user to logout from the session.
	 * @param event
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void logout(ActionEvent e) throws ClassNotFoundException, IOException {
		serialController.storeUserList(UserController.userList);

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("Login.fxml"));
		AnchorPane root = (AnchorPane) loader.load();

		stage.close();

		LoginController lg = loader.getController();
		Stage ns = new Stage();

		lg.start(ns);
		ns.setTitle("Login");
		ns.setScene(new Scene(root, 449, 365));
		ns.setResizable(true);
		ns.show();
	}

	/**
	 * Enables the behavior to go back to the previous screen.
	 * @param e
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void back(ActionEvent e) throws IOException, ClassNotFoundException {
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
	 * start is what will occur upon starting the admin dashboard scene.
	 * @param primaryStage
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws FileNotFoundException 
	 */
	public void start(Stage primaryStage) throws FileNotFoundException, ClassNotFoundException, IOException{
		this.stage = primaryStage;

		curr_user = Serialization.readCurrentUser();
		curr_album = Serialization.readCurrentAlbum();

		albumTitle.setText("Album View - " + curr_album.getName());
		
		
		
		for (int i = 0; i <= UserController.userList.size() - 1; i++) {
			if (UserController.userList.get(i).equals(curr_user)) {
				curr_user = UserController.userList.get(i);
			}
		}

		/*if(curr_user.getUserName().equals("stock")) {
			photoList = curr_user.albums.get(0).photos;
			photos = FXCollections.observableList(photoList);
			albumsView.setItems(photos);
			albumsView.setCellFactory(param -> new ListCell<Photo>() {
	            private ImageView imageView = new ImageView();
	            @Override
	            public void updateItem(Photo name, boolean empty) {
	                super.updateItem(name, empty);
	                if (empty) {
	                    setText(null);
	                    setGraphic(null);
	                } else {
	                	for (Photo photo:photoList) {
	                    	imageView.setImage(new Image("file:"+name.filepath));
		                    imageView.setFitWidth(100);
		            	    imageView.setFitHeight(100);
	                    }
	                    setText(name.photoName);
	                    setGraphic(imageView);
	                }
	            }
	        });
		} else {
			
		} */
		if (curr_album.photos == null) {
			photos = FXCollections.observableList(photoList);
			albumsView.setItems(photos);
		} else {
			photoList = curr_album.photos;
			photos = FXCollections.observableList(photoList);
			albumsView.setItems(photos);
			albumsView.setCellFactory(param -> new ListCell<Photo>() {
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
	                    setText(name.photoName + "\n" + "Caption: " + name.caption);
	                    setGraphic(imageView);
	                }
	            }
	        });
		}
		albumsView.setOnMouseClicked(new EventHandler<MouseEvent>() {

		    @Override
		    public void handle(MouseEvent click) {

		        if (click.getClickCount() == 2) {
		        	int selectedIndex = albumsView.getSelectionModel().getSelectedIndex();
		    		if (selectedIndex != -1) {
		    			Photo selectedPhoto = (Photo) albumsView.getSelectionModel().getSelectedItem();
		    			FXMLLoader loader = new FXMLLoader();
		    			loader.setLocation(getClass().getResource("photo view.fxml"));
		    			AnchorPane root = null;
						try {
							root = (AnchorPane) loader.load();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		    			
		    			PhotoController pc = loader.getController();
		    			try {
							pc.start(stage, selectedIndex, selectedPhoto);
						} catch (ClassNotFoundException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		    			
		    			stage.setTitle("Photos View");
		    			stage.setScene(new Scene(root, 700, 600));
		    			stage.setResizable(true);
		    			stage.show();

		    		}
		        }
		    }
		});
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
			