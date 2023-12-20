package com.db.document_generator.service;

import com.db.document_generator.domain.Chapters;
import com.db.document_generator.domain.Classes;
import com.db.document_generator.model.ClassesDTO;
import com.db.document_generator.repos.ChaptersRepository;
import com.db.document_generator.repos.ClassesRepository;
import com.db.document_generator.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ClassesService {

    private final ClassesRepository classesRepository;
    private final ChaptersRepository chaptersRepository;

    public ClassesService(final ClassesRepository classesRepository,
            final ChaptersRepository chaptersRepository) {
        this.classesRepository = classesRepository;
        this.chaptersRepository = chaptersRepository;
    }

    public List<ClassesDTO> findAll() {
        final List<Classes> classeses = classesRepository.findAll(Sort.by("classId"));
        return classeses.stream()
                .map(classes -> mapToDTO(classes, new ClassesDTO()))
                .toList();
    }

    public ClassesDTO get(final Integer classId) {
        return classesRepository.findById(classId)
                .map(classes -> mapToDTO(classes, new ClassesDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final ClassesDTO classesDTO) {
        final Classes classes = new Classes();
        mapToEntity(classesDTO, classes);
        return classesRepository.save(classes).getClassId();
    }

    public void update(final Integer classId, final ClassesDTO classesDTO) {
        final Classes classes = classesRepository.findById(classId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(classesDTO, classes);
        classesRepository.save(classes);
    }

    public void delete(final Integer classId) {
        classesRepository.deleteById(classId);
    }

    private ClassesDTO mapToDTO(final Classes classes, final ClassesDTO classesDTO) {
        classesDTO.setClassId(classes.getClassId());
        classesDTO.setTitle(classes.getTitle());
        classesDTO.setDescription(classes.getDescription());
        classesDTO.setCode(classes.getCode());
        classesDTO.setChapter(classes.getChapter() == null ? null : classes.getChapter().getChapterId());
        return classesDTO;
    }

    private Classes mapToEntity(final ClassesDTO classesDTO, final Classes classes) {
        classes.setTitle(classesDTO.getTitle());
        classes.setDescription(classesDTO.getDescription());
        classes.setCode(classesDTO.getCode());
        final Chapters chapter = classesDTO.getChapter() == null ? null : chaptersRepository.findById(classesDTO.getChapter())
                .orElseThrow(() -> new NotFoundException("chapter not found"));
        classes.setChapter(chapter);
        return classes;
    }

}
