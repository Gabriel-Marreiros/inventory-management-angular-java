package br.com.gabrielmarreiros.inventorymanagementangularjava.exceptions;

public class InvalidSortException extends RuntimeException {
    public InvalidSortException() {}

    public InvalidSortException(String message) {
        super(message);
    }
}
