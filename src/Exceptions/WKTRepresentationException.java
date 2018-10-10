package Exceptions;

public class WKTRepresentationException extends Exception {

    public WKTRepresentationException() {
        System.out.println("Geometry not representable in WKT format (see https://en.wikipedia.org/wiki/Well-known_text)");
    }
}
