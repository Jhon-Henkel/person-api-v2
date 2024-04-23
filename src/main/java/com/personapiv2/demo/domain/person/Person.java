package com.personapiv2.demo.domain.person;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.personapiv2.demo.domain.address.Address;
import com.personapiv2.demo.dto.address.AddressDTO;
import com.personapiv2.demo.dto.person.PersonDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "person_id")
    @JsonManagedReference
    private List<Address> addresses = new ArrayList<>();

    public Person(PersonDTO person) {
        this.fullName = person.fullName();
        this.birthDate = person.birthDate();
        this.addresses = new ArrayList<>();
        for (AddressDTO address : person.addresses()) {
            this.addresses.add(new Address(address));
        }
    }

    public void setAddresses(List<AddressDTO> addresses) {
        this.addresses.clear();
        if (addresses != null) {
            for (AddressDTO address : addresses) {
                this.addresses.add(new Address(address));
            }
        }
    }
}
