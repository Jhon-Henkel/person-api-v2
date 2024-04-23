package com.personapiv2.demo.dto.person;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.Objects;

public record PersonDTO(
        @JsonProperty(required = true) String fullName,
        @JsonProperty(required = true) LocalDate birthDate
) {
    public PersonDTO {
        Objects.requireNonNull(fullName, "fullName must not be null");
        Objects.requireNonNull(birthDate, "birthDate must not be null");
    }
}
