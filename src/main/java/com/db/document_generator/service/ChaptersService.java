package com.db.document_generator.service;

import com.db.document_generator.domain.Chapters;
import com.db.document_generator.model.ChaptersDTO;
import com.db.document_generator.repos.ChaptersRepository;
import com.db.document_generator.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ChaptersService {

    private final ChaptersRepository chaptersRepository;

    public ChaptersService(final ChaptersRepository chaptersRepository) {
        this.chaptersRepository = chaptersRepository;
    }

    public List<ChaptersDTO> findAll() {
        final List<Chapters> chapterses = chaptersRepository.findAll(Sort.by("chapterId"));
        return chapterses.stream()
                .map(chapters -> mapToDTO(chapters, new ChaptersDTO()))
                .toList();
    }

    public ChaptersDTO get(final Integer chapterId) {
        return chaptersRepository.findById(chapterId)
                .map(chapters -> mapToDTO(chapters, new ChaptersDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final ChaptersDTO chaptersDTO) {
        final Chapters chapters = new Chapters();
        mapToEntity(chaptersDTO, chapters);
        return chaptersRepository.save(chapters).getChapterId();
    }

    public void update(final Integer chapterId, final ChaptersDTO chaptersDTO) {
        final Chapters chapters = chaptersRepository.findById(chapterId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(chaptersDTO, chapters);
        chaptersRepository.save(chapters);
    }

    public void delete(final Integer chapterId) {
        chaptersRepository.deleteById(chapterId);
    }

    private ChaptersDTO mapToDTO(final Chapters chapters, final ChaptersDTO chaptersDTO) {
        chaptersDTO.setChapterId(chapters.getChapterId());
        chaptersDTO.setTitle(chapters.getTitle());
        chaptersDTO.setCode(chapters.getCode());
        return chaptersDTO;
    }

    private Chapters mapToEntity(final ChaptersDTO chaptersDTO, final Chapters chapters) {
        chapters.setTitle(chaptersDTO.getTitle());
        chapters.setCode(chaptersDTO.getCode());
        return chapters;
    }

    public boolean codeExists(final String code) {
        return chaptersRepository.existsByCodeIgnoreCase(code);
    }

}
