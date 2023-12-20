package com.db.document_generator.service;

import com.db.document_generator.domain.Classes;
import com.db.document_generator.domain.Department;
import com.db.document_generator.domain.Document;
import com.db.document_generator.domain.DocumentTypes;
import com.db.document_generator.domain.Person;
import com.db.document_generator.domain.User;
import com.db.document_generator.model.DocumentDTO;
import com.db.document_generator.repos.ClassesRepository;
import com.db.document_generator.repos.DepartmentRepository;
import com.db.document_generator.repos.DocumentRepository;
import com.db.document_generator.repos.DocumentTypesRepository;
import com.db.document_generator.repos.PersonRepository;
import com.db.document_generator.repos.UserRepository;
import com.db.document_generator.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentTypesRepository documentTypesRepository;
    private final UserRepository userRepository;
    private final PersonRepository personRepository;
    private final DepartmentRepository departmentRepository;
    private final ClassesRepository classesRepository;

    public DocumentService(final DocumentRepository documentRepository,
            final DocumentTypesRepository documentTypesRepository,
            final UserRepository userRepository, final PersonRepository personRepository,
            final DepartmentRepository departmentRepository,
            final ClassesRepository classesRepository) {
        this.documentRepository = documentRepository;
        this.documentTypesRepository = documentTypesRepository;
        this.userRepository = userRepository;
        this.personRepository = personRepository;
        this.departmentRepository = departmentRepository;
        this.classesRepository = classesRepository;
    }

    public List<DocumentDTO> findAll() {
        final List<Document> documents = documentRepository.findAll(Sort.by("documentId"));
        return documents.stream()
                .map(document -> mapToDTO(document, new DocumentDTO()))
                .toList();
    }

    public DocumentDTO get(final Long documentId) {
        return documentRepository.findById(documentId)
                .map(document -> mapToDTO(document, new DocumentDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final DocumentDTO documentDTO) {
        final Document document = new Document();
        mapToEntity(documentDTO, document);
        return documentRepository.save(document).getDocumentId();
    }

    public void update(final Long documentId, final DocumentDTO documentDTO) {
        final Document document = documentRepository.findById(documentId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(documentDTO, document);
        documentRepository.save(document);
    }

    public void delete(final Long documentId) {
        documentRepository.deleteById(documentId);
    }

    private DocumentDTO mapToDTO(final Document document, final DocumentDTO documentDTO) {
        documentDTO.setDocumentId(document.getDocumentId());
        documentDTO.setYearOfWork(document.getYearOfWork());
        documentDTO.setProgramName(document.getProgramName());
        documentDTO.setProgramShortName(document.getProgramShortName());
        documentDTO.setProgramNameEn(document.getProgramNameEn());
        documentDTO.setDescription(document.getDescription());
        documentDTO.setByDocument(document.getByDocument());
        documentDTO.setDocumentType(document.getDocumentType() == null ? null : document.getDocumentType().getDocumentTypeId());
        documentDTO.setUser(document.getUser() == null ? null : document.getUser().getUserId());
        documentDTO.setTeacher(document.getTeacher() == null ? null : document.getTeacher().getPersonId());
        documentDTO.setHeadTeacher(document.getHeadTeacher() == null ? null : document.getHeadTeacher().getPersonId());
        documentDTO.setDepartment(document.getDepartment() == null ? null : document.getDepartment().getDepartmentId());
        documentDTO.setClasss(document.getClasss() == null ? null : document.getClasss().getClassId());
        return documentDTO;
    }

    private Document mapToEntity(final DocumentDTO documentDTO, final Document document) {
        document.setYearOfWork(documentDTO.getYearOfWork());
        document.setProgramName(documentDTO.getProgramName());
        document.setProgramShortName(documentDTO.getProgramShortName());
        document.setProgramNameEn(documentDTO.getProgramNameEn());
        document.setDescription(documentDTO.getDescription());
        document.setByDocument(documentDTO.getByDocument());
        final DocumentTypes documentType = documentDTO.getDocumentType() == null ? null : documentTypesRepository.findById(documentDTO.getDocumentType())
                .orElseThrow(() -> new NotFoundException("documentType not found"));
        document.setDocumentType(documentType);
        final User user = documentDTO.getUser() == null ? null : userRepository.findById(documentDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        document.setUser(user);
        final Person teacher = documentDTO.getTeacher() == null ? null : personRepository.findById(documentDTO.getTeacher())
                .orElseThrow(() -> new NotFoundException("teacher not found"));
        document.setTeacher(teacher);
        final Person headTeacher = documentDTO.getHeadTeacher() == null ? null : personRepository.findById(documentDTO.getHeadTeacher())
                .orElseThrow(() -> new NotFoundException("headTeacher not found"));
        document.setHeadTeacher(headTeacher);
        final Department department = documentDTO.getDepartment() == null ? null : departmentRepository.findById(documentDTO.getDepartment())
                .orElseThrow(() -> new NotFoundException("department not found"));
        document.setDepartment(department);
        final Classes classs = documentDTO.getClasss() == null ? null : classesRepository.findById(documentDTO.getClasss())
                .orElseThrow(() -> new NotFoundException("classs not found"));
        document.setClasss(classs);
        return document;
    }

}
