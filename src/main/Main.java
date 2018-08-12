package main;

import GeometryFactory.Geometry;
import GeometryFactory.Point;
import GeometryFactory.GeometryFactory;

public class Main {

    public static void main(String[] args) {
        // some basic examples on how to parse a WKT point - this will be more difficult with Lines and Polygons
        String[] arr = "POINT (30 10)".split("[()]"); // ["POINT", "30 10"}
        System.out.println(arr.length); //
        System.out.println(arr[1].split(" ")); //
        // Yay, it works like this
        Point p = GeometryFactory.createPoint(new double[] {23.4, 123.2,3,42,1,54,3});
        Point q = GeometryFactory.createPoint("POINT M (30 10)");
        //
        System.out.println("Point p");
        for (double cord : p.getCoordinates()) {
            System.out.println(cord);
        }

        System.out.println("Point q");
        for (double cord :  q.getCoordinates()) {
            System.out.println(cord);
        }
//        Geometry point = GeometryFactory.createGeomFromWKT("POINT");
        System.out.println(p.getInfo());
    }

}
