package com.db.document_generator.service;

import com.db.document_generator.domain.DocumentTypes;
import com.db.document_generator.model.DocumentTypesDTO;
import com.db.document_generator.repos.DocumentTypesRepository;
import com.db.document_generator.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class DocumentTypesService {

    private final DocumentTypesRepository documentTypesRepository;

    public DocumentTypesService(final DocumentTypesRepository documentTypesRepository) {
        this.documentTypesRepository = documentTypesRepository;
    }

    public List<DocumentTypesDTO> findAll() {
        final List<DocumentTypes> documentTypeses = documentTypesRepository.findAll(Sort.by("documentTypeId"));
        return documentTypeses.stream()
                .map(documentTypes -> mapToDTO(documentTypes, new DocumentTypesDTO()))
                .toList();
    }

    public DocumentTypesDTO get(final Integer documentTypeId) {
        return documentTypesRepository.findById(documentTypeId)
                .map(documentTypes -> mapToDTO(documentTypes, new DocumentTypesDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final DocumentTypesDTO documentTypesDTO) {
        final DocumentTypes documentTypes = new DocumentTypes();
        mapToEntity(documentTypesDTO, documentTypes);
        return documentTypesRepository.save(documentTypes).getDocumentTypeId();
    }

    public void update(final Integer documentTypeId, final DocumentTypesDTO documentTypesDTO) {
        final DocumentTypes documentTypes = documentTypesRepository.findById(documentTypeId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(documentTypesDTO, documentTypes);
        documentTypesRepository.save(documentTypes);
    }

    public void delete(final Integer documentTypeId) {
        documentTypesRepository.deleteById(documentTypeId);
    }

    private DocumentTypesDTO mapToDTO(final DocumentTypes documentTypes,
            final DocumentTypesDTO documentTypesDTO) {
        documentTypesDTO.setDocumentTypeId(documentTypes.getDocumentTypeId());
        documentTypesDTO.setTittle(documentTypes.getTittle());
        return documentTypesDTO;
    }

    private DocumentTypes mapToEntity(final DocumentTypesDTO documentTypesDTO,
            final DocumentTypes documentTypes) {
        documentTypes.setTittle(documentTypesDTO.getTittle());
        return documentTypes;
    }

    public boolean tittleExists(final String tittle) {
        return documentTypesRepository.existsByTittleIgnoreCase(tittle);
    }

}
