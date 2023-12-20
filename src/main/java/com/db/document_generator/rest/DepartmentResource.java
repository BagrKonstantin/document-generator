package com.db.document_generator.rest;

import com.db.document_generator.model.DepartmentDTO;
import com.db.document_generator.service.DepartmentService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/departments", produces = MediaType.APPLICATION_JSON_VALUE)
public class DepartmentResource {

    private final DepartmentService departmentService;

    public DepartmentResource(final DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.findAll());
    }

    @GetMapping("/{departmentId}")
    public ResponseEntity<DepartmentDTO> getDepartment(
            @PathVariable(name = "departmentId") final Integer departmentId) {
        return ResponseEntity.ok(departmentService.get(departmentId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createDepartment(
            @RequestBody @Valid final DepartmentDTO departmentDTO) {
        final Integer createdDepartmentId = departmentService.create(departmentDTO);
        return new ResponseEntity<>(createdDepartmentId, HttpStatus.CREATED);
    }

    @PutMapping("/{departmentId}")
    public ResponseEntity<Integer> updateDepartment(
            @PathVariable(name = "departmentId") final Integer departmentId,
            @RequestBody @Valid final DepartmentDTO departmentDTO) {
        departmentService.update(departmentId, departmentDTO);
        return ResponseEntity.ok(departmentId);
    }

    @DeleteMapping("/{departmentId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDepartment(
            @PathVariable(name = "departmentId") final Integer departmentId) {
        departmentService.delete(departmentId);
        return ResponseEntity.noContent().build();
    }

}
