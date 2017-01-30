package model;

import java.io.FileWriter;
import java.io.IOException;

public class RuntimeTester {
	
	public static void createRuntimeAnalysis(String filename, int startRectNum, int step, int stopRectNum){
		try {
			FileWriter fw = new FileWriter(filename, false);
			
//		String out = new String();
			fw.write("#Rechtecke,Sweep-Line-Zeit[ms],Brute-Force-Zeit[ms]\n");
//		out += ;
			for(int i = startRectNum; i <= stopRectNum; i+=step){
				PointDescriptorSet p = new PointDescriptorSet(RandomDataGenerator.createRandomLabels(i, 5000000, 3000000, 200, 400, 2000));
				
				double start = System.currentTimeMillis();
				p.findCollisionsSweepLine();
				double time1 = System.currentTimeMillis();
				p.findCollisionsBruteForce();
				double time2 = System.currentTimeMillis();
				
//			out += i + "," + (time1 - start) + "," + (time2 - time1) + "\n";
				fw.write(i + "," + (time1 - start) + "," + (time2 - time1) + "\n");
			}
			
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
