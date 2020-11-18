package view;

import java.io.Serializable;

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
	
	String[] choices = {"and", "or"};
	Serialization serialController = new Serialization();
	andOr.setItems(choices);
	
	public void back(ActionEvent e) {
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
	
	public void start(Stage stage) {
		this.stage = stage;
	}
	
}
