package view;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import app.Album;
import app.Photo;
import app.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Serialization;

public class AlbumController implements Serializable {

	Stage stage;
	@FXML
	Button add;
	@FXML
	Button delete;
	@FXML
	Button moveCopy;
	@FXML
	Button recaption;
	@FXML
	ListView albumsView;
	@FXML
	TextField photoName;
	@FXML
	TextField albumName;
	@FXML
	TextField caption;
	@FXML
	Button back;
	@FXML
	Button openButton;
	@FXML
	Button logout;

	User curr_user;
	ObservableList<Photo> photos = FXCollections.observableArrayList();
	ArrayList<Photo> photoList = new ArrayList<Photo>();
	Serialization serialController = new Serialization();
	
	private ArrayList<Album> updateAlbumPt1(Album curr_album, ArrayList<Album> albumList) {
		for (int i = 0; i <= albumList.size() - 1; i++) {
			System.out.println(albumList.get(i));
			if (albumList.get(i).getName().equals(curr_album.getName())) {
				albumList.remove(i);
				break;
			}
		}
		albumList.add(curr_album);
		return albumList;
	}

	public void add(ActionEvent e) throws FileNotFoundException, IOException, ClassNotFoundException {
		Desktop desktop = Desktop.getDesktop();
		FileChooser fc = new FileChooser();
		fc.setTitle("Add photo");
		fc.setInitialDirectory(new File(System.getProperty("user.home")));
		fc.getExtensionFilters().addAll(new ExtensionFilter("All Images", "*.*"));
		File file = fc.showOpenDialog(stage);
        if (file != null) {
        	Photo newPhoto = new Photo();
        	newPhoto.filepath = file.getAbsolutePath();
        	newPhoto.photoName = file.getName();
        	newPhoto.date = newPhoto.setDate(file);
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
	                    	imageView.setImage(new Image("file:" + name.filepath));
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
        }
	}

	protected Alert displayImage(Photo p) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(p.photoName);

		alert.initModality(Modality.APPLICATION_MODAL);
		alert.initOwner(stage);

		DialogPane dialogPane = alert.getDialogPane();
		GridPane grid = new GridPane();
		ColumnConstraints graphicColumn = new ColumnConstraints();
		ColumnConstraints textColumn = new ColumnConstraints();
		grid.getColumnConstraints().setAll(graphicColumn, textColumn);

		Image ni = new Image(p.filepath);
		ImageView imageView = new ImageView(ni);
		imageView.setFitWidth(400);
		imageView.setFitHeight(400);
		StackPane stackPane = new StackPane(imageView);
		grid.add(stackPane, 0, 0);

		dialogPane.setHeader(grid);
		dialogPane.setGraphic(imageView);

		alert.showAndWait();

		return alert;
	}

	public void openImage(ActionEvent e) {
		int selectedIndex = albumsView.getSelectionModel().getSelectedIndex();
		if (selectedIndex != -1) {
			Photo photoToView = (Photo) albumsView.getSelectionModel().getSelectedItem();
			int newSelectedIndex = (selectedIndex == albumsView.getItems().size() - 1) ? selectedIndex - 1
					: selectedIndex;
			displayImage(photoToView);
		}
	}

	public void delete(ActionEvent e) throws FileNotFoundException, ClassNotFoundException, IOException {
		int selectedIndex = albumsView.getSelectionModel().getSelectedIndex();
		if (selectedIndex != -1) {
			Photo photoToView = (Photo) albumsView.getSelectionModel().getSelectedItem();
			int newSelectedIndex = (selectedIndex == albumsView.getItems().size() - 1) ? selectedIndex - 1
					: selectedIndex;
			photoList.remove(selectedIndex);
			photos = FXCollections.observableList(photoList);
			albumsView.setItems(photos);
		}
		Album curr_album = Serialization.readCurrentAlbum();
		curr_album.photos = photoList;
		curr_album.getStartingDateRange();
		curr_album.getEndingDateRange();
		curr_user.albums = updateAlbumPt1(curr_album, curr_user.albums);
		UserController.userList = UserController.updateAlbum(curr_user, UserController.userList);
		Serialization.storeUserList(UserController.userList);
	}

	public void moveCopy(ActionEvent e) {
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
		Button move = new Button("move");
		Button copy = new Button("copy");
		
		grid.add(l, 0, 0);
		grid.add(move, 0, 0);
		grid.add(copy, 0, 0);
		grid.setAlignment(Pos.BASELINE_CENTER);

		/*Image ni = new Image(p.filepath);
		ImageView imageView = new ImageView(ni);
		imageView.setFitWidth(400);
		imageView.setFitHeight(400);
		StackPane stackPane = new StackPane(imageView);
		grid.add(stackPane, 0, 0);*/

		dialogPane.setHeader(grid);

		alert.showAndWait();

  
	}

	public void recaption(ActionEvent e) {
	
	}

	/**
	 * logout enables the user to logout from the session.
	 * 
	 * @param event
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void logout(ActionEvent e) throws ClassNotFoundException, IOException {
		FXMLLoader loader = new FXMLLoader();
		LoginController lg = loader.getController();
		serialController.storeUserList(UserController.userList);

		loader.setLocation(getClass().getResource("Login.fxml"));
		AnchorPane root = (AnchorPane) loader.load();
		stage.close();

		Stage ns = new Stage();

		lg.start(ns);
		ns.setTitle("Login");
		ns.setScene(new Scene(root, 449, 365));
		ns.setResizable(true);
		ns.show();
	}

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
		Album curr_album = Serialization.readCurrentAlbum();

		for (int i = 0; i <= UserController.userList.size() - 1; i++) {
			if (UserController.userList.get(i).equals(curr_user)) {
				curr_user = UserController.userList.get(i);
			}
		}

		if(curr_user.getUserName().equals("stock")) {
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
			albumsView.setOnMouseClicked(new EventHandler<MouseEvent>() {

			    @Override
			    public void handle(MouseEvent click) {

			        if (click.getClickCount() == 2) {
			        	int selectedIndex = albumsView.getSelectionModel().getSelectedIndex();
			    		if (selectedIndex != -1) {
			    			Photo curr_album = (Photo) albumsView.getSelectionModel().getSelectedItem();
			    			//System.out.println(curr_album);
			    			int newSelectedIndex = (selectedIndex == albumsView.getItems().size() - 1) ? selectedIndex - 1
			    					: selectedIndex;
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
								pc.start(stage);
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
	                    	imageView.setImage(new Image("file:"+name.filepath));
		                    imageView.setFitWidth(100);
		            	    imageView.setFitHeight(100);
	                    }
	                    setText(name.photoName);
	                    setGraphic(imageView);
	                }
	            }
	        });
			albumsView.setOnMouseClicked(new EventHandler<MouseEvent>() {

			    @Override
			    public void handle(MouseEvent click) {

			        if (click.getClickCount() == 2) {
			        	int selectedIndex = albumsView.getSelectionModel().getSelectedIndex();
			    		if (selectedIndex != -1) {
			    			Photo curr_album = (Photo) albumsView.getSelectionModel().getSelectedItem();
			    			//System.out.println(curr_album);
			    			int newSelectedIndex = (selectedIndex == albumsView.getItems().size() - 1) ? selectedIndex - 1
			    					: selectedIndex;
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
								pc.start(stage);
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
		}

	}
}
