package main;

import GeometryFactory.Geometry;
import GeometryFactory.GeometryFactory;

public class Main {

    public static void main(String[] args) {
        String[] arr = "Polygon((0 0, 0 20, 20 20, 20 0, 0 0),(5 5, 5 15, 15 15, 15 5, 5 5))".split("[()]");
        System.out.println(arr.length);
        for (String c : arr)
            System.out.println(c);
//        Geometry point = GeometryFactory.createGeomFromWKT("POINT");
//        System.out.println(point.getInfo());
    }

}
