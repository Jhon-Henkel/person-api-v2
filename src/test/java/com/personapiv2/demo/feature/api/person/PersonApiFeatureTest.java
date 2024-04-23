package com.personapiv2.demo.feature.api.person;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personapiv2.demo.dto.address.AddressDTO;
import com.personapiv2.demo.dto.person.PersonDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PersonApiFeatureTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreatePerson() throws Exception {
        PersonDTO personDTO = new PersonDTO(
                "John Doe",
                LocalDate.of(2002, Month.MARCH, 30),
                List.of(
                        new AddressDTO("Rua 1", 123456, 123, "Cidade 1", true),
                        new AddressDTO("Rua 2", 654321, 321, "Cidade 2", false)
                )
        );

        mockMvc.perform(post("/api/v2/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void testCreatePersonWithNullFullName() throws Exception {
        Map<String, Object> personMap = new HashMap<>();
        personMap.put("fullName", null);
        personMap.put("birthDate", LocalDate.of(2002, Month.MARCH, 30));
        personMap.put("addresses", List.of(
                new AddressDTO("Rua 1", 123456, 123, "Cidade 1", true),
                new AddressDTO("Rua 2", 654321, 321, "Cidade 2", false)
        ));

        mockMvc.perform(post("/api/v2/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personMap)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("fullName must not be null")));
    }

    @Test
    void testCreatePersonWithoutFullName() throws Exception {
        Map<String, Object> personMap = new HashMap<>();
        personMap.put("birthDate", LocalDate.of(2002, Month.MARCH, 30));

        mockMvc.perform(post("/api/v2/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personMap)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("se error: Missing required creator property 'fullName' (index 0)")));
    }

    @Test
    void testCreatePersonWithNullAddresses() throws Exception {
        Map<String, Object> personMap = new HashMap<>();
        personMap.put("fullName", "John Doe");
        personMap.put("birthDate", LocalDate.of(2002, Month.MARCH, 30));
        personMap.put("addresses", null);

        mockMvc.perform(post("/api/v2/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personMap)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("addresses must not be null")));
    }

    @Test
    void testCreatePersonWithoutAddresses() throws Exception {
        Map<String, Object> personMap = new HashMap<>();
        personMap.put("fullName", "John Doe");
        personMap.put("birthDate", LocalDate.of(2002, Month.MARCH, 30));

        mockMvc.perform(post("/api/v2/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personMap)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("se error: Missing required creator property 'addresses' (index 2)")));
    }

    @Test
    void testCreatePersonWithNullBirthDate() throws Exception {
        Map<String, Object> personMap = new HashMap<>();
        personMap.put("fullName", "John Doe");
        personMap.put("birthDate", null);
        personMap.put("addresses", List.of(
                new AddressDTO("Rua 1", 123456, 123, "Cidade 1", true),
                new AddressDTO("Rua 2", 654321, 321, "Cidade 2", false)
        ));

        mockMvc.perform(post("/api/v2/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personMap)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("birthDate must not be null")));
    }

    @Test
    void testCreatePersonWithoutBirthDate() throws Exception {
        Map<String, Object> personMap = new HashMap<>();
        personMap.put("fullName", "John Doe");

        mockMvc.perform(post("/api/v2/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personMap)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("se error: Missing required creator property 'birthDate' (index 1)")));
    }

    @Test
    void testGetAllPersonsWithoutPersons() throws Exception {
        mockMvc.perform(get("/api/v2/person"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(0)));
    }

    @Test
    void testGetAllPersonsWithPersons() throws Exception {
        PersonDTO personDTO = new PersonDTO(
                "John Doe",
                LocalDate.of(2002, Month.MARCH, 30),
                List.of(
                        new AddressDTO("Rua 1", 123456, 123, "Cidade 1", true),
                        new AddressDTO("Rua 2", 654321, 321, "Cidade 2", false)
                )
        );

        mockMvc.perform(post("/api/v2/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personDTO)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/v2/person"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$[0].fullName", is("John Doe")))
                .andExpect(jsonPath("$[0].birthDate", is("2002-03-30")));
    }

    @Test
    void testGetPersonById() throws Exception {
        PersonDTO personDTO = new PersonDTO(
                "John Doe",
                LocalDate.of(2002, Month.MARCH, 30),
                List.of(
                        new AddressDTO("Rua 1", 123456, 123, "Cidade 1", true),
                        new AddressDTO("Rua 2", 654321, 321, "Cidade 2", false)
                )
        );

        mockMvc.perform(post("/api/v2/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personDTO)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/v2/person/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName", is("John Doe")))
                .andExpect(jsonPath("$.birthDate", is("2002-03-30")));
    }

    @Test
    void testGetPersonByIdNotFound() throws Exception {
        mockMvc.perform(get("/api/v2/person/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Person not found with ID 1")));
    }

    @Test
    void testUpdatePerson() throws Exception {
        PersonDTO personDTO = new PersonDTO(
                "John Doe",
                LocalDate.of(2002, Month.MARCH, 30),
                List.of(
                        new AddressDTO("Rua 1", 123456, 123, "Cidade 1", true),
                        new AddressDTO("Rua 2", 654321, 321, "Cidade 2", false)
                )
        );

        mockMvc.perform(post("/api/v2/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personDTO)))
                .andExpect(status().isCreated());

        PersonDTO personDTOUpdated = new PersonDTO(
                "Jane Doe Doe",
                LocalDate.of(2003, Month.APRIL, 16),
                List.of(
                        new AddressDTO("Rua 1", 123456, 123, "Cidade 1", true),
                        new AddressDTO("Rua 2", 654321, 321, "Cidade 2", false)
                )
        );

        mockMvc.perform(put("/api/v2/person/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personDTOUpdated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName", is("Jane Doe Doe")))
                .andExpect(jsonPath("$.birthDate", is("2003-04-16")));
    }

    @Test
    void testUpdatePersonNotFound() throws Exception {
        PersonDTO personDTO = new PersonDTO(
                "John Doe",
                LocalDate.of(2002, Month.MARCH, 30),
                List.of(
                        new AddressDTO("Rua 1", 123456, 123, "Cidade 1", true),
                        new AddressDTO("Rua 2", 654321, 321, "Cidade 2", false)
                )
        );

        mockMvc.perform(put("/api/v2/person/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personDTO)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Person not found with ID 999")));
    }

    @Test
    void testDeletePerson() throws Exception {
        PersonDTO personDTO = new PersonDTO(
                "John Doe",
                LocalDate.of(2002, Month.MARCH, 30),
                List.of(
                        new AddressDTO("Rua 1", 123456, 123, "Cidade 1", true),
                        new AddressDTO("Rua 2", 654321, 321, "Cidade 2", false)
                )
        );

        mockMvc.perform(post("/api/v2/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personDTO)))
                .andExpect(status().isCreated());

        mockMvc.perform(delete("/api/v2/person/1"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v2/person/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Person not found with ID 1")));
    }
}
