package app;

import view.LoginController;

import java.lang.ModuleLayer.Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * @author Karun Kanda
 * @author Yulin Ni
 */

/**
 * Photos class is where the main method will go and initiate the application. 
 */
public class Photos extends Application{
	
	/**
	 * Starts the program and sets the primaryStage.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/Login.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		
		LoginController controller = loader.getController();
		controller.start(primaryStage);
		
		primaryStage.setTitle("Photos");
		primaryStage.setScene(new Scene(root, 621, 424));
		primaryStage.setResizable(false);
		primaryStage.show();
		
		
	}
	
	/**
	 * The main method is where the application will start from.
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
