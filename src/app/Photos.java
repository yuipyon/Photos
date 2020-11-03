package app;

import view.LoginController;

import java.lang.ModuleLayer.Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Photos extends Application{
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
	
	public static void main(String[] args) {
		launch(args);
		
	}
}
