package br.com.gabrielmarreiros.inventorymanagementangularjava.enums;

public enum RolesEnum {
    ADMIN("Administrador"),
    USER("Usuário");

    private final String value;

    RolesEnum(String value){
        this.value = value;
    };

    public String getValue() {
        return this.value;
    }
}
