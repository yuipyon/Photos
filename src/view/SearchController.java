package view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

import app.Tag;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Serialization;

public class SearchController implements Serializable {

	String[] choices = {"and", "or"};
	
	@FXML ComboBox andOr = new ComboBox(FXCollections.observableArrayList(choices));;
	@FXML Text tag1;
	@FXML Text tag2;
	@FXML ListView searchResults;
	@FXML DatePicker dateTo;
	@FXML DatePicker dateFrom;
	@FXML Button addTagButton;
	@FXML Button removeTagButton;
	@FXML Button searchButton;
	@FXML Button createAlbumButton;
	@FXML TextField tType;
	@FXML TextField tValue;
	@FXML Button backButton;
	
	Stage stage;
	
	Serialization serialController = new Serialization();
	
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
	
	public void addTag(ActionEvent e) {
		String type = tType.getText();
		String value = tValue.getText();
		Tag newTag = new Tag(type, value);
		if (tag1 == null) {
			tag1.setText(newTag.toString());
		}
		else {
			tag2.setText(newTag.toString());
		}
		tType.setText("");
		tValue.setText("");
	}
	
	public void removeTag(ActionEvent e) {
		if (tag1.getText().isBlank())
			tag2.setText("");
		else
			tag1.setText("");
	}
	
	public void createAlbum(ActionEvent e) {
		
	}
	
	public void search(ActionEvent e) {
		
	}
	
	public void start(Stage stage) {
		this.stage = stage;
	}
	
}

