package com.awbd.subscription.exceptions;

//@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    public NotFoundException(String id) {
        super("The search with id " + id + " doesn't exist.");
    }
}
