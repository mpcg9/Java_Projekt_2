package model;
import java.util.*;

public class PointDescriptorSet implements Iterable<PointDescriptor>{
	private List<PointDescriptor> points;
	private List<PointDescriptorPair> collisions;
	
	public PointDescriptorSet(Collection<PointDescriptor> points){
		this.points = new LinkedList<PointDescriptor>(points);
	}
	
	public void findCollisionsSweepLine(){
		this.collisions = new LinkedList<PointDescriptorPair>();
		
		// Erstellen des EventPoint - Suchbaums
		TreeSet<EventPoint> events = new TreeSet<EventPoint>();
		for(PointDescriptor p : points){
			double yStart = p.getBotleft().y;
			double yEnde = p.getBotleft().y + p.getHeight();
			
			events.add(new EventPoint(true, yStart, p));
			events.add(new EventPoint(false, yEnde, p));
		}
		
		// Suchen nach Kollisionen
		LinkedList<PointDescriptor> statusCrowd = new LinkedList<PointDescriptor>();
		for(EventPoint e : events){
			if(e.isStartPoint){
				for(PointDescriptor p : statusCrowd){
					if(e.point.collidesWith(p)){
						this.collisions.add(new PointDescriptorPair(p, e.point));
					}
				}
				statusCrowd.add(e.point);
			}
			else statusCrowd.remove(e.point);
		}
	}
	
	public void findCollisionsBruteForce(){
		this.collisions = new LinkedList<PointDescriptorPair>();
		PointDescriptor[] pointArray = this.points.toArray(new PointDescriptor[this.points.size()]);
		for(int i = 0; i < pointArray.length; i++){
			PointDescriptor p = pointArray[i];
			for(int j = i + 1; j < pointArray.length; j++){
				if(p.collidesWith(pointArray[j])){
					this.collisions.add(new PointDescriptorPair(p, pointArray[j]));
				}
			}
		}
		
	}
	
	public double findMinimumScaleFactor(){
		if(this.collisions == null) this.findCollisionsSweepLine();
		double minScaleFactor = Double.MIN_VALUE;
		for(PointDescriptorPair p : this.collisions){
			double pScale = p.getMinimumScaleFactor();
			if(pScale > minScaleFactor) minScaleFactor = pScale;
		}
		return minScaleFactor;
	}
	
	public List<PointDescriptor> getScaledPoints(double scaleFactor){
		LinkedList<PointDescriptor> list = new LinkedList<PointDescriptor>();
		for(PointDescriptor p : this.points){
			PointDescriptor pNeu = new PointDescriptor(p);
			pNeu.getBotleft().x *= scaleFactor;
			pNeu.getBotleft().y *= scaleFactor;
			list.add(pNeu);
		}
		return list;
	}
	
	private class EventPoint implements Comparable<EventPoint>{
		public boolean isStartPoint;
		public double y;
		public PointDescriptor point;
		
		public EventPoint(boolean isStartPoint, double y, PointDescriptor point) {
			this.isStartPoint = isStartPoint;
			this.y = y;
			this.point = point;
		}

		@Override
		public int compareTo(EventPoint o) {
			if(this.y > o.y) return  1;
			if(o.y > this.y) return -1;
			return 0;
		}
		
	}
	
	private class PointDescriptorPair{
		private PointDescriptor point1;
		private PointDescriptor point2;
		
		public PointDescriptorPair(PointDescriptor p1, PointDescriptor p2){
			this.point1 = p1;
			this.point2 = p2;
		}
		
		public PointDescriptor p1(){
			return point1;
		}
		
		public PointDescriptor p2(){
			return point2;
		}
		
		public double getMinimumScaleFactor(){
			return Math.min(getMinimumScaleFactorX(), getMinimumScaleFactorY());
		}
		
		private double getMinimumScaleFactorY(){
			double height, y1, y2;
			if(point1.getBotleft().y < point2.getBotleft().y){
				height = point1.getHeight();
				y1 = point1.getBotleft().y;
				y2 = point2.getBotleft().y;
			}
			else if(point1.getBotleft().y != point1.getBotleft().y){
				height = point2.getHeight();
				y1 = point2.getBotleft().y;
				y2 = point1.getBotleft().y;
			}
			else return Double.MAX_VALUE;
			return height/(y2-y1);
		}
		
		private double getMinimumScaleFactorX(){
			double length, x1, x2;
			if(point1.getBotleft().x < point2.getBotleft().x){
				length = point1.getWidth();
				x1 = point1.getBotleft().x;
				x2 = point2.getBotleft().x;
			}
			else if(point1.getBotleft().x != point1.getBotleft().x){
				length = point2.getWidth();
				x1 = point2.getBotleft().x;
				x2 = point1.getBotleft().x;
			}
			else return Double.MAX_VALUE;
			return length/(x2-x1);
		}
	}

	public List<PointDescriptor> getPoints() {
		return points;
	}

	public void setPoints(List<PointDescriptor> points) {
		this.points = points;
	}

	@Override
	public Iterator<PointDescriptor> iterator() {
		return this.points.iterator();
	}
}
