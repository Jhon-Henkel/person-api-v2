package com.personapiv2.demo.unit.dto.person;

import com.personapiv2.demo.dto.person.PersonDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PersonDtoUnitTest {
    @Test
    public void testCreatePersonDto() {
        PersonDTO personDto = new PersonDTO("John Doe", LocalDate.of(2002, Month.MARCH, 30));

        assertEquals("John Doe", personDto.fullName());
        assertEquals(LocalDate.of(2002, Month.MARCH, 30), personDto.birthDate());
    }
}
