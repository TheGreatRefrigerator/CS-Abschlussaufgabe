package main;

import Exceptions.DimensionalException;
import Exceptions.InvalidPolygonException;
import Exceptions.WktInvalidException;
import Exceptions.WKTRepresentationException;
import FeaturePackage.LineFeature;
import FeaturePackage.PointFeature;
import FeaturePackage.PolygonFeature;
import GeometryFactory.Point;
import GeometryFactory.GeometryFactory;
import GeometryFactory.Line;
import GeometryFactory.Polygon;

import static main.CsvTest.csvTest;
import static main.FeatureTest.featureTest;
import static main.LineTest.lineTest;
import static main.PolygonTest.polygonTest;

// Add the commons-csv-1.5.jar to your Modules/Libraries

public class Main {

    public static void main(String[] args) {

        lineTest();
        polygonTest();

        featureTest();
        csvTest();
    }
}
