package com.personapiv2.demo.unit.dto.address;

import com.personapiv2.demo.dto.address.AddressDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AddressDtoUnitTest {
    @Test
    public void testCreateAddressDto() {
        AddressDTO addressDto = new AddressDTO(
                "Rua 1",
                123456,
                123,
                "Cidade 1",
                true
        );

        assertEquals("Rua 1", addressDto.publicPlace());
        assertEquals(123456, addressDto.zipCode());
        assertEquals(123, addressDto.number());
        assertEquals("Cidade 1", addressDto.city());
        assertEquals(true, addressDto.mainAddress());
    }
}
