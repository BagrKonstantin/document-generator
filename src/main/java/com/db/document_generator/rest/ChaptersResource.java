package com.db.document_generator.rest;

import com.db.document_generator.model.ChaptersDTO;
import com.db.document_generator.service.ChaptersService;
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
@RequestMapping(value = "/api/chapterss", produces = MediaType.APPLICATION_JSON_VALUE)
public class ChaptersResource {

    private final ChaptersService chaptersService;

    public ChaptersResource(final ChaptersService chaptersService) {
        this.chaptersService = chaptersService;
    }

    @GetMapping
    public ResponseEntity<List<ChaptersDTO>> getAllChapterss() {
        return ResponseEntity.ok(chaptersService.findAll());
    }

    @GetMapping("/{chapterId}")
    public ResponseEntity<ChaptersDTO> getChapters(
            @PathVariable(name = "chapterId") final Integer chapterId) {
        return ResponseEntity.ok(chaptersService.get(chapterId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createChapters(
            @RequestBody @Valid final ChaptersDTO chaptersDTO) {
        final Integer createdChapterId = chaptersService.create(chaptersDTO);
        return new ResponseEntity<>(createdChapterId, HttpStatus.CREATED);
    }

    @PutMapping("/{chapterId}")
    public ResponseEntity<Integer> updateChapters(
            @PathVariable(name = "chapterId") final Integer chapterId,
            @RequestBody @Valid final ChaptersDTO chaptersDTO) {
        chaptersService.update(chapterId, chaptersDTO);
        return ResponseEntity.ok(chapterId);
    }

    @DeleteMapping("/{chapterId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteChapters(
            @PathVariable(name = "chapterId") final Integer chapterId) {
        chaptersService.delete(chapterId);
        return ResponseEntity.noContent().build();
    }

}
