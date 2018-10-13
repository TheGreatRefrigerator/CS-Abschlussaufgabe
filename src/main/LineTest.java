package main;

import Exceptions.DimensionalException;
import Exceptions.WKTRepresentationException;
import Exceptions.WktInvalidException;
import GeometryFactory.GeometryFactory;
import GeometryFactory.Line;

public class LineTest {
    public static void lineTest() {
        System.out.println();
        System.out.println("Line Tests:");
        Line l, s;
        try {
            // examples for line creation
            l = GeometryFactory.createLine("LINESTRING Z(12 31 2,2 3 3,2 1 1,12 5 1)");
            s = GeometryFactory.createLine(new double[][]{{12, 31, 2}, {2, 3.432, 3}, {2, 111, 1}, {11, 43, 1}});
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
