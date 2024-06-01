package br.com.gabrielmarreiros.inventorymanagementangularjava.exceptions;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException() {}

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
