package com.db.document_generator.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ChaptersDTO {

    private Integer chapterId;

    @NotNull
    @Size(max = 200)
    private String title;

    @NotNull
    @Size(max = 2)
    private String code;

}
