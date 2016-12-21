package model;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

public class PointDescriptor extends Rectangle {
	private String description;
	
	public PointDescriptor(Point2D.Double botleft, double width, double height) {
		super(botleft, width, height);
	}

	public PointDescriptor(double topY, double leftX, double width, double height) {
		super(topY, leftX, width, height);
	}
	
	public PointDescriptor(String description, Point2D.Double descriptionPoint){
		super();
		this.setHeight(12); //Beispieltextgroesse
		this.setWidth(description.length() * 4); 
		//TODO: Automatisches Erkennen von Textbreite und -hoehe.
		this.setBotleft(descriptionPoint);
		this.description = description;
	}

}
