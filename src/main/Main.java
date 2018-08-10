package main;

import GeometryFactory.Geometry;
import GeometryFactory.GeometryFactory;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello");
        Geometry point = GeometryFactory.createGeomFromWKT("POINT");
        System.out.println(point.getInfo());
    }

}
