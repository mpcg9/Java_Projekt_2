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
		this.setBotleft(botleft);
		this.setWidth(width);
		this.setHeight(height);
	}
	
	/**
	 * Creates a rectangle with the following parameters:
	 * @param topY The y-coordinate of the bottom left point of the rectangle.
	 * @param leftX The x-coordinate of the bottom left point of the rectangle.
	 * @param width The width of the rectangle.
	 * @param height The height of the rectangle.
	 */
	public Rectangle(double topY, double leftX, double width, double height){
		this.setBotleft(new Point2D.Double(leftX, topY));
		this.setWidth(width);
		this.setHeight(height);
	}
	
	/**
	 * Creates a Rectangle with no specific arguments.
	 * Do not use this unless you know what you're doing and don't fear NullPointerExceptions!
	 */
	public Rectangle() {
		
	}

	/**
	 * Checks two rectangles on whether or not they do collide.
	 * @param r1 The first rectangle to check.
	 * @param r2 The second rectangle to check.
	 * @return true if and only if the rectangles do collide.
	 */
	public static boolean collide(Rectangle r1, Rectangle r2){
		if(r1.getHeight() + r1.getBotleft().y < r2.getBotleft().y)
			return false;
		if(r2.getHeight() + r2.getBotleft().y < r1.getBotleft().y)
			return false;
		if(r1.getWidth() + r1.getBotleft().x < r2.getBotleft().x)
			return false;
		if(r2.getWidth() + r2.getBotleft().x < r1.getBotleft().x)
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

	/**
	 * @return the width
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(double width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(double height) {
		this.height = height;
	}

	/**
	 * @return the botleft
	 */
	public Point2D.Double getBotleft() {
		return botleft;
	}

	/**
	 * @param botleft the botleft to set
	 */
	public void setBotleft(Point2D.Double botleft) {
		this.botleft = botleft;
	}
}
