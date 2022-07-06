package exceptions;

public class InvalidCityNameException extends RuntimeException{
    public InvalidCityNameException() {
    }

    public InvalidCityNameException(String message) {
        super(message);
    }
}
