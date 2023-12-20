package com.db.document_generator.repos;

import com.db.document_generator.domain.Document;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DocumentRepository extends JpaRepository<Document, Long> {
}
