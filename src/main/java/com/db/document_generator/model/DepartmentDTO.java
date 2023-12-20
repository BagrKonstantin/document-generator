package com.db.document_generator.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DepartmentDTO {

    private Integer departmentId;

    @NotNull
    @Size(max = 100)
    private String title;

    @NotNull
    private Integer faculty;

}
