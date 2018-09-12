package main;

import Exceptions.WKTDimensionalException;
import GeometryFactory.Point;
import GeometryFactory.GeometryFactory;
import GeometryFactory.Line;

public class Main {

	public static void main(String[] args) {
		// some basic examples on how to parse a WKT point - this will be more difficult
		// with Lines and Polygons
		String[] arr = "POINT (30 10)".split("[()]"); // ["POINT", "30 10"}
		System.out.println(arr.length); //
		System.out.println(arr[1].split(" ")); //
		// Yay, it works like this
		Point p = GeometryFactory.createPoint(new double[] { 23.4, 123.2, 3, 42, 1, 54, 3 });
		Point q = GeometryFactory.createPoint("POINT (30 10)");
		//
		Line l;
		try {
			l = GeometryFactory.createLine("LINESTRING (12 31 23 1,2 4 3 1,2 1 22 1,12 2 132 1)");

			for (Point x : l.getPoints()) {
				System.out.println(x.getWKT() + ", " + String.valueOf(x.getLrsValue()));
			}

		} catch (WKTDimensionalException e) {
			System.out.println("Schade wars");
			e.printStackTrace();
		}
		System.out.println("Point p");
		for (double cord : p.getCoordinates()) {
			System.out.println(cord);
		}

		System.out.println("Point q");
		for (double cord : q.getCoordinates()) {
			System.out.println(cord);
		}
//        Geometry point = GeometryFactory.createGeomFromWKT("POINT");
		System.out.println(p.getWKT());

	}

}
