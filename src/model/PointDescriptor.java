package model;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

public class PointDescriptor extends Rectangle implements Comparable<PointDescriptor>{
	private String description;
	
	public PointDescriptor() {
		super();
	}
	
	public PointDescriptor(Point2D.Double botleft, double width, double height) {
		super(botleft, width, height);
	}

	public PointDescriptor(double bottomY, double leftX, double width, double height) {
		super(bottomY, leftX, width, height);
	}
	
	public PointDescriptor(String description, Point2D.Double descriptionPoint){
		super();
		this.setHeight(12); //Beispieltextgroesse
		this.setWidth(description.length() * 4); 
		//TODO: Automatisches Erkennen von Textbreite und -hoehe.
		this.setBotleft(descriptionPoint);
		this.description = description;
	}

	@Override
	public int compareTo(PointDescriptor arg0) {
		double diff = this.getBotleft().y - arg0.getBotleft().y;
		return (int) Math.signum(diff);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
}
