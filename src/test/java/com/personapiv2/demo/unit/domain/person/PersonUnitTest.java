package com.personapiv2.demo.unit.domain.person;

import com.personapiv2.demo.domain.person.Person;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    public void testEqualsWithDifferentId() {
        Person person1 = new Person();
        person1.setId(1L);

        Person person2 = new Person();
        person2.setId(2L);

        assertFalse(person1.equals(person2));
    }

    @Test
    public void testHashCodeWithDifferentId() {
        Person person1 = new Person();
        person1.setId(1L);

        Person person2 = new Person();
        person2.setId(2L);

        assertNotEquals(person1.hashCode(), person2.hashCode());
    }

    @Test
    public void testToString() {
        Person person = new Person();
        person.setFullName("John Doe");
        person.setBirthDate(LocalDate.of(2002, Month.MARCH, 30));

        String expected = "Person(id=null, fullName=John Doe, birthDate=2002-03-30, addresses=[])";
        assertEquals(expected, person.toString());
    }

    @Test
    public void testSetId() {
        Person person = new Person();
        person.setId(1L);

        assertEquals(1L, person.getId());
    }
}
