package com.db.document_generator.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FacultyDTO {

    private Integer facultyId;

    @NotNull
    @Size(max = 100)
    private String title;

}
