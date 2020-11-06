package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import app.Album;
import app.User;


@SuppressWarnings("serial")
public class UserController implements Serializable{
	
	@FXML TextField name;
	@FXML TextField date;
	@FXML TextField numPhotos;
	@FXML Button searchButton;
	@FXML Button deleteButton;
	@FXML Button createButton;
	@FXML Button openButton;
	@FXML Button logoutButton;
	@FXML ListView albumList;
	
	private ObservableList<Album> albums = FXCollections.observableArrayList();
	private ArrayList<Album> albumLists = new ArrayList<Album>();
	
	public void logout(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("Login.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		Stage s = new Stage();
		s.setTitle("Login");
		s.setScene(new Scene(root, 621, 424));
		s.setResizable(true);
		s.show();
	}
	
	public void create(ActionEvent e) throws ParseException {
		/*String albumName = name.getText();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMM-dd-yyyy");
		Date date1 = dateFormat.parse(date.getText());
		int numPhotos1 = Integer.parseInt(numPhotos.getText());
		Album newAlbum = new Album(albumName, numPhotos1, date1);
		albumLists.add(newAlbum);
		albums = FXCollections.observableList(albumLists);
		albumList.setItems(albums);*/
	}
	
	public void start(Stage primaryStage){
		
	}
	
	public void search(ActionEvent e) {}
	public void delete(ActionEvent e) {}
	public void open(ActionEvent e) {}


}
