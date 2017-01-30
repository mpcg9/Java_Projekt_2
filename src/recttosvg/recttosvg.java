package recttosvg;

import model.*;
import org.jdom2.*;
import org.jdom2.output.*;
import java.util.*;
import java.io.*;

public class recttosvg {
	
	public static void saveSVG(Iterable<? extends Rectangle> Rechtecke, String filename){
		
		Element svg = new Element("svg");
		Document svgdoc = new Document(svg);
		Namespace n = Namespace.getNamespace("http://www.w3.org/2000/svg");
		Namespace nd = Namespace.getNamespace("xlink","http://www.w3.org/1999/xlink");
		
		svg.setNamespace(n);
		svg.addNamespaceDeclaration(nd);
		svg.setAttribute("viewBox", getViewBoxSize(Rechtecke));
		
		Random rand = new Random();
		
		for (Rectangle r : Rechtecke){
			int randR = rand.nextInt(0x90) + 0x35;
			int randG = rand.nextInt(0x90) + 0x35;
			int randB = rand.nextInt(0x90) + 0x35;
			
			String randColour = "#" + Integer.toHexString(randR) + Integer.toHexString(randG) + Integer.toHexString(randB);
			
			Element curr = new Element("rect", n);
			
			curr.setAttribute("x", String.valueOf( r.getBotleft().getX()));
			curr.setAttribute("y", String.valueOf( r.getBotleft().getY()));
			curr.setAttribute("height", String.valueOf( r.getHeight()));
			curr.setAttribute("width", String.valueOf( r.getWidth()));
			curr.setAttribute("stroke", "#000000");
			curr.setAttribute("fill", randColour);
			
			svg.addContent(curr);
		}
		
		XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
		
		try{
			FileWriter fw = new FileWriter(filename, false);
			out.output(svgdoc, fw);
			System.out.println("File saved");
		}catch(IOException e){
			System.out.println("Fehler: IOException!");
		}catch(Exception e){
			System.out.println("Fehler: Exception!");
		}
		
	}
	
	public static String getViewBoxSize(Iterable<? extends Rectangle> rechtecke){
		double yMin = Double.MAX_VALUE;
		double yMax = Double.MIN_VALUE;
		double xMin = Double.MAX_VALUE;
		double xMax = Double.MIN_VALUE;
		
		for(Rectangle r : rechtecke){
			if(r.getBotleft().y < yMin) yMin = r.getBotleft().y;
			if(r.getBotleft().y + r.getHeight() > yMax) yMax = r.getBotleft().y + r.getHeight();
			if(r.getBotleft().x < xMin) xMin = r.getBotleft().x;
			if(r.getBotleft().x + r.getWidth() > xMax) xMax = r.getBotleft().x + r.getWidth();
		}
		
		return xMin + " " + yMin + " " + (xMax - xMin) + " " + (yMax - yMin);
	}

}
