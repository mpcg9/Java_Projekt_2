import java.awt.Point;
import java.util.*;
import recttosvg.*;
import model.*;

public class Test {

	public static void main(String[] args) {
		LinkedList<PointDescriptor> p = new LinkedList<PointDescriptor>();
		p.add(new PointDescriptor(20, 5, 50, 8));
		p.add(new PointDescriptor(35, 7, 50, 8));
		p.add(new PointDescriptor(25, 15, 50, 8));
		p.add(new PointDescriptor(15, 3, 50, 8));
		p.add(new PointDescriptor(40, 20, 50, 8));
		
		PointDescriptorSet pSet = new PointDescriptorSet(p);
		TreeSet<PointDescriptor> p2 = pSet.getPoints();
		
		for(PointDescriptor point : p2) System.out.println("("+point.getBotleft().x+"|"+point.getBotleft().y+")");
		
		recttosvg.saveSVG(p2);
	}

}
