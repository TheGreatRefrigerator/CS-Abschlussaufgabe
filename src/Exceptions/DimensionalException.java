package Exceptions;

public class DimensionalException extends Throwable {

    public DimensionalException (int index) {
            System.out.println("Dimension conflict in Geometry between Points " + index + " and " + (index + 1) + ":");
    }
}
