package com.personapiv2.demo.dto.person;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.personapiv2.demo.dto.address.AddressDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public record PersonDTO(
        @JsonProperty(required = true) String fullName,
        @JsonProperty(required = true) LocalDate birthDate,
        @JsonProperty(required = true) List<AddressDTO> addresses
) {
    public PersonDTO {
        Objects.requireNonNull(fullName, "fullName must not be null");
        Objects.requireNonNull(birthDate, "birthDate must not be null");
        Objects.requireNonNull(addresses, "addresses must not be null");
    }
}
