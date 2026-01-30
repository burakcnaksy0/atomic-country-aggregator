package com.burakcanaksoy.atomiccountryaggregator.exception;

public class ISONotFoundException extends RuntimeException {
    public ISONotFoundException(String message) {
        super(message);
    }
}
