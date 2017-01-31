package model;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PointDescriptorSet implements Iterable<PointDescriptor>{
	private List<PointDescriptor> points;
	private List<PointDescriptorPair> collisions;
	
	private static final Lock LOCK1 = new ReentrantLock();
	private static final Lock LOCK2 = new ReentrantLock();
	
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
//						System.out.println(e.point.getDescription() + ", " + p.getDescription() + ", " + new PointDescriptorPair(p, e.point).getMinimumScaleFactor());
					}
				}
				statusCrowd.add(e.point);
			}
			else statusCrowd.remove(e.point);
		}
//		System.out.println(this.collisions.size() + " Kollisionen gefunden");
	}
	
	public void findCollisionsBruteForce(){
		this.collisions = new LinkedList<PointDescriptorPair>();
		PointDescriptor[] pointArray = this.points.toArray(new PointDescriptor[this.points.size()]);
		for(int i = 0; i < pointArray.length; i++){
			PointDescriptor p = pointArray[i];
			for(int j = i + 1; j < pointArray.length; j++){
				if(p.collidesWith(pointArray[j])){
					this.collisions.add(new PointDescriptorPair(p, pointArray[j]));
//					System.out.println(p.getDescription() + ", " + pointArray[j].getDescription() + ", " + new PointDescriptorPair(p, pointArray[j]).getMinimumScaleFactor());
				}
			}
		}
//		System.out.println(this.collisions.size() + " Kollisionen gefunden");
	}
	
	public void findCollisionsSweepLineMultithreaded(){
		this.collisions = new LinkedList<PointDescriptorPair>();
		TreeSet<EventPoint> events = new TreeSet<EventPoint>();
		PointDescriptor[] pointArray = this.points.toArray(new PointDescriptor[this.points.size()]);
		int[] currentIndex = {0};
		
		Thread[] t = new Thread[Runtime.getRuntime().availableProcessors()];
		for(int i = 0; i < t.length; i++){
			EventMaker c = new EventMaker(currentIndex, events, pointArray);
			t[i] = new Thread(c);
			t[i].start();
		}
		for(Thread tx : t){
			try {
				tx.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		ArrayList<PointDescriptor> statusCrowd = new ArrayList<PointDescriptor>();
		LinkedList<Thread> threads = new LinkedList<Thread>();
		for(EventPoint e : events){
			if(e.isStartPoint){
				SweepLineCollisionFinder f = new SweepLineCollisionFinder(e.point, statusCrowd.toArray(new PointDescriptor[statusCrowd.size()]), this.collisions);
				Thread tn = new Thread(f);
				tn.start();
				
				statusCrowd.add(e.point);
				threads.add(tn);
			}
			else statusCrowd.remove(e.point);
		}
		for(Thread tx : threads){
			try {
				tx.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
//		System.out.println(this.collisions.size() + " Kollisionen gefunden");
	}
	
	public void findCollisionsBruteForceMultithreaded(){
		int[] currentIndex = {0}; // Implementation als Array, da so Referenzen übergeben werden.
		this.collisions = new LinkedList<PointDescriptorPair>();
		PointDescriptor[] pointArray = this.points.toArray(new PointDescriptor[this.points.size()]);
		
		Thread[] t = new Thread[Runtime.getRuntime().availableProcessors()];
		for(int i = 0; i < t.length; i++){
			CollisionSearcher c = new CollisionSearcher(currentIndex, collisions, pointArray);
			t[i] = new Thread(c);
			t[i].start();
		}
		for(Thread tx : t){
			try {
				tx.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
//		System.out.println(this.collisions.size() + " Kollisionen gefunden");
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
			else if(point1.getBotleft().y > point2.getBotleft().y){
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
			else if(point1.getBotleft().x > point2.getBotleft().x){
				length = point2.getWidth();
				x1 = point2.getBotleft().x;
				x2 = point1.getBotleft().x;
			}
			else return Double.MAX_VALUE;
			return length/(x2-x1);
		}
	}

	private class CollisionSearcher implements Runnable{
		private int[] currentIndex;
		private List<PointDescriptorPair> resultList;
		private PointDescriptor[] points;
		
		public CollisionSearcher(int[] index, List<PointDescriptorPair> collisions, PointDescriptor[] points){
			this.currentIndex = index;
			this.points = points;
			this.resultList = collisions;
		}
		
		@Override
		public void run() {
			int i;
			
			LOCK1.lock();
			i = currentIndex[0];
			currentIndex[0] ++ ;
			LOCK1.unlock();
			
			while(i < points.length){
//				System.out.println(i);
				for(int j = i+1; j < points.length; j++){
					if(points[i].collidesWith(points[j])){
						LOCK2.lock();
						this.resultList.add(new PointDescriptorPair(points[i], points[j]));
						LOCK2.unlock();
					}
				}
				LOCK1.lock();
				i = currentIndex[0];
				currentIndex[0] ++ ;
				LOCK1.unlock();
			}
		}
		
	}
	
	private class EventMaker implements Runnable{
		private int[] currentIndex;
		private TreeSet<EventPoint> events;
		private PointDescriptor[] pointArray;
		
		public EventMaker(int[] currentIndex, TreeSet<EventPoint> events, PointDescriptor[] pointArray) {
			this.currentIndex = currentIndex;
			this.events = events;
			this.pointArray = pointArray;
		}

		@Override
		public void run(){
			
			LOCK1.lock();
			int i = currentIndex[0];
			currentIndex[0] = i+1;
			LOCK1.unlock();
			
			while(i < pointArray.length){
				PointDescriptor p = pointArray[i];
				
				double yStart = p.getBotleft().y;
				double yEnde = p.getBotleft().y + p.getHeight();
				
				EventPoint start = new EventPoint(true, yStart, p);
				LOCK2.lock();
				events.add(start);
				LOCK2.unlock();
				
				EventPoint ende  = new EventPoint(false, yEnde, p);
				LOCK2.lock();
				events.add(ende);
				LOCK2.unlock();
				
				LOCK1.lock();
				i = currentIndex[0];
				currentIndex[0] = i+1;
				LOCK1.unlock();
			}
		}
	}
	
	private class SweepLineCollisionFinder implements Runnable{
		private List<PointDescriptorPair> resultList;
		private PointDescriptor[] statusCrowd;
		private PointDescriptor p;
		public SweepLineCollisionFinder(PointDescriptor p, PointDescriptor[] statusCrowd, List<PointDescriptorPair> resultList) {
			this.resultList = resultList;
			this.statusCrowd = statusCrowd;
			this.p = p;
		}
		
		public void run(){
			for(PointDescriptor t : statusCrowd){
				if(p.collidesWith(t)){
					PointDescriptorPair collision = new PointDescriptorPair(p, t);
					LOCK2.lock();
					resultList.add(collision);
					LOCK2.unlock();
				}
			}
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
