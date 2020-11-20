package com.mikayelovich.model;

import com.mikayelovich.model.enums.UserRole;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
@ToString
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    @ToString.Exclude
    @ManyToMany(mappedBy = "roleEntitySet")
    private Set<UserEntity> users;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleEntity that = (RoleEntity) o;
        return Objects.equals(id, that.id) &&
                role == that.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role);
    }

}
