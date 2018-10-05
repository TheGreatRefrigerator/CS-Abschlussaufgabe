package Exceptions;

public class WKTRepresentationException extends Exception{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public WKTRepresentationException() {
        System.out.println("Geometry not representable in WKT format (see https://en.wikipedia.org/wiki/Well-known_text)");
    }
}
