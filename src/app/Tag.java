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
public class Tag implements Serializable {
	
	/**
	 * String tagName stores the name of the tag (e.g: location).
	 */
	TagType tagName;
	
	/**
	 * String tagValue stores the value of the tag (e.g: New Brunswick).
	 */
	String tagValue;

	/**
	 * Creates a new instance of Tag and initializes it.
	 * @param tagName
	 * @param tagValue
	 */
	public Tag(TagType tagName, String tagValue) {
		this.tagName = tagName;
		this.tagValue = tagValue;
	}
	
	/**
	 * getTagName gets the name of the tag.
	 * @return String
	 */
	public TagType getTagName() {
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
		return tagName.toString() + ":" + tagValue;
	}
}
