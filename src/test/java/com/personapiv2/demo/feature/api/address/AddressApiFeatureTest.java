package com.personapiv2.demo.feature.api.address;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personapiv2.demo.controllers.person.PersonController;
import com.personapiv2.demo.domain.address.Address;
import com.personapiv2.demo.domain.person.Person;
import com.personapiv2.demo.dto.address.AddressDTO;
import com.personapiv2.demo.dto.person.PersonDTO;
import com.personapiv2.demo.repositories.PersonRepository;
import com.personapiv2.demo.services.address.AddressService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AddressApiFeatureTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private AddressService addressService;
    @Autowired
    private PersonController personController;

    @Test
    public void testReadPersonAddressPersonNotFound() throws Exception {
        mockMvc.perform(get("/api/v2/address/person/9999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Person not found with ID 9999")));
    }

    @Test
    public void testReadPersonAddressPerson() throws Exception {
        PersonDTO personDTO = new PersonDTO(
                "John Doe",
                LocalDate.of(2002, Month.MARCH, 30),
                List.of(
                        new AddressDTO("Rua 1", 123456, 123, "Cidade 1", true),
                        new AddressDTO("Rua 2", 654321, 321, "Cidade 2", false)
                )
        );

        Person person = personRepository.save(new Person(personDTO));

        mockMvc.perform(get("/api/v2/address/person/" + person.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)));
    }

    @Test
    public void testUpdateAddressAddressNotFound() throws Exception {
        mockMvc.perform(put("/api/v2/address/9999")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(new AddressDTO("Rua 1", 123456, 123, "Cidade 1", true))))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Address not found with ID 9999")));
    }

    @Test
    public void testUpdateAddress() throws Exception {
        PersonDTO personDTO = new PersonDTO(
                "John Doe",
                LocalDate.of(2002, Month.MARCH, 30),
                List.of(
                        new AddressDTO("Rua 1", 123456, 123, "Cidade 1", true),
                        new AddressDTO("Rua 2", 654321, 321, "Cidade 2", false)
                )
        );

        Person person = personRepository.save(new Person(personDTO));

        mockMvc.perform(put("/api/v2/address/" + person.getAddresses().getFirst().getId())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(new AddressDTO("Rua 2", 654321, 321, "Cidade 2", false))))
                .andExpect(status().isOk());

        Address addressUpdated = addressService.findById(person.getAddresses().getFirst().getId());

        assert addressUpdated.getPublicPlace().equals("Rua 2");
    }

    @Test
    public void testUpdateWithInvalidZipCode() throws Exception {
        Map<String, Object> addressMap = new HashMap<>();
        addressMap.put("publicPlace", "Avenida ABC");
        addressMap.put("zipCode", null);
        addressMap.put("number", 123);
        addressMap.put("city", "Cidade 1");
        addressMap.put("mainAddress", true);

        mockMvc.perform(put("/api/v2/address/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(addressMap)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("zipCode must not be null")));
    }

    @Test
    public void testUpdateWithInvalidPublicPlace() throws Exception {
        Map<String, Object> addressMap = new HashMap<>();
        addressMap.put("publicPlace", null);
        addressMap.put("zipCode", 88750000);
        addressMap.put("number", 123);
        addressMap.put("city", "Cidade 1");
        addressMap.put("mainAddress", true);

        mockMvc.perform(put("/api/v2/address/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(addressMap)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("publicPlace must not be null")));
    }

    @Test
    public void testUpdateWithInvalidNumber() throws Exception {
        Map<String, Object> addressMap = new HashMap<>();
        addressMap.put("publicPlace", "Avenida ABC");
        addressMap.put("zipCode", 88750000);
        addressMap.put("number", null);
        addressMap.put("city", "Cidade 1");
        addressMap.put("mainAddress", true);

        mockMvc.perform(put("/api/v2/address/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(addressMap)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("number must not be null")));
    }

    @Test
    public void testUpdateWithInvalidCity() throws Exception {
        Map<String, Object> addressMap = new HashMap<>();
        addressMap.put("publicPlace", "Avenida ABC");
        addressMap.put("zipCode", 88750000);
        addressMap.put("number", 123);
        addressMap.put("city", null);
        addressMap.put("mainAddress", true);

        mockMvc.perform(put("/api/v2/address/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(addressMap)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("city must not be null")));
    }

    @Test
    public void testUpdateWithInvalidMainAddress() throws Exception {
        Map<String, Object> addressMap = new HashMap<>();
        addressMap.put("publicPlace", "Avenida ABC");
        addressMap.put("zipCode", 88750000);
        addressMap.put("number", 123);
        addressMap.put("city", "Cidade 1");
        addressMap.put("mainAddress", null);

        mockMvc.perform(put("/api/v2/address/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(addressMap)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("mainAddress must not be null")));
    }

    @Test
    public void testUpdateWithoutZipCode() throws Exception {
        Map<String, Object> addressMap = new HashMap<>();
        addressMap.put("publicPlace", "Avenida ABC");
        addressMap.put("number", 123);
        addressMap.put("city", "Cidade 1");
        addressMap.put("mainAddress", true);

        mockMvc.perform(put("/api/v2/address/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(addressMap)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("se error: Missing required creator property 'zipCode' (index 1)")));
    }

    @Test
    public void testUpdateWithoutPublicPlace() throws Exception {
        Map<String, Object> addressMap = new HashMap<>();
        addressMap.put("zipCode", 88750000);
        addressMap.put("number", 123);
        addressMap.put("city", "Cidade 1");
        addressMap.put("mainAddress", true);

        mockMvc.perform(put("/api/v2/address/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(addressMap)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("se error: Missing required creator property 'publicPlace' (index 0)")));
    }

    @Test
    public void testUpdateWithoutNumber() throws Exception {
        Map<String, Object> addressMap = new HashMap<>();
        addressMap.put("publicPlace", "Avenida ABC");
        addressMap.put("zipCode", 88750000);
        addressMap.put("city", "Cidade 1");
        addressMap.put("mainAddress", true);

        mockMvc.perform(put("/api/v2/address/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(addressMap)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("se error: Missing required creator property 'number' (index 2)")));
    }

    @Test
    public void testUpdateWithoutCity() throws Exception {
        Map<String, Object> addressMap = new HashMap<>();
        addressMap.put("publicPlace", "Avenida ABC");
        addressMap.put("zipCode", 88750000);
        addressMap.put("number", 123);
        addressMap.put("mainAddress", true);

        mockMvc.perform(put("/api/v2/address/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(addressMap)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("se error: Missing required creator property 'city' (index 3)")));
    }

    @Test
    public void testUpdateWithoutMainAddress() throws Exception {
        Map<String, Object> addressMap = new HashMap<>();
        addressMap.put("publicPlace", "Avenida ABC");
        addressMap.put("zipCode", 88750000);
        addressMap.put("number", 123);
        addressMap.put("city", "Cidade 1");

        mockMvc.perform(put("/api/v2/address/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(addressMap)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("se error: Missing required creator property 'mainAddress' (index 4)")));
    }

    @Test
    public void testCreatePersonAddressPersonNotFound() throws Exception {
        mockMvc.perform(post("/api/v2/address/person/9999")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(new AddressDTO("Rua 1", 123456, 123, "Cidade 1", true))))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Person not found with ID 9999")));
    }

    @Test
    public void testDeleteAddress() throws Exception {
        PersonDTO personDTO = new PersonDTO(
                "John Doe",
                LocalDate.of(2002, Month.MARCH, 30),
                List.of(
                        new AddressDTO("Rua 1", 123456, 123, "Cidade 1", true),
                        new AddressDTO("Rua 2", 654321, 321, "Cidade 2", false)
                )
        );

        Person person = personRepository.save(new Person(personDTO));

        Optional<Address> address = person.getAddresses().stream().findFirst();

        mockMvc.perform(delete("/api/v2/address/" + address.get().getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v2/address/person/" + person.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(1)));
    }
}
