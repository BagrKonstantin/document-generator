package com.db.document_generator.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DocumentTypesDTO {

    private Integer documentTypeId;

    @Size(max = 20)
    private String tittle;

}
