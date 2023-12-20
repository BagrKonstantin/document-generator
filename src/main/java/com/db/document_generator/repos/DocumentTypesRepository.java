package com.db.document_generator.repos;

import com.db.document_generator.domain.DocumentTypes;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DocumentTypesRepository extends JpaRepository<DocumentTypes, Integer> {

    boolean existsByTittleIgnoreCase(String tittle);

}
