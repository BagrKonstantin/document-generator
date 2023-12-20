package com.db.document_generator.rest;

import com.db.document_generator.model.PersonDTO;
import com.db.document_generator.service.PersonService;
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
@RequestMapping(value = "/api/people", produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonResource {

    private final PersonService personService;

    public PersonResource(final PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity<List<PersonDTO>> getAllPeople() {
        return ResponseEntity.ok(personService.findAll());
    }

    @GetMapping("/{personId}")
    public ResponseEntity<PersonDTO> getPerson(
            @PathVariable(name = "personId") final Long personId) {
        return ResponseEntity.ok(personService.get(personId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createPerson(@RequestBody @Valid final PersonDTO personDTO) {
        final Long createdPersonId = personService.create(personDTO);
        return new ResponseEntity<>(createdPersonId, HttpStatus.CREATED);
    }

    @PutMapping("/{personId}")
    public ResponseEntity<Long> updatePerson(@PathVariable(name = "personId") final Long personId,
            @RequestBody @Valid final PersonDTO personDTO) {
        personService.update(personId, personDTO);
        return ResponseEntity.ok(personId);
    }

    @DeleteMapping("/{personId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePerson(@PathVariable(name = "personId") final Long personId) {
        personService.delete(personId);
        return ResponseEntity.noContent().build();
    }

}
