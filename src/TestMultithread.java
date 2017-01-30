import model.CsvReader;
import model.PointDescriptorSet;

public class TestMultithread {

	public static void main(String[] args) {
		PointDescriptorSet points = new PointDescriptorSet(CsvReader.readFile("auswahl_benelux.csv"));
		double minScale = points.findMinimumScaleFactor();
		System.out.println("Minimaler Zoomfaktor Benelux: " + minScale);
		
		points.findCollisionsBruteForceMultithreaded();
		minScale = points.findMinimumScaleFactor();
		System.out.println("Minimaler Zoomfaktor Benelux: " + minScale);
	}

}
