package recttosvg;

import model.*;
import org.jdom2.*;
import org.jdom2.output.*;
import java.util.*;
import java.io.*;

public class recttosvg {
	
	public recttosvg(LinkedList<Rectangle> Rechtecke){
		
		Element svg = new Element("svg");
		Document svgdoc = new Document(svg);
		Namespace n = Namespace.getNamespace("http://www.w3.org/2000/svg");
		Namespace nd = Namespace.getNamespace("xlink","http://www.w3.org/1999/xlink");
		
		svg.setNamespace(n);
		svg.addNamespaceDeclaration(nd);
		
		Random rand = new Random();
		
		for (Rectangle r:Rechtecke){
			int randR = rand.nextInt(0x90) + 0x35;
			int randG = rand.nextInt(0x90) + 0x35;
			int randB = rand.nextInt(0x90) + 0x35;
			
			String randColour = "#" + Integer.toHexString(randR) + Integer.toHexString(randG) + Integer.toHexString(randB);
			
			Element curr = new Element("Rectangle", n);
			
			curr.setAttribute("x", String.valueOf(r.getBotleft().getX()));
			curr.setAttribute("y", String.valueOf(r.getBotleft().getY()));
			curr.setAttribute("height", String.valueOf(r.getHeight()));
			curr.setAttribute("width", String.valueOf(r.getWidth()));
			curr.setAttribute("stroke", "#000000");
			curr.setAttribute("fill", randColour);
			
			svg.addContent(curr);
		}
		
		XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
		String filename = "Rechtecke.svg";
		
		try{
			FileWriter fw = new FileWriter(filename, false);
			out.output(svgdoc, fw);
		}catch(IOException e){
			System.out.println("Fehler: IOException!");
		}catch(Exception e){
			System.out.println("Fehler: Exception!");
		}
		
	}

}
