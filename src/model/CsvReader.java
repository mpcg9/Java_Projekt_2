package model;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class CsvReader {
	public static List<PointDescriptor> readFile(String filename){
		LinkedList<PointDescriptor> list = new LinkedList<PointDescriptor>();
		
		File file = new File(filename);
		FileReader fr;
		try {
			fr = new FileReader(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return new LinkedList<PointDescriptor>();
		}
		BufferedReader br = new BufferedReader(fr);
		try {
			nextLine: for(String line = br.readLine(); line != null; line = br.readLine()){
				try {
					String[] args = line.split(",");
					double x = Double.parseDouble(args[1]);
					double y = Double.parseDouble(args[2]);
					double height = Double.parseDouble(args[3]);
					double width = Double.parseDouble(args[4]);
					list.add(new PointDescriptor(y, x, width, height));
				} catch (NumberFormatException e) {
					continue nextLine;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			try {
				br.close();
				fr.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return new LinkedList<PointDescriptor>();
		}
		try {
			br.close();
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
}
