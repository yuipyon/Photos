package view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import app.Album;
import app.Photo;
import app.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Serialization;

/**
 * @author Karun Kanda
 * @author Yulin Ni
 */

/**
 * LoginController controls all the behavior of the screen and buttons in the
 * login screen.
 */
public class LoginController extends ActionEvent implements Serializable {

	/**
	 * TextField enables the user or admin to enter their login credentials.
	 */
	@FXML
	TextField UsernameBox;

	/**
	 * Button submitBox enables the user or admin to login.
	 */
	@FXML
	Button SubmitBox;

	/**
	 * Stage stage is used to switch between scenes in one stage.
	 */
	Stage stage;

	/**
	 * ArrayList<User> users holds the list of Users stored in usernames.ser
	 */
	ArrayList<User> userList = new ArrayList<User>();

	/**
	 * Creates a instance of the Serialization model to create serializable data to
	 * use between controllers.
	 */
	Serialization serialController = new Serialization();

	/**
	 * userExists finds if the user exists in the list of users.
	 * 
	 * @param username
	 * @param userList
	 * @return
	 */
	private boolean userExists(User username, ArrayList<User> userList) {
		if (userList.isEmpty()) {
			return false;
		}
		for (int i = 0; i <= userList.size() - 1; i++) {
			if (userList.get(i).equals(username)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * login enables the behavior for the user to login according to if their
	 * username credentials.
	 * 
	 * @param event
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void login(ActionEvent event) throws IOException, ClassNotFoundException {

		boolean userExist;
		User username = new User(UsernameBox.getText());

		if (username.toString().isBlank()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Invalid Input");
			alert.setHeaderText("Invalid input for username");
			alert.setContentText("Please include a valid username");
			alert.showAndWait();
		} else if (!UsernameBox.getText().equals("admin") && !UsernameBox.getText().equals("stock")) {
			userExist = userExists(username, userList);
			//System.out.println(userExist);

			if (userExist == true) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("user_dashboard.fxml"));
				AnchorPane root = (AnchorPane) loader.load();
				serialController.storeCurrentUser(username);
				// //System.out.println((User)serialController.readCurrentUser());

				UserController userController = loader.getController();
				userController.start(stage);

				stage.setTitle("User Dashboard");
				stage.setScene(new Scene(root, 800, 600));
				stage.setResizable(true);
				stage.show();
			} else {
				userList.add(username);
				serialController.storeUserList(userList);
				serialController.storeCurrentUser(username);
				//System.out.println((User) serialController.readCurrentUser());

				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("user_dashboard.fxml"));
				AnchorPane root = (AnchorPane) loader.load();

				UserController userController = loader.getController();
				userController.start(stage);

				stage.setTitle("User Dashboard");
				stage.setScene(new Scene(root, 800, 600));
				stage.setResizable(true);
				stage.show();
			}

		} else if (!UsernameBox.getText().equals("admin") && UsernameBox.getText().equals("stock")) {
			userExist = userExists(username, userList);
			//System.out.println(userExist);

			if (userExist == true) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("user_dashboard.fxml"));
				AnchorPane root = (AnchorPane) loader.load();
				serialController.storeCurrentUser(username);
				// //System.out.println((User)serialController.readCurrentUser());

				UserController userController = loader.getController();
				userController.start(stage);

				stage.setTitle("User Dashboard");
				stage.setScene(new Scene(root, 800, 600));
				stage.setResizable(true);
				stage.show();
			} else {
				ArrayList<Photo> photos = new ArrayList<Photo>();
				Photo one = new Photo();
				one.photo = new Image("file:data/soccerball.jpg");
				one.filepath = "data/soccerball.jpg";
				one.photoName = "Soccer Ball";
				
				Photo two = new Photo();
				two.photo = new Image("file:data/basketball.png");
				two.filepath = "data/basketball.png";
				two.photoName = "Basketball";
				
				Photo three = new Photo();
				three.photo = new Image("file:data/tennisball.jpeg");
				three.filepath = "data/tennisball.jpeg";
				three.photoName = "Tennis Ball";
				
				Photo four = new Photo();
				four.photo = new Image("file:data/cricketball.jpg");
				four.filepath = "data/cricketball.jpg";
				four.photoName = "Cricketball";
				
				Photo five = new Photo();
				five.photo = new Image("file:data/ship.jpg");
				five.filepath = "data/ship.jpg";
				five.photoName = "Rocketship";
				
				photos.add(one);
				photos.add(two);
				photos.add(three);
				photos.add(four);
				photos.add(five);
				
				ArrayList<Album> a = new ArrayList<Album>();
				Album al = new Album("stock");
				al.photos = photos;
				a.add(al);
				
				username.albums = a;
				userList.add(username);
				serialController.storeUserList(userList);
				serialController.storeCurrentUser(username);
				//System.out.println((User) serialController.readCurrentUser());

				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("user_dashboard.fxml"));
				AnchorPane root = (AnchorPane) loader.load();

				UserController userController = loader.getController();
				userController.start(stage);

				stage.setTitle("User Dashboard");
				stage.setScene(new Scene(root, 800, 600));
				stage.setResizable(true);
				stage.show();
			}
		} else {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("admin_dashboard.fxml"));
			AnchorPane root = (AnchorPane) loader.load();

			AdminController adminController = loader.getController();
			adminController.start(stage);

			stage.setTitle("Admin Dashboard");
			stage.setScene(new Scene(root, 621, 424));
			stage.setResizable(false);
			stage.show();
		}
	}

	/**
	 * start is what will occur upon starting the user dashboard scene.
	 * 
	 * @param primaryStage
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void start(Stage primaryStage) throws FileNotFoundException, ClassNotFoundException, IOException {
		this.stage = primaryStage;
		//System.out.println(stage);
		File file = new File("user_data/usernames.ser");
		if (userList == null) {

		} else {
			userList = serialController.readUserList();
		}
		
		primaryStage.setOnCloseRequest(event -> {
			try {
				Serialization.storeUserList(userList);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		//System.out.println(userList);

	}
}
