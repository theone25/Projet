package com.mine.projet.tinycart;

public class QuantityInvalidException extends RuntimeException {
    //Class version for serialization
    private static final long serialVersionUID = 21L;
    private static final String DEFAULT_MESSAGE = "The entered quantity is invalid.";

    public QuantityInvalidException() {
        super(DEFAULT_MESSAGE);
    }

    public QuantityInvalidException(String message) {
        super(message);
    }
}