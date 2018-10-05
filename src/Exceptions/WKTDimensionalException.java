package Exceptions;

public class WKTDimensionalException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public WKTDimensionalException() {
        System.out.println("Invalid WKT format (see https://en.wikipedia.org/wiki/Well-known_text)");
    }
}
