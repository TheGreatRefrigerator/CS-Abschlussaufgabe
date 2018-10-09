package main;

import Exceptions.DimensionalException;
import Exceptions.InvalidPolygonException;
import Exceptions.WktInvalidException;
import Exceptions.WKTRepresentationException;
import FeaturePackage.PointFeature;
import GeometryFactory.Point;
import GeometryFactory.GeometryFactory;
import GeometryFactory.Line;
import GeometryFactory.Polygon;

public class Main {

	public static void main(String[] args) {
		System.out.println("Point Tests:");
		Point p = GeometryFactory.createPoint(23.4, 123.2, 3, 42, 1, 54, 3);
		Point m = GeometryFactory.createPointM( 2, 1232, 33);
		Point n = null;
		try {
			n = GeometryFactory.createPoint("POINT M(1232 33 2)");
		} catch (WktInvalidException e) {
			e.printStackTrace();
		}

		System.out.println(p.getWktType());
		// use assertion tests ?
		// assert p.getWktType() == null;
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

		System.out.println(n.is(m));
		n.setX(32);
		System.out.println(n.getX());
		System.out.println(n.is(m));

		Point q = null;
		try {
			q = GeometryFactory.createPoint("POINT ZM(30 10 9 2)");
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

		System.out.println();
		System.out.println("Line Tests:");
		Line l,s;
		try {
			// examples for line creation
			l = GeometryFactory.createLine("LINESTRING Z(12 31 2,2 3 3,2 1 1,12 5 1)");
			s = GeometryFactory.createLine(new double[][] {{12,31,2},{2,3.432,3},{2,111,1},{11, 43,1}});
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

		System.out.println();
		System.out.println("Polygon Tests:");
		Point x1 = GeometryFactory.createPoint(3,4,6);
		Point x2 = GeometryFactory.createPoint(4,5,9);
		Point x3 = GeometryFactory.createPoint(6,2,10);

		Point[] polyPoints = {x1,x2,x3,x1};
		Point p1 = x1;
		p1.setLrsValue(3.);
		x2.setLrsValue(4.);
		x3.setLrsValue(1.);
		Polygon poly = null;
		Polygon poly2 = null;
		Polygon poly3 = null;

		try {
			poly = GeometryFactory.createPolygon(polyPoints);
			poly2 = GeometryFactory.createPolygon("POLYGON ((35 10,45 45,15 40,10 20,35 10))");
			poly3 = GeometryFactory.createPolygon(new double[][] {{12,31,2},{2,3.432,3},{2,111,1},{11, 43,1},{12,31,2}});
		} catch (DimensionalException | InvalidPolygonException | WktInvalidException e) {
			e.printStackTrace();
		}

		try {
			if (poly != null) {
				System.out.println(poly.getWKT());
			}
			if (poly2 != null) {
				System.out.println(poly2.getWKT());
			}
			if (poly3 != null) {
				System.out.println(poly3.getWKT());
			}

		} catch (WKTRepresentationException e) {
			e.printStackTrace();
		}

		System.out.println();
		System.out.println();
		System.out.println("---Test FeaturePackage---");
		System.out.println("PointFeature Tests:");
		try {
			PointFeature x = new PointFeature("Heidelberg", "POINT (8.685297 49.416404)");
			System.out.println(x.getInfo());
			System.out.println(x.getWKT());
			System.out.println(x.getName());
			System.out.println(x.getGeometry().getDimension());
			System.out.println(x.getGeometry().getX());
			System.out.println(x.getGeometry().getY());

		} catch (WktInvalidException | WKTRepresentationException e) {
			e.printStackTrace();
		}

	}
}
