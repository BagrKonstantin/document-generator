package com.db.document_generator.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Roles {

    @Id
    @Column(nullable = false, updatable = false)
    private String roleId;

    @Column(unique = true, length = 20)
    private String title;

    @ManyToMany
    @JoinTable(
            name = "User_Roles",
            joinColumns = @JoinColumn(name = "role_Id"),
            inverseJoinColumns = @JoinColumn(name = "user_Id")
    )
    private Set<User> userRolesUsers;

}
