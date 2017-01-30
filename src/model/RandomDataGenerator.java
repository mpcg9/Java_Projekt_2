package model;

import java.util.*;

public class RandomDataGenerator {
	public static ArrayList<PointDescriptor> createRandomLabels(int numel, int xMax, int yMax, int height, int widthMin, int widthMax){
		ArrayList<PointDescriptor> list = new ArrayList<PointDescriptor>(numel);
		for(int i = 0; i < numel; i++){
			list.add(i, createRandomLabel(xMax, yMax, height, widthMin, widthMax));
		}
		return list;
	}
	
	public static PointDescriptor createRandomLabel(int xMax, int yMax, int height, int widthMin, int widthMax){
		int x = randint(xMax);
		int y = randint(yMax);
		int width = widthMin + randint(widthMax - widthMin);
		return new PointDescriptor(y, x, width, height);
	}
	
	public static int randint(int max){
		return (int) Math.round((Math.random() * (max+1)) - 0.5);
	}
}
