package com.db.document_generator.repos;

import com.db.document_generator.domain.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FacultyRepository extends JpaRepository<Faculty, Integer> {
}
