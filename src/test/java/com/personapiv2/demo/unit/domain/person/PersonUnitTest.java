package com.personapiv2.demo.unit.domain.person;

import com.personapiv2.demo.domain.person.Person;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PersonUnitTest {
    @Test
    public void testCreatePerson() {
        Person person = new Person();
        person.setFullName("John Doe");
        person.setBirthDate(LocalDate.of(2002, Month.MARCH, 30));

        assertEquals("John Doe", person.getFullName());
        assertEquals(LocalDate.of(2002, Month.MARCH, 30), person.getBirthDate());
    }
}
