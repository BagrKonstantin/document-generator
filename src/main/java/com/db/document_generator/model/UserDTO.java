package com.db.document_generator.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDTO {

    private Long userId;

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
    @Size(max = 30)
    private String email;

    @Size(max = 50)
    private String username;

    @Size(max = 100)
    private String hashedPassword;

}
