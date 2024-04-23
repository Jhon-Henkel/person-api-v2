package com.personapiv2.demo.feature.controllers.person;

import com.personapiv2.demo.controllers.person.PersonController;
import com.personapiv2.demo.domain.person.Person;
import com.personapiv2.demo.dto.person.PersonDTO;
import com.personapiv2.demo.exception.PersonNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class PersonControllerFeatureTest {
    @Autowired
    private PersonController personController;

    @Test
    void testCreatePerson() {
        PersonDTO personDTO = new PersonDTO("John Doe", LocalDate.of(2002, Month.MARCH, 30));

        Person person = this.personController.createPerson(personDTO).getBody();

        assert person != null;
        assertEquals("John Doe", person.getFullName());
        assertEquals("2002-03-30", person.getBirthDate().toString());
    }

    @Test
    void testGetPersonById() throws PersonNotFoundException {
        PersonDTO personDTO = new PersonDTO("John Doe", LocalDate.of(2002, Month.MARCH, 30));

        Person person = this.personController.createPerson(personDTO).getBody();

        assert person != null;
        Long id = person.getId();

        Person personFound = this.personController.readOnePerson(id);

        assert personFound != null;
        assertEquals("John Doe", personFound.getFullName());
        assertEquals("2002-03-30", personFound.getBirthDate().toString());
    }

    @Test
    void testGetAllPersons() {
        PersonDTO personDTO = new PersonDTO("John Doe", LocalDate.of(2002, Month.MARCH, 30));

        this.personController.createPerson(personDTO);

        PersonDTO personDTO2 = new PersonDTO("Jane Doe", LocalDate.of(2003, Month.APRIL, 15));

        this.personController.createPerson(personDTO2);

        assertEquals(2, this.personController.readAllPersons().size());
    }

    @Test
    void testUpdatePerson() throws PersonNotFoundException {
        PersonDTO personDTO = new PersonDTO("John Doe", LocalDate.of(2002, Month.MARCH, 30));

        Person person = this.personController.createPerson(personDTO).getBody();

        assert person != null;
        Long id = person.getId();

        PersonDTO personDTO2 = new PersonDTO("Jane Doe Doe", LocalDate.of(2003, Month.APRIL, 16));

        this.personController.updatePerson(id, personDTO2);

        Person personUpdated = this.personController.readOnePerson(id);

        assert personUpdated != null;
        assertEquals("Jane Doe Doe", personUpdated.getFullName());
        assertEquals("2003-04-16", personUpdated.getBirthDate().toString());
    }

    @Test
    void testDeletePerson() {
        PersonDTO personDTO = new PersonDTO("John Doe", LocalDate.of(2002, Month.MARCH, 30));

        Person person = this.personController.createPerson(personDTO).getBody();

        assert person != null;
        Long id = person.getId();

        this.personController.deletePerson(id);

        assertEquals(0, this.personController.readAllPersons().size());
    }
}
