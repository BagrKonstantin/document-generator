package com.db.document_generator.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DocumentDTO {

    private Long documentId;

    @NotNull
    private Integer yearOfWork;

    @NotNull
    @Size(max = 200)
    private String programName;

    @NotNull
    @Size(max = 100)
    private String programShortName;

    @NotNull
    @Size(max = 200)
    private String programNameEn;

    @NotNull
    @Size(max = 1000)
    private String description;

    @NotNull
    @Size(max = 100)
    private String byDocument;

    private Integer documentType;

    private Long user;

    private Long teacher;

    private Long headTeacher;

    private Integer department;

    private Integer classs;

}
