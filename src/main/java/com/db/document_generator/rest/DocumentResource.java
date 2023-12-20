package com.db.document_generator.rest;

import com.db.document_generator.model.DocumentDTO;
import com.db.document_generator.service.DocumentService;
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
@RequestMapping(value = "/api/documents", produces = MediaType.APPLICATION_JSON_VALUE)
public class DocumentResource {

    private final DocumentService documentService;

    public DocumentResource(final DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping
    public ResponseEntity<List<DocumentDTO>> getAllDocuments() {
        return ResponseEntity.ok(documentService.findAll());
    }

    @GetMapping("/{documentId}")
    public ResponseEntity<DocumentDTO> getDocument(
            @PathVariable(name = "documentId") final Long documentId) {
        return ResponseEntity.ok(documentService.get(documentId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createDocument(@RequestBody @Valid final DocumentDTO documentDTO) {
        final Long createdDocumentId = documentService.create(documentDTO);
        return new ResponseEntity<>(createdDocumentId, HttpStatus.CREATED);
    }

    @PutMapping("/{documentId}")
    public ResponseEntity<Long> updateDocument(
            @PathVariable(name = "documentId") final Long documentId,
            @RequestBody @Valid final DocumentDTO documentDTO) {
        documentService.update(documentId, documentDTO);
        return ResponseEntity.ok(documentId);
    }

    @DeleteMapping("/{documentId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDocument(
            @PathVariable(name = "documentId") final Long documentId) {
        documentService.delete(documentId);
        return ResponseEntity.noContent().build();
    }

}
