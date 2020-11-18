package view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import app.Album;
import app.Tag;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
	
	@FXML ComboBox andOr;
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
	ArrayList<String> choices = new ArrayList<String>();
	String item = null;
	
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
	
	public void dateFromAction(ActionEvent e) {
		System.out.println(dateFrom.getValue());
	}
	
	public void ToDateAction(ActionEvent e) {
		System.out.println(dateTo.getValue());
	}	
	
	public void start(Stage stage) {
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
	}
	
	
}

