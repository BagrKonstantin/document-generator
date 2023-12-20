package com.db.document_generator.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PersonDTO {

    private Long personId;

    @NotNull
    @Size(max = 20)
    private String firstname;

    @NotNull
    @Size(max = 20)
    private String surname;

    @NotNull
    @Size(max = 20)
    private String lastname;

    @NotNull
    @Size(max = 200)
    private String status;

}
