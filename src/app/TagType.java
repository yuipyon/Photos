package app;

import java.io.Serializable;

public class TagType implements Serializable {
	
	static final long serialVersionUID = -7005170714542958199L;
	
	String name; 
	/**
	 * boolean multiplicity determines the multiplicity of a particular tag type that is allowed per photo. 
	 * false = only one allowed per photo
	 * true = multiple allowed per photo
	 */
	public boolean multiplicity;

	public TagType(String name, boolean multiplicity) {
		this.name = name;
		this.multiplicity = multiplicity; 
	}
	
	public String toString() {
		return name; 
	}
}
