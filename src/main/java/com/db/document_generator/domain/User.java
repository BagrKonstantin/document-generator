package com.db.document_generator.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "\"User\"")
@Getter
@Setter
public class User {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Long userId;

    @Column(nullable = false, length = 20)
    private String firstname;

    @Column(nullable = false, length = 20)
    private String surname;

    @Column(nullable = false, length = 20)
    private String lastname;

    @Column(nullable = false, unique = true, length = 30)
    private String email;

    @Column(length = 50)
    private String username;

    @Column(length = 100)
    private String hashedPassword;

    @ManyToMany(mappedBy = "userRolesUsers")
    private Set<Roles> userRolesRoleses;

    @OneToMany(mappedBy = "user")
    private Set<Document> userDocuments;

}
