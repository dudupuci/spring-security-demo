package io.github.com.springsecuritydemo.exceptions;

public class CannotUpdateProductException extends RuntimeException {
    public CannotUpdateProductException(String message) {
        super(message);
    }
}
