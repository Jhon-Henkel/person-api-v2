package com.personapiv2.demo.domain.address;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.personapiv2.demo.domain.person.Person;
import com.personapiv2.demo.dto.address.AddressDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String publicPlace;

    private Integer zipCode;

    private Integer number;

    private String city;

    private Boolean mainAddress;

    @ManyToOne
    @JoinColumn(name = "person_id", insertable = false, updatable = false)
    @JsonBackReference
    private Person person;

    public Address(AddressDTO address) {
        this.publicPlace = address.publicPlace();
        this.zipCode = address.zipCode();
        this.number = address.number();
        this.city = address.city();
        this.mainAddress = address.mainAddress();
    }
}
