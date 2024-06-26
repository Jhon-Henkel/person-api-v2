package com.personapiv2.demo.controllers.person;

import com.personapiv2.demo.domain.person.Person;
import com.personapiv2.demo.dto.person.PersonDTO;
import com.personapiv2.demo.exception.PersonNotFoundException;
import com.personapiv2.demo.services.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/person")
public class PersonController {
    @Autowired
    private PersonService personService;

    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody PersonDTO person) {
        Person newPerson = this.personService.createPerson(person);
        return new ResponseEntity<>(newPerson, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Person> readAllPersons() {
        return personService.findAll();
    }

    @GetMapping("/{id}")
    public Person readOnePerson(@PathVariable Long id) throws PersonNotFoundException{
        return personService.findById(id);
    }

    @PutMapping("/{id}")
    public Person updatePerson(@PathVariable Long id, @RequestBody PersonDTO person) throws PersonNotFoundException {
        return personService.update(id, person);
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable Long id) {
        personService.delete(id);
    }
}
