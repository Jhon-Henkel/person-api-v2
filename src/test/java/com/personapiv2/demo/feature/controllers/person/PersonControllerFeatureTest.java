package com.personapiv2.demo.feature.controllers.person;

import com.personapiv2.demo.controllers.person.PersonController;
import com.personapiv2.demo.domain.person.Person;
import com.personapiv2.demo.dto.person.PersonDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
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
}
