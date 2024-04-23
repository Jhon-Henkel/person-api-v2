package com.personapiv2.demo.dto.address;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public record AddressDTO(
        @JsonProperty(required = true) String publicPlace,
        @JsonProperty(required = true) Integer zipCode,
        @JsonProperty(required = true) Integer number,
        @JsonProperty(required = true) String city,
        @JsonProperty(required = true) Boolean mainAddress
) {
    public AddressDTO {
        Objects.requireNonNull(publicPlace, "publicPlace must not be null");
        Objects.requireNonNull(zipCode, "zipCode must not be null");
        Objects.requireNonNull(number, "number must not be null");
        Objects.requireNonNull(city, "city must not be null");
        Objects.requireNonNull(mainAddress, "mainAddress must not be null");
    }
}
