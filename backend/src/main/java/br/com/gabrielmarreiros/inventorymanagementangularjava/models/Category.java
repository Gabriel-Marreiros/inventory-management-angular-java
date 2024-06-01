package br.com.gabrielmarreiros.inventorymanagementangularjava.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, unique = true)
    private String name;
    private boolean active;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime modifiedAt;

    public Category() {}

    public Category(UUID id){
        this.id = id;
    }

    public Category(UUID id, String name, boolean active, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.name = name;
        this.active = active;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}
