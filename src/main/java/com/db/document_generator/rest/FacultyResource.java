package com.db.document_generator.rest;

import com.db.document_generator.model.FacultyDTO;
import com.db.document_generator.service.FacultyService;
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
@RequestMapping(value = "/api/faculties", produces = MediaType.APPLICATION_JSON_VALUE)
public class FacultyResource {

    private final FacultyService facultyService;

    public FacultyResource(final FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping
    public ResponseEntity<List<FacultyDTO>> getAllFaculties() {
        return ResponseEntity.ok(facultyService.findAll());
    }

    @GetMapping("/{facultyId}")
    public ResponseEntity<FacultyDTO> getFaculty(
            @PathVariable(name = "facultyId") final Integer facultyId) {
        return ResponseEntity.ok(facultyService.get(facultyId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createFaculty(@RequestBody @Valid final FacultyDTO facultyDTO) {
        final Integer createdFacultyId = facultyService.create(facultyDTO);
        return new ResponseEntity<>(createdFacultyId, HttpStatus.CREATED);
    }

    @PutMapping("/{facultyId}")
    public ResponseEntity<Integer> updateFaculty(
            @PathVariable(name = "facultyId") final Integer facultyId,
            @RequestBody @Valid final FacultyDTO facultyDTO) {
        facultyService.update(facultyId, facultyDTO);
        return ResponseEntity.ok(facultyId);
    }

    @DeleteMapping("/{facultyId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteFaculty(
            @PathVariable(name = "facultyId") final Integer facultyId) {
        facultyService.delete(facultyId);
        return ResponseEntity.noContent().build();
    }

}
