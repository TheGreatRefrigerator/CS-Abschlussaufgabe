package main;

import Exceptions.WKTRepresentationException;
import Exceptions.WktInvalidException;
import GeometryFactory.GeometryFactory;
import GeometryFactory.Point;

public class PointTest {
    public static void pointTest() {
        System.out.println("---Point Test---");
        Point p = GeometryFactory.createPoint(23.4, 123.2, 3, 42, 1, 54, 3);
        Point m = GeometryFactory.createPointM(2, 1232, 33);
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

    }
}
