package com.personapiv2.demo.unit.dto.person;

import com.personapiv2.demo.dto.address.AddressDTO;
import com.personapiv2.demo.dto.person.PersonDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PersonDtoUnitTest {
    @Test
    public void testCreatePersonDto() {
        PersonDTO personDto = new PersonDTO(
                "John Doe",
                LocalDate.of(2002, Month.MARCH, 30),
                List.of(
                        new AddressDTO("Rua 1", 123456, 123, "Cidade 1", true),
                        new AddressDTO("Rua 2", 654321, 321, "Cidade 2", false)
                )
        );

        assertEquals("John Doe", personDto.fullName());
        assertEquals(LocalDate.of(2002, Month.MARCH, 30), personDto.birthDate());
        assertEquals(2, personDto.addresses().size());
    }
}
