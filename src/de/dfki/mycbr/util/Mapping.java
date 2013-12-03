package de.dfki.mycbr.util;

/**
 * class to represent the mapping between data base attributes and myCBR attributes
 * @author myCBR Team, Pascal Reuss
 *
 */
public class Mapping {
	
	private String source;
	private String target;
	
	public Mapping(String source, String target) {
		this.source = source;
		this.target = target;
	}
	
	public void setSource(String s) {
		this.source = s;
	}
	
	public void setTarget(String t) {
		this.target = t;
	}
	
	public String getSource() {
		return this.source;
	}
	
	public String getTarget() {
		return this.target;
	}

}
