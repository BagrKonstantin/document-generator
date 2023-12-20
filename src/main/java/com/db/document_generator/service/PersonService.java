package com.db.document_generator.service;

import com.db.document_generator.domain.Person;
import com.db.document_generator.model.PersonDTO;
import com.db.document_generator.repos.PersonRepository;
import com.db.document_generator.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(final PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<PersonDTO> findAll() {
        final List<Person> persons = personRepository.findAll(Sort.by("personId"));
        return persons.stream()
                .map(person -> mapToDTO(person, new PersonDTO()))
                .toList();
    }

    public PersonDTO get(final Long personId) {
        return personRepository.findById(personId)
                .map(person -> mapToDTO(person, new PersonDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PersonDTO personDTO) {
        final Person person = new Person();
        mapToEntity(personDTO, person);
        return personRepository.save(person).getPersonId();
    }

    public void update(final Long personId, final PersonDTO personDTO) {
        final Person person = personRepository.findById(personId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(personDTO, person);
        personRepository.save(person);
    }

    public void delete(final Long personId) {
        personRepository.deleteById(personId);
    }

    private PersonDTO mapToDTO(final Person person, final PersonDTO personDTO) {
        personDTO.setPersonId(person.getPersonId());
        personDTO.setFirstname(person.getFirstname());
        personDTO.setSurname(person.getSurname());
        personDTO.setLastname(person.getLastname());
        personDTO.setStatus(person.getStatus());
        return personDTO;
    }

    private Person mapToEntity(final PersonDTO personDTO, final Person person) {
        person.setFirstname(personDTO.getFirstname());
        person.setSurname(personDTO.getSurname());
        person.setLastname(personDTO.getLastname());
        person.setStatus(personDTO.getStatus());
        return person;
    }

}
