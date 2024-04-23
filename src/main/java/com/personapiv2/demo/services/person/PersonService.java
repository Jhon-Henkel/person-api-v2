package com.personapiv2.demo.services.person;

import com.personapiv2.demo.domain.person.Person;
import com.personapiv2.demo.dto.person.PersonDTO;
import com.personapiv2.demo.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    public Person createPerson(PersonDTO person) {
        Person newPerson = new Person(person);
        this.savePerson(newPerson);
        return newPerson;
    }

    private void savePerson(Person person) {
        this.personRepository.save(person);
    }
}
