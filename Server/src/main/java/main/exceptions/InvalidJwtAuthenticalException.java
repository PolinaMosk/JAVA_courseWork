package main.exceptions;

public class InvalidJwtAuthenticalException extends RuntimeException{
    public InvalidJwtAuthenticalException(String message) {
        super(message);
    }
}
