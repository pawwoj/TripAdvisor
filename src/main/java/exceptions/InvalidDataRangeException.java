package exceptions;

public class InvalidDataRangeException extends RuntimeException{
    public InvalidDataRangeException() {
    }

    public InvalidDataRangeException(String message) {
        super(message);
    }
}
