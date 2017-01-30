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
		double minScale = points.findMinimumScaleFactor();
		System.out.println("Minimaler Zoomfaktor Benelux: " + minScale);
		recttosvg.saveSVG(points, "RechteckeBenelux.svg");
		recttosvg.saveSVG(points.getScaledPoints(minScale), "RechteckeSkaliertBenelux.svg");
		
		points = new PointDescriptorSet(CsvReader.readFile("auswahl_schweiz.csv"));
		minScale = points.findMinimumScaleFactor();
		System.out.println("Minimaler Zoomfaktor Schweiz: " + minScale);
		recttosvg.saveSVG(points, "RechteckeSchweiz.svg");
		recttosvg.saveSVG(points.getScaledPoints(minScale), "RechteckeSkaliertSchweiz.svg");
		
		// this takes a f***ing eternity to execute. Uncomment for demonstration purposes!
		RuntimeTester.createRuntimeAnalysis("Runtime_Analysis.csv", 5000, 1000, 30000);
		System.out.println("finished execution");
		
//		for(PointDescriptor point : points) System.out.println("("+point.getBotleft().x+"|"+point.getBotleft().y+")");
		
		

		}

}
