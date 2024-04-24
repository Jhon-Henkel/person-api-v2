package com.personapiv2.demo.dto.address;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.personapiv2.demo.domain.address.Address;

import java.util.ArrayList;
import java.util.List;
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

    public static List<AddressDTO> fromDomain(List<Address> addresses) {
        List<AddressDTO> newAddresses = new ArrayList<>();
        for (Address address : addresses) {
            newAddresses.add(new AddressDTO(
                    address.getPublicPlace(),
                    address.getZipCode(),
                    address.getNumber(),
                    address.getCity(),
                    address.getMainAddress()
            ));
        }
        return newAddresses;
    }
}
