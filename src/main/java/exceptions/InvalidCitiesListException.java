package exceptions;

public class InvalidCitiesListException extends RuntimeException{
    public InvalidCitiesListException() {
    }

    public InvalidCitiesListException(String message) {
        super(message);
    }
}
