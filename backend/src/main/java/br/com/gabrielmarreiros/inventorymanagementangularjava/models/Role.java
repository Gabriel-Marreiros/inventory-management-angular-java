package br.com.gabrielmarreiros.inventorymanagementangularjava.models;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, unique = true)
    private String name;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime modifiedAt;

    public Role(){}

    public Role(UUID id) {
        this.id = id;
    }

    public Role(UUID id, String name, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.name = name;
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

    @Override
    public String getAuthority() {
        return this.name;
    }
}
