package main;

import Exceptions.DimensionalException;
import Exceptions.WktInvalidException;
import Exceptions.WKTRepresentationException;
import GeometryFactory.Point;
import GeometryFactory.GeometryFactory;
import GeometryFactory.Line;
import GeometryFactory.Polygon;

public class Main {

	public static void main(String[] args) {
		// Yay, it works like this
		Point p = GeometryFactory.createPoint(23.4, 123.2, 3, 42, 1, 54, 3);
		Point m = GeometryFactory.createPointM( 2, 1232, 33);
		Point n = null;
		try {
			n = GeometryFactory.createPoint("POINT M(1232 33 2)");
		} catch (WktInvalidException e) {
			e.printStackTrace();
		}

		System.out.println(p.getWktType());
		System.out.println(p.getDimension());
		System.out.println(p.getX());
		p.setX(10.1);
		System.out.println(p.getX());
		p.setX(23.4);
		System.out.println(p.getX());

		System.out.println(m.getWktType());
		System.out.println(m.getDimension());
		try {
			System.out.println(m.getWKT());
		} catch (WKTRepresentationException e1) {

			e1.printStackTrace();
		}

		Point x1 = GeometryFactory.createPoint(3,4);
		Point x2 = GeometryFactory.createPoint(4,5);
		Point x3 = GeometryFactory.createPoint(6,2);

		Point[] polyPoints = {x1,x2,x3,x1};

		Polygon poly = GeometryFactory.createPolygon(polyPoints);

		for (Point x : poly.getPoints()) {
			try {
				System.out.println(x.getWKT());
			} catch (WKTRepresentationException e) {
				e.printStackTrace();
			}
		}

		System.out.println(n.is(m));
		n.setX(32);
		System.out.println(n.getX());
		System.out.println(n.is(m));

		Point q = null;
		try {
			q = GeometryFactory.createPoint("POINT M(30 10 9 2)");
		} catch (WktInvalidException e) {
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
		try {
			System.out.println(m.getWKT());
		} catch (WKTRepresentationException e) {
			e.printStackTrace();
		}
		System.out.println("Point m's value:" + m.getLrsValue());

		System.out.println("Point q coords should be 30 10");
		for (double cord : q.getCoordinates()) {
			System.out.print(cord + ", ");
		}
		System.out.println("Point q's value: " + q.getLrsValue());
//		TODO test functions of Point (eg. Point m or p) like m.getX()
//		System.out.println(m.getLrsValue());
//		m.getCoordinates();
//		Line pmLine = GeometryFactory.createLine(p,m);
		Line l,s;
		try {
			// examples for line creation
			l = GeometryFactory.createLine("LINESTRING Z(12 31 2,2 3 3,2 1 1,12 5 1)");
			s = GeometryFactory.createLine(new double[][] {{12,31},{2,3.432,3},{2,111,1},{12,5,1}});
			System.out.println(s.getWktType());
			try {
				System.out.println(l.getWKT());
				System.out.println(s.getWKT());
			} catch (WKTRepresentationException e) {
				e.printStackTrace();
			}
		} catch (WktInvalidException | DimensionalException e) {
			e.printStackTrace();
		}
	}
}
