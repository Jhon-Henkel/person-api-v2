package com.personapiv2.demo.unit.domain.address;

import com.personapiv2.demo.domain.address.Address;
import com.personapiv2.demo.dto.address.AddressDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
