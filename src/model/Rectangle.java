package model;

import java.awt.geom.Point2D;

/**
 * Represents a Rectangle.
 * @author florian
 *
 */
public class Rectangle {
	private Point2D.Double topleft;
	private double width;
	private double height;
	
	/**
	 * Creates a rectangle with the following parameters:
	 * @param topleft The point to the top left of the rectangle.
	 * @param width The width of the rectangle.
	 * @param height The height of the rectangle.
	 */
	public Rectangle(Point2D.Double topleft, double width, double height){
		this.topleft = topleft;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Creates a rectangle with the following parameters:
	 * @param topY The y-coordinate of the top left point of the rectangle.
	 * @param leftX The x-coordinate of the top left point of the rectangle.
	 * @param width The width of the rectangle.
	 * @param height The height of the rectangle.
	 */
	public Rectangle(double topY, double leftX, double width, double height){
		this.topleft = new Point2D.Double(leftX, topY);
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
	
	/**
	 * Checks if this rectangle does collide with the specified other rectangle r.
	 * @param r The rectangle to check.
	 * @return true if and only if the rectangles do collide.
	 */
	public boolean collidesWith(Rectangle r){
		return collide(this, r);
	}
}
