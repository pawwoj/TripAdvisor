package exceptions;

public class DataOutOfRangeException extends RuntimeException{
    public DataOutOfRangeException() {
    }

    public DataOutOfRangeException(String message) {
        super(message);
    }
}
