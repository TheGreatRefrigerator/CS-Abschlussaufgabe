package Exceptions;

public class InvalidPolygonException extends Throwable {

    public InvalidPolygonException() {
        System.out.println("Polygons need to have at least four Points and the last has to equal the first Point.");
    }
}
