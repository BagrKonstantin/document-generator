package com.db.document_generator.model;

import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RolesDTO {

    @Size(max = 255)
    private String roleId;

    @Size(max = 20)
    private String title;

    private List<Long> userRolesUsers;

}
