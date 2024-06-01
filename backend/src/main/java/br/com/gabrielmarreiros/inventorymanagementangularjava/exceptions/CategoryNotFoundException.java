package br.com.gabrielmarreiros.inventorymanagementangularjava.exceptions;

public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException() {}

    public CategoryNotFoundException(String message) {
        super(message);
    }
}
