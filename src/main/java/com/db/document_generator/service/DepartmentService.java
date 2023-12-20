package com.db.document_generator.service;

import com.db.document_generator.domain.Department;
import com.db.document_generator.domain.Faculty;
import com.db.document_generator.model.DepartmentDTO;
import com.db.document_generator.repos.DepartmentRepository;
import com.db.document_generator.repos.FacultyRepository;
import com.db.document_generator.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final FacultyRepository facultyRepository;

    public DepartmentService(final DepartmentRepository departmentRepository,
            final FacultyRepository facultyRepository) {
        this.departmentRepository = departmentRepository;
        this.facultyRepository = facultyRepository;
    }

    public List<DepartmentDTO> findAll() {
        final List<Department> departments = departmentRepository.findAll(Sort.by("departmentId"));
        return departments.stream()
                .map(department -> mapToDTO(department, new DepartmentDTO()))
                .toList();
    }

    public DepartmentDTO get(final Integer departmentId) {
        return departmentRepository.findById(departmentId)
                .map(department -> mapToDTO(department, new DepartmentDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final DepartmentDTO departmentDTO) {
        final Department department = new Department();
        mapToEntity(departmentDTO, department);
        return departmentRepository.save(department).getDepartmentId();
    }

    public void update(final Integer departmentId, final DepartmentDTO departmentDTO) {
        final Department department = departmentRepository.findById(departmentId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(departmentDTO, department);
        departmentRepository.save(department);
    }

    public void delete(final Integer departmentId) {
        departmentRepository.deleteById(departmentId);
    }

    private DepartmentDTO mapToDTO(final Department department, final DepartmentDTO departmentDTO) {
        departmentDTO.setDepartmentId(department.getDepartmentId());
        departmentDTO.setTitle(department.getTitle());
        departmentDTO.setFaculty(department.getFaculty() == null ? null : department.getFaculty().getFacultyId());
        return departmentDTO;
    }

    private Department mapToEntity(final DepartmentDTO departmentDTO, final Department department) {
        department.setTitle(departmentDTO.getTitle());
        final Faculty faculty = departmentDTO.getFaculty() == null ? null : facultyRepository.findById(departmentDTO.getFaculty())
                .orElseThrow(() -> new NotFoundException("faculty not found"));
        department.setFaculty(faculty);
        return department;
    }

}
