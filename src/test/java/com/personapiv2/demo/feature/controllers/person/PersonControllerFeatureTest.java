package com.personapiv2.demo.feature.controllers.person;

import com.personapiv2.demo.controllers.person.PersonController;
import com.personapiv2.demo.domain.person.Person;
import com.personapiv2.demo.dto.address.AddressDTO;
import com.personapiv2.demo.dto.person.PersonDTO;
import com.personapiv2.demo.exception.PersonNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class PersonControllerFeatureTest {
    @Autowired
    private PersonController personController;

    @Test
    void testCreatePerson() {
        PersonDTO personDTO = new PersonDTO(
                "John Doe",
                LocalDate.of(2002, Month.MARCH, 30),
                List.of(
                        new AddressDTO("Rua 1", 123456, 123, "Cidade 1", true),
                        new AddressDTO("Rua 2", 654321, 321, "Cidade 2", false)
                )
        );

        Person person = this.personController.createPerson(personDTO).getBody();

        assert person != null;
        assertEquals("John Doe", person.getFullName());
        assertEquals("2002-03-30", person.getBirthDate().toString());
    }

    @Test
    void testGetPersonById() throws PersonNotFoundException {
        PersonDTO personDTO = new PersonDTO(
                "John Doe",
                LocalDate.of(2002, Month.MARCH, 30),
                List.of(
                        new AddressDTO("Rua 1", 123456, 123, "Cidade 1", true),
                        new AddressDTO("Rua 2", 654321, 321, "Cidade 2", false)
                )
        );

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
        PersonDTO personDTO = new PersonDTO(
                "John Doe",
                LocalDate.of(2002, Month.MARCH, 30),
                List.of(
                        new AddressDTO("Rua 1", 123456, 123, "Cidade 1", true),
                        new AddressDTO("Rua 2", 654321, 321, "Cidade 2", false)
                )
        );

        this.personController.createPerson(personDTO);

        PersonDTO personDTO2 = new PersonDTO(
                "John Doe",
                LocalDate.of(2002, Month.MARCH, 30),
                List.of(
                        new AddressDTO("Rua 1", 123456, 123, "Cidade 1", true),
                        new AddressDTO("Rua 2", 654321, 321, "Cidade 2", false)
                )
        );

        this.personController.createPerson(personDTO2);

        assertEquals(2, this.personController.readAllPersons().size());
    }

    @Test
    void testUpdatePerson() throws PersonNotFoundException {
        PersonDTO personDTO = new PersonDTO(
                "John Doe",
                LocalDate.of(2002, Month.MARCH, 30),
                List.of(
                        new AddressDTO("Rua 1", 123456, 123, "Cidade 1", true),
                        new AddressDTO("Rua 2", 654321, 321, "Cidade 2", false)
                )
        );

        Person person = this.personController.createPerson(personDTO).getBody();

        assert person != null;
        Long id = person.getId();

        PersonDTO updatedPersonDTO = new PersonDTO(
                "Jane Doe Doe",
                LocalDate.of(2003, Month.APRIL, 30),
                List.of(
                        new AddressDTO("Rua 1", 123456, 123, "Cidade 1", true),
                        new AddressDTO("Rua 2", 654321, 321, "Cidade 2", false)
                )
        );

        Person updatedPerson = this.personController.updatePerson(id, updatedPersonDTO);
        assert updatedPerson != null;

        Person retrievedPerson = this.personController.readOnePerson(id);

        assert retrievedPerson != null;
        assertEquals("Jane Doe Doe", retrievedPerson.getFullName());
        assertEquals("2003-04-30", retrievedPerson.getBirthDate().toString());
        assertEquals(2, retrievedPerson.getAddresses().size());
    }

    @Test
    void testDeletePerson() {
        PersonDTO personDTO = new PersonDTO(
                "John Doe",
                LocalDate.of(2002, Month.MARCH, 30),
                List.of(
                        new AddressDTO("Rua 1", 123456, 123, "Cidade 1", true),
                        new AddressDTO("Rua 2", 654321, 321, "Cidade 2", false)
                )
        );

        Person person = this.personController.createPerson(personDTO).getBody();

        assert person != null;
        Long id = person.getId();

        this.personController.deletePerson(id);

        assertEquals(0, this.personController.readAllPersons().size());
    }
}
