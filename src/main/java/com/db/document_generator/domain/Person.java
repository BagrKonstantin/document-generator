package com.db.document_generator.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Person {

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
    private Long personId;

    @Column(nullable = false, length = 20)
    private String firstname;

    @Column(nullable = false, length = 20)
    private String surname;

    @Column(nullable = false, length = 20)
    private String lastname;

    @Column(nullable = false, length = 200)
    private String status;

    @OneToMany(mappedBy = "teacher")
    private Set<Document> teacherDocuments;

    @OneToMany(mappedBy = "headTeacher")
    private Set<Document> headTeacherDocuments;

}
