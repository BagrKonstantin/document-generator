package com.db.document_generator.repos;

import com.db.document_generator.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PersonRepository extends JpaRepository<Person, Long> {
}
