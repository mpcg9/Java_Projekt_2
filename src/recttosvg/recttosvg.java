package recttosvg;

import model.*;
import org.jdom2.*;
import org.jdom2.output.*;
import java.util.*;
import java.io.*;

public class recttosvg {
	
	public static void saveSVG(Iterable<PointDescriptor> rechtecke, String filename){
		int maxWidth = 600;
		int maxHeight = 400;
		
		int[] viewbox = getViewBoxInts(rechtecke);
		
		double scaleW = ((double) maxWidth)/((double) viewbox[2]);
		double scaleH = ((double) maxHeight)/((double) viewbox[3]);
		double scale = Math.max(scaleW,scaleH);
		double boxHeight = viewbox[3] * scale;
		
		Element svg = new Element("svg");
		Document svgdoc = new Document(svg);
		Namespace n = Namespace.getNamespace("http://www.w3.org/2000/svg");
		Namespace nd = Namespace.getNamespace("xlink","http://www.w3.org/1999/xlink");
		
		svg.setNamespace(n);
		svg.addNamespaceDeclaration(nd);
//		svg.setAttribute("viewBox", getViewBoxSize(Rechtecke));
		
		Element style = new Element("style",n);
		style.addContent(""
				+ ".label{"
				+ "		font-family:monospace;"
				+ "}");
		svg.addContent(style);
		
		Random rand = new Random();
		
		for (PointDescriptor r : rechtecke){
			int randR = rand.nextInt(0x90) + 0x35;
			int randG = rand.nextInt(0x90) + 0x35;
			int randB = rand.nextInt(0x90) + 0x35;
			
			String randColour = "#" + Integer.toHexString(randR) + Integer.toHexString(randG) + Integer.toHexString(randB);
			
			Element g = new Element("g", n);
			Element curr = new Element("rect", n);
			
			double x 		= (r.getBotleft().getX() - viewbox[0]) * scale;
			double y 		= (r.getBotleft().getY() - viewbox[1]) * scale;
			double height 	=  r.getHeight()					   * scale;
			double width 	=  r.getWidth()						   * scale;
			
			y = boxHeight - y;
			
			g.setAttribute("transform", "translate("+x+","+y+")");
			
//			curr.setAttribute("x", 		String.valueOf("0");
//			curr.setAttribute("y", 		String.valueOf("0");
			curr.setAttribute("height", String.valueOf(height));
			curr.setAttribute("width", 	String.valueOf(width));
//			curr.setAttribute("stroke", "#000000");
			curr.setAttribute("fill", randColour);
			g.addContent(curr);
			
			if (r.getDescription() != null) {
				Element text = new Element("text", n);
				//			text.setAttribute("x", "0");
				text.setAttribute("y", String.valueOf(height * 0.9));
				text.setAttribute("class", "label");
				text.setAttribute("style", "font-size: " + height + ";");
				text.addContent(r.getDescription());
				g.addContent(text);
			}
			svg.addContent(g);
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
		
		return Math.round(xMin) + " " + Math.round(yMin) + " " + Math.round(xMax - xMin) + " " + Math.round(yMax - yMin);
	}

	public static int[] getViewBoxInts(Iterable<? extends Rectangle> rechtecke){
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
		
		int[] out = {(int) Math.round(xMin), (int) Math.round(yMin), (int) Math.round(xMax - xMin), (int) Math.round(yMax - yMin)};
		return out;
	}
}
