package com.db.document_generator.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ClassesDTO {

    private Integer classId;

    @NotNull
    @Size(max = 300)
    private String title;

    @NotNull
    @Size(max = 1000)
    private String description;

    @NotNull
    @Size(max = 2)
    private String code;

    @NotNull
    private Integer chapter;

}
