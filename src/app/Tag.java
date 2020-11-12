package app;

import java.io.Serializable;

public class Tag implements Serializable{
	
	String tagName;
	String tagValue;

	public Tag(String tagName, String tagValue) {
		this.tagName = tagName;
		this.tagValue = tagValue;
	}
	
	public String getTagName() {
		return tagName;
	}
	
	public String getTagValue() {
		return tagValue;
	}
	
	public String toString() {
		return tagName + ":" + tagValue;
	}
}
