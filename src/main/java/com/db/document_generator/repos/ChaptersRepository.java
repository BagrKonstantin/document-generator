package com.db.document_generator.repos;

import com.db.document_generator.domain.Chapters;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ChaptersRepository extends JpaRepository<Chapters, Integer> {

    boolean existsByCodeIgnoreCase(String code);

}
