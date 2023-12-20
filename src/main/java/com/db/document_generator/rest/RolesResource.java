package com.db.document_generator.rest;

import com.db.document_generator.model.RolesDTO;
import com.db.document_generator.service.RolesService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/roless", produces = MediaType.APPLICATION_JSON_VALUE)
public class RolesResource {

    private final RolesService rolesService;

    public RolesResource(final RolesService rolesService) {
        this.rolesService = rolesService;
    }

    @GetMapping
    public ResponseEntity<List<RolesDTO>> getAllRoless() {
        return ResponseEntity.ok(rolesService.findAll());
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<RolesDTO> getRoles(@PathVariable(name = "roleId") final String roleId) {
        return ResponseEntity.ok(rolesService.get(roleId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<String> createRoles(@RequestBody @Valid final RolesDTO rolesDTO,
            final BindingResult bindingResult) throws MethodArgumentNotValidException {
        if (!bindingResult.hasFieldErrors("roleId") && rolesDTO.getRoleId() == null) {
            bindingResult.rejectValue("roleId", "NotNull");
        }
        if (!bindingResult.hasFieldErrors("roleId") && rolesService.roleIdExists(rolesDTO.getRoleId())) {
            bindingResult.rejectValue("roleId", "Exists.roles.roleId");
        }
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(new MethodParameter(
                    this.getClass().getDeclaredMethods()[0], -1), bindingResult);
        }
        final String createdRoleId = rolesService.create(rolesDTO);
        return new ResponseEntity<>(createdRoleId, HttpStatus.CREATED);
    }

    @PutMapping("/{roleId}")
    public ResponseEntity<String> updateRoles(@PathVariable(name = "roleId") final String roleId,
            @RequestBody @Valid final RolesDTO rolesDTO) {
        rolesService.update(roleId, rolesDTO);
        return ResponseEntity.ok(roleId);
    }

    @DeleteMapping("/{roleId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteRoles(@PathVariable(name = "roleId") final String roleId) {
        rolesService.delete(roleId);
        return ResponseEntity.noContent().build();
    }

}
