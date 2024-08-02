package com.example.dndcharactercreatordemo.dal.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Roles")
public class Role{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "Title must not be empty")
    @Size(max=20, message = "The title must have maximum 20 characters")
    private String title;
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "Role_privileges",
            joinColumns = { @JoinColumn(name = "role_id") },
            inverseJoinColumns = { @JoinColumn(name = "privilege_id") }
    )
    private Set<Privilege> privileges;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Set<Privilege> privileges) {
        this.privileges = privileges;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id) || Objects.equals(title, role.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, privileges);
    }
}
