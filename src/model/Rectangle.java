package model;

import java.awt.geom.Point2D;

public class Rectangle {
	private Point2D.Double topleft;
	private double width;
	private double height;
	
	public Rectangle(Point2D.Double topleft, double width, double height){
		this.topleft = topleft;
		this.width = width;
		this.height = height;
	}
	
	public Rectangle(double topY, double leftX, double width, double height){
		this.topleft = new Point2D.Double(leftX, topY);
		this.width = width;
		this.height = height;
	}
	
	public static boolean collide(Rectangle r1, Rectangle r2){
		if(r1.height + r1.topleft.y < r2.topleft.y)
			return false;
		if(r2.height + r2.topleft.y < r1.topleft.y)
			return false;
		if(r1.width + r1.topleft.x < r2.topleft.x)
			return false;
		if(r2.width + r2.topleft.x < r1.topleft.x)
			return false;
		return true;
	}
	
	public boolean collidesWith(Rectangle r){
		return collide(this, r);
	}
}
