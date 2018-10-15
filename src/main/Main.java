package main;

import static main.CsvTest.csvTest;
import static main.FeatureTest.featureTest;
import static main.LineTest.lineTest;
import static main.PointTest.pointTest;
import static main.PolygonTest.polygonTest;

// TODO: Add the commons-csv-1.5.jar to your Modules/Libraries!!!

public class Main {

    public static void main(String[] args) {
        System.out.println("###Test GeometryFactory###");
        pointTest();
        lineTest();
        polygonTest();

        System.out.println("###Test FeaturePackage###");
        featureTest();
        System.out.println("###Test CSV###");
        csvTest();

        System.out.println();
        System.out.println();
    }
}
