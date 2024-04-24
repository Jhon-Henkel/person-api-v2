package com.personapiv2.demo.feature.controllers.address;

import com.personapiv2.demo.controllers.address.AddressController;
import com.personapiv2.demo.domain.address.Address;
import com.personapiv2.demo.domain.person.Person;
import com.personapiv2.demo.dto.address.AddressDTO;
import com.personapiv2.demo.dto.person.PersonDTO;
import com.personapiv2.demo.exception.AddressNotFoundException;
import com.personapiv2.demo.exception.PersonNotFoundException;
import com.personapiv2.demo.services.person.PersonService;
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
public class AddressControllerFeatureTest {
    @Autowired
    private PersonService personService;

    @Autowired
    private AddressController addressController;

    @Test
    void testReadPersonAddresses() throws PersonNotFoundException {
        PersonDTO person = new PersonDTO(
                "John Doe",
                LocalDate.of(2002, Month.MARCH, 30),
                List.of(
                        new AddressDTO("Rua 1", 123456, 123, "Cidade 1", true),
                        new AddressDTO("Rua 2", 654321, 321, "Cidade 2", false)
                )
        );

        Person personCreated = personService.createPerson(person);
        assert personCreated != null;

        List<Address> addresses = this.addressController.readPersonAddresses(personCreated.getId());
        assert addresses != null;

        assertEquals(2, addresses.size());
    }

    @Test
    void testUpdate() throws PersonNotFoundException, AddressNotFoundException {
        PersonDTO person = new PersonDTO(
                "John Doe",
                LocalDate.of(2002, Month.MARCH, 30),
                List.of(
                        new AddressDTO("Rua 1", 123456, 123, "Cidade 1", true),
                        new AddressDTO("Rua 2", 654321, 321, "Cidade 2", false)
                )
        );

        Person personCreated = personService.createPerson(person);
        assert personCreated != null;

        List<Address> addresses = this.addressController.readPersonAddresses(personCreated.getId());
        assert addresses != null;

        AddressDTO addressDTO = new AddressDTO("Rua 3", 123456, 123, "Cidade 1", true);
        Address addressUpdated = this.addressController.update(addresses.getFirst().getId(), addressDTO);
        assert addressUpdated != null;

        assertEquals("Rua 3", addressUpdated.getPublicPlace());
    }

    @Test
    void testCreatePersonAddress() throws PersonNotFoundException {
        PersonDTO person = new PersonDTO(
                "John Doe",
                LocalDate.of(2002, Month.MARCH, 30),
                List.of(
                        new AddressDTO("Rua 1", 123456, 123, "Cidade 1", true),
                        new AddressDTO("Rua 2", 654321, 321, "Cidade 2", false)
                )
        );

        Person personCreated = personService.createPerson(person);
        assert personCreated != null;

        AddressDTO addressDTO = new AddressDTO("Rua 3", 123456, 123, "Cidade 1", true);
        Person personUpdated = this.addressController.createPersonAddress(personCreated.getId(), addressDTO);
        assert personUpdated != null;

        assertEquals(3, personUpdated.getAddresses().size());
    }
}
