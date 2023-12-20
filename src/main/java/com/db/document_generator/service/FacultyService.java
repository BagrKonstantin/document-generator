package com.db.document_generator.service;

import com.db.document_generator.domain.Faculty;
import com.db.document_generator.model.FacultyDTO;
import com.db.document_generator.repos.FacultyRepository;
import com.db.document_generator.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyService(final FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public List<FacultyDTO> findAll() {
        final List<Faculty> faculties = facultyRepository.findAll(Sort.by("facultyId"));
        return faculties.stream()
                .map(faculty -> mapToDTO(faculty, new FacultyDTO()))
                .toList();
    }

    public FacultyDTO get(final Integer facultyId) {
        return facultyRepository.findById(facultyId)
                .map(faculty -> mapToDTO(faculty, new FacultyDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final FacultyDTO facultyDTO) {
        final Faculty faculty = new Faculty();
        mapToEntity(facultyDTO, faculty);
        return facultyRepository.save(faculty).getFacultyId();
    }

    public void update(final Integer facultyId, final FacultyDTO facultyDTO) {
        final Faculty faculty = facultyRepository.findById(facultyId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(facultyDTO, faculty);
        facultyRepository.save(faculty);
    }

    public void delete(final Integer facultyId) {
        facultyRepository.deleteById(facultyId);
    }

    private FacultyDTO mapToDTO(final Faculty faculty, final FacultyDTO facultyDTO) {
        facultyDTO.setFacultyId(faculty.getFacultyId());
        facultyDTO.setTitle(faculty.getTitle());
        return facultyDTO;
    }

    private Faculty mapToEntity(final FacultyDTO facultyDTO, final Faculty faculty) {
        faculty.setTitle(facultyDTO.getTitle());
        return faculty;
    }

}
