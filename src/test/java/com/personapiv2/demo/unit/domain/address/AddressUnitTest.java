package com.personapiv2.demo.unit.domain.address;

import com.personapiv2.demo.domain.address.Address;
import com.personapiv2.demo.domain.person.Person;
import com.personapiv2.demo.dto.address.AddressDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AddressUnitTest {
    @Test
    public void testCreateAddress() {
        Address address = new Address();
        address.setPublicPlace("Rua das Flores");
        address.setZipCode(12345678);
        address.setNumber(123);
        address.setCity("São Paulo");
        address.setMainAddress(true);

        assertEquals("Rua das Flores", address.getPublicPlace());
        assertEquals(12345678, address.getZipCode());
        assertEquals(123, address.getNumber());
        assertEquals("São Paulo", address.getCity());
        assertEquals(true, address.getMainAddress());
    }

    @Test
    public void testCreateAddressDTO() {
        AddressDTO addressDTO = new AddressDTO(
                "Rua das Flores",
                12345678,
                123,
                "São Paulo",
                true
        );

        Address address = new Address(addressDTO);

        assertEquals("Rua das Flores", address.getPublicPlace());
        assertEquals(12345678, address.getZipCode());
        assertEquals(123, address.getNumber());
        assertEquals("São Paulo", address.getCity());
        assertEquals(true, address.getMainAddress());
    }

    @Test
    public void testEqualsWithDifferentId() {
        Address address1 = new Address();
        address1.setId(1L);

        Address address2 = new Address();
        address2.setId(2L);

        assertFalse(address1.equals(address2));
    }

    @Test
    public void testEqualsWithDifferentPerson() {
        Address address1 = new Address();
        Person person1 = new Person();
        person1.setFullName("John Doe");
        address1.setPerson(person1);

        Address address2 = new Address();
        Person person2 = new Person();
        person2.setFullName("Jane Doe");
        address2.setPerson(person2);

        assertFalse(address1.equals(address2));
    }

    @Test
    public void testHashCodeWithDifferentId() {
        Address address1 = new Address();
        address1.setId(1L);

        Address address2 = new Address();
        address2.setId(2L);

        assertNotEquals(address1.hashCode(), address2.hashCode());
    }

    @Test
    public void testHashCodeWithDifferentPerson() {
        Address address1 = new Address();
        Person person1 = new Person();
        person1.setFullName("John Doe");
        address1.setPerson(person1);

        Address address2 = new Address();
        Person person2 = new Person();
        person2.setFullName("Jane Doe");
        address2.setPerson(person2);

        assertNotEquals(address1.hashCode(), address2.hashCode());
    }

    @Test
    public void testToString() {
        Address address = new Address();
        address.setPublicPlace("Rua das Flores");
        address.setZipCode(12345678);
        address.setNumber(123);
        address.setCity("São Paulo");
        address.setMainAddress(true);

        String expected = "Address(id=null, publicPlace=Rua das Flores, zipCode=12345678, number=123, city=São Paulo, mainAddress=true, person=null)";
        assertEquals(expected, address.toString());
    }

    @Test
    public void testSetId() {
        Address address = new Address();
        address.setId(1L);

        assertEquals(1L, address.getId());
    }

    @Test
    public void testSetPerson() {
        Address address = new Address();
        Person person = new Person();
        person.setFullName("John Doe");

        address.setPerson(person);

        assertEquals(person, address.getPerson());
        assertEquals("John Doe", address.getPerson().getFullName());
    }
}
