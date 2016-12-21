package model;

import java.awt.geom.Point2D;

/**
 * Represents a Rectangle.
 * @author florian
 */
public class Rectangle {
	private Point2D.Double botleft;
	private double width;
	private double height;
	
	/**
	 * Creates a rectangle with the following parameters:
	 * @param botleft The point to the bottom left of the rectangle.
	 * @param width The width of the rectangle.
	 * @param height The height of the rectangle.
	 */
	public Rectangle(Point2D.Double botleft, double width, double height){
		this.botleft = botleft;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Creates a rectangle with the following parameters:
	 * @param topY The y-coordinate of the bottom left point of the rectangle.
	 * @param leftX The x-coordinate of the bottom left point of the rectangle.
	 * @param width The width of the rectangle.
	 * @param height The height of the rectangle.
	 */
	public Rectangle(double topY, double leftX, double width, double height){
		this.botleft = new Point2D.Double(leftX, topY);
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Checks two rectangles on whether or not they do collide.
	 * @param r1 The first rectangle to check.
	 * @param r2 The second rectangle to check.
	 * @return true if and only if the rectangles do collide.
	 */
	public static boolean collide(Rectangle r1, Rectangle r2){
		if(r1.height + r1.botleft.y < r2.botleft.y)
			return false;
		if(r2.height + r2.botleft.y < r1.botleft.y)
			return false;
		if(r1.width + r1.botleft.x < r2.botleft.x)
			return false;
		if(r2.width + r2.botleft.x < r1.botleft.x)
			return false;
		return true;
	}
	
	/**
	 * Checks if this rectangle does collide with the specified other rectangle r.
	 * @param r The rectangle to check.
	 * @return true if and only if the rectangles do collide.
	 */
	public boolean collidesWith(Rectangle r){
		return collide(this, r);
	}
}
