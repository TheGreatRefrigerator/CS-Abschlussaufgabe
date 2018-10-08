package Exceptions;

public class WktInvalidException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public WktInvalidException() {
        System.out.println("Invalid WKT format (see https://en.wikipedia.org/wiki/Well-known_text)");
    }
}