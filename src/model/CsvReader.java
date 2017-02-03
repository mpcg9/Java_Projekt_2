package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

public class CsvReader {
	public static List<PointDescriptor> readFile(String filename){
		LinkedList<PointDescriptor> list = new LinkedList<PointDescriptor>();

		File file = new File(filename);
		FileInputStream fis;
		InputStreamReader isr;
		BufferedReader br;
		try {
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis, "Cp1252");	
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
			return new LinkedList<PointDescriptor>();
		}
		br = new BufferedReader(isr);
		try {
			nextLine: for(String line = br.readLine(); line != null; line = br.readLine()){
				try {
					String[] args = line.split(",");
					double x = Double.parseDouble(args[1]);
					double y = Double.parseDouble(args[2]);
					double height = Double.parseDouble(args[3]);
					double width = Double.parseDouble(args[4]);
//					for(char i : args[0].toCharArray()){
//						System.out.println(Character.toString(i) + ": \\u" + Integer.toHexString(i | 0x10000).substring(1) );
//					}
//					System.out.println(args[0]);
					args[0] = args[0].replace('\ufffd', '\u00FC'); // wer auch immer die Testdaten gemacht hat: "\ufffd"? so kodiert man das ue doch nicht!
//					System.out.println(args[0]);
					list.add(new PointDescriptor(args[0], y, x, width, height));
				} catch (NumberFormatException e) {
					continue nextLine;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			try {
				br.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return new LinkedList<PointDescriptor>();
		}
		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
}
