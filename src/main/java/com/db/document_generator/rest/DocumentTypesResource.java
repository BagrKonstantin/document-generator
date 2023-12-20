package com.db.document_generator.rest;

import com.db.document_generator.model.DocumentTypesDTO;
import com.db.document_generator.service.DocumentTypesService;
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
@RequestMapping(value = "/api/documentTypess", produces = MediaType.APPLICATION_JSON_VALUE)
public class DocumentTypesResource {

    private final DocumentTypesService documentTypesService;

    public DocumentTypesResource(final DocumentTypesService documentTypesService) {
        this.documentTypesService = documentTypesService;
    }

    @GetMapping
    public ResponseEntity<List<DocumentTypesDTO>> getAllDocumentTypess() {
        return ResponseEntity.ok(documentTypesService.findAll());
    }

    @GetMapping("/{documentTypeId}")
    public ResponseEntity<DocumentTypesDTO> getDocumentTypes(
            @PathVariable(name = "documentTypeId") final Integer documentTypeId) {
        return ResponseEntity.ok(documentTypesService.get(documentTypeId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createDocumentTypes(
            @RequestBody @Valid final DocumentTypesDTO documentTypesDTO) {
        final Integer createdDocumentTypeId = documentTypesService.create(documentTypesDTO);
        return new ResponseEntity<>(createdDocumentTypeId, HttpStatus.CREATED);
    }

    @PutMapping("/{documentTypeId}")
    public ResponseEntity<Integer> updateDocumentTypes(
            @PathVariable(name = "documentTypeId") final Integer documentTypeId,
            @RequestBody @Valid final DocumentTypesDTO documentTypesDTO) {
        documentTypesService.update(documentTypeId, documentTypesDTO);
        return ResponseEntity.ok(documentTypeId);
    }

    @DeleteMapping("/{documentTypeId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDocumentTypes(
            @PathVariable(name = "documentTypeId") final Integer documentTypeId) {
        documentTypesService.delete(documentTypeId);
        return ResponseEntity.noContent().build();
    }

}
