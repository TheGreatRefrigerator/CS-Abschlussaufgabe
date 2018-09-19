package main;

import Exceptions.WKTDimensionalException;
import GeometryFactory.Point;
import GeometryFactory.GeometryFactory;
import GeometryFactory.Line;

public class Main {

	public static void main(String[] args) {
		// Yay, it works like this
		Point p = GeometryFactory.createPoint(new double[] { 23.4, 123.2, 3, 42, 1, 54, 3 });
		Point m = GeometryFactory.createPointM( 2, 1232, 33);
		Point q = null;
		try {
			q = GeometryFactory.createPoint("POINT M(30 10 2 2)");
		} catch (WKTDimensionalException e) {
			e.printStackTrace();
		}
		// Test p and q
		System.out.println("Point p coords should be 23.4, 123.2, 3, 42, 1, 54, 3");
		for (double cord : p.getCoordinates()) {
			System.out.print(cord + ", ");
		}
		System.out.println();

		System.out.println("Point m coords should be 1232, 33");
		for (double cord : m.getCoordinates()) {
			System.out.print(cord + ", ");
		}
		System.out.println(m.getWKT());
		System.out.println("Point m's value:" + m.getLrsValue());

		System.out.println("Point q coords should be 30 10");
		for (double cord : q.getCoordinates()) {
			System.out.print(cord + ", ");
		}
		System.out.println("Point q's value: " + q.getLrsValue());

//		Line l;
//		try {
//			l = GeometryFactory.createLine("LINESTRING ZM(12 31 23 1,2 4 3 1,2 1 22 1,12 2 132 1)");
//
//			for (Point x : l.getPoints()) {
//				System.out.println(x.getWKT() + ", " + String.valueOf(x.getLrsValue()));
//			}
//
//		} catch (WKTDimensionalException e) {
//			System.out.println("Schade wars");
//			e.printStackTrace();
//		}
//        Geometry point = GeometryFactory.createGeomFromWKT("POINT");
		System.out.println(p.getWKT());
	}

}
