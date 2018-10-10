package Exceptions;

public class DimensionalException extends Throwable {
    /**
     * Informs about the first pair of coordinates that do not have the same dimension
     * @param index - index of the array-item where the exception occurred
     */
    public DimensionalException(int index) {
        System.out.println("Dimension conflict in Geometry between Points " + index + " and " + (index + 1) + ":");
    }
}
