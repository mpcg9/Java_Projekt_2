package model;
import java.util.*;

public class PointDescriptorSet {
	private TreeSet<PointDescriptor> points;
	private LinkedList<PointDescriptorPair> collisions;
	
	public PointDescriptorSet(Collection<PointDescriptor> points){
		this.points = new TreeSet<PointDescriptor>(points);
	}
	
	public void findCollisionsSweepLine(){
		
		//TODO: implement sweep-line  collision detection algorithm.
	}
	
	//TODO: implement brute-force collision detection algorithm.
	
	//TODO: implement minimum scale factor finding algorithm (see below).
	
	private class EventPoint{
		
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

	public TreeSet<PointDescriptor> getPoints() {
		return points;
	}

	public void setPoints(TreeSet<PointDescriptor> points) {
		this.points = points;
	}
}
