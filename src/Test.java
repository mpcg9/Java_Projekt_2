import java.util.*;
import recttosvg.*;
import model.*;

public class Test {

//	public static void main(String[] args) {
//		LinkedList<PointDescriptor> p = new LinkedList<PointDescriptor>();
//		p.add(new PointDescriptor(20, 5, 50, 8));
//		p.add(new PointDescriptor(35, 7, 50, 8));
//		p.add(new PointDescriptor(25, 15, 50, 8));
//		p.add(new PointDescriptor(15, 3, 50, 8));
//		p.add(new PointDescriptor(40, 20, 50, 8));
//		
//		PointDescriptorSet pSet = new PointDescriptorSet(p);
//		List<PointDescriptor> p2 = pSet.getPoints();
//		
//		for(PointDescriptor point : p2) System.out.println("("+point.getBotleft().x+"|"+point.getBotleft().y+")");
//		
//		recttosvg.saveSVG(p2);
//	}
	
	public static void main(String[] args){
		PointDescriptorSet points = new PointDescriptorSet(CsvReader.readFile("auswahl_benelux.csv"));
		
		double timeElapsed;
		double startTime;
		
		startTime = System.currentTimeMillis();
		points.findCollisionsSweepLine();
		timeElapsed = System.currentTimeMillis() - startTime;
		System.out.println("Sweep-Line-Algortihmus: Rechendauer " + timeElapsed + "ms");
		
		startTime = System.currentTimeMillis();
		points.findCollisionsBruteForce();
		timeElapsed = System.currentTimeMillis() - startTime;
		System.out.println("Brute-Force-Algortihmus: Rechendauer " + timeElapsed + "ms");
		
		double minScale = points.findMinimumScaleFactor();
//		for(PointDescriptor point : points) System.out.println("("+point.getBotleft().x+"|"+point.getBotleft().y+")");
		
		System.out.println(minScale);
		
		recttosvg.saveSVG(points, "Rechtecke.svg");
		recttosvg.saveSVG(points.getScaledPoints(minScale), "RechteckeSkaliert.svg");
		}

}
