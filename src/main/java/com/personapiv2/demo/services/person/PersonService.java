package com.personapiv2.demo.services.person;

import com.personapiv2.demo.domain.person.Person;
import com.personapiv2.demo.dto.person.PersonDTO;
import com.personapiv2.demo.exception.PersonNotFoundException;
import com.personapiv2.demo.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Person> findAll() {
        return this.personRepository.findAll();
    }

    public Person findById(Long id) throws PersonNotFoundException {
        return this.personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
    }

    public Person update(Long id, PersonDTO person) throws PersonNotFoundException {
        Person personToUpdate = this.personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
        personToUpdate.setFullName(person.fullName());
        personToUpdate.setBirthDate(person.birthDate());
        personToUpdate.setAddresses(person.addresses());
        return this.personRepository.save(personToUpdate);
    }

    public void delete(Long id) {
        this.personRepository.deleteById(id);
    }
}
