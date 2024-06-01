package br.com.gabrielmarreiros.inventorymanagementangularjava.infra;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
    private String title;
    private String description;
    private int status;
    private String instance;

    public ErrorResponse(){}

    public ErrorResponse withTitle(String title){
        this.title = title;
        return this;
    }

    public ErrorResponse withDescription(String description){
        this.description = description;
        return this;
    }

    public ErrorResponse withStatus(int status){
        this.status = status;
        return this;
    }

    public ErrorResponse withStatus(HttpStatus status){
        this.status = status.value();
        return this;
    }

    public ErrorResponse withInstance(String instance){
        this.instance = instance;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getStatus() {
        return status;
    }

    public String getInstance() {
        return instance;
    }
}
