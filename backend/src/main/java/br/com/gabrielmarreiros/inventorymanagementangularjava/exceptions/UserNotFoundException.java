package br.com.gabrielmarreiros.inventorymanagementangularjava.exceptions;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException() {}

    public UserNotFoundException(String message) {
        super(message);
    }
}
