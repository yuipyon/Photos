package app;

import java.io.Serializable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

/**
 * @author Karun Kanda
 * @author Yulin Ni
 */

/**
 * The Tag class is where the tagName and tagValue will be stored for any photo.
 */
public class Tag extends Label implements Serializable {
	
	/**
	 * String tagName stores the name of the tag (e.g: location).
	 */
	String tagName;
	
	/**
	 * String tagValue stores the value of the tag (e.g: New Brunswick).
	 */
	String tagValue;

	/**
	 * Creates a new instance of Tag.
	 * @param tagName
	 * @param tagValue
	 */
	public Tag(String tagName, String tagValue) {
		this.tagName = tagName;
		this.tagValue = tagValue;
	}
	
	/**
	 * Creates a new instance of Tag. (w/o arguments)
	 */
	public Tag() {
		super();
		initTag();
	}

	/**
	 * Creates a new private instance of a Tag.
	 * @param arg0
	 * @param arg1
	 */
	private Tag(String arg0, Node arg1) {
		super(arg0, arg1);
	}

	/**
	 * Creates an instance of Tag and initializes the tag.
	 * @param arg0
	 */
	public Tag(String arg0) {
		super(arg0);
		initTag();
	}

	/**
	 * initTag initializes a tag.
	 */
	private final void initTag(){
		Path path = new Path();
		/**
		 * you will need to increase the 5 if you want the close button to be big
		 */
		path.getElements().addAll(
				new MoveTo(0,0),new LineTo(5, 5),
				new MoveTo(5,0), new LineTo(0,5));
		path.setStyle("-fx-stroke: red;");
		path.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent paramT) {
				Node n = Tag.this.getParent();
				if(n instanceof Pane){//of course it is
					((Pane)n).getChildren().remove(Tag.this);
				}
			}
		});
		setPadding(new Insets(3,5,3,5));
		setGraphic(path);
		setContentDisplay(ContentDisplay.RIGHT);
		setGraphicTextGap(8);
		graphicProperty().addListener(new ChangeListener<Node>() {
			@Override
			public void changed(ObservableValue<? extends Node> paramObservableValue,
					Node paramT1, Node paramT2) {
				if(paramT2 != path){
					setGraphic(path);
				}
			}
		});

		setStyle("-fx-background-color: gold; "
				+ "-fx-border-radius: 3;"
				+ "-fx-border-color: red;");
	}

	
	/**
	 * getTagName gets the name of the tag.
	 * @return String
	 */
	public String getTagName() {
		return tagName;
	}
	
	/**
	 * getTagValue gets the value of the tag.
	 * @return String
	 */
	public String getTagValue() {
		return tagValue;
	}
	
	/**
	 * Returns the tag name and tag value.
	 */
	public String toString() {
		return tagName + ":" + tagValue;
	}
}
