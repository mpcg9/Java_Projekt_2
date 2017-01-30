package model;
import java.util.*;

public class PointDescriptorSet {
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
	
	// TODO: implement scale factor finding algorithm
	
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
		
		//TODO: implement minimum scale factor finding method.
	}

	public List<PointDescriptor> getPoints() {
		return points;
	}

	public void setPoints(List<PointDescriptor> points) {
		this.points = points;
	}
}
