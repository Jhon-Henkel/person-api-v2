package com.personapiv2.demo.domain.person;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.personapiv2.demo.dto.person.PersonDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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

    public Person(PersonDTO person) {
        this.fullName = person.fullName();
        this.birthDate = person.birthDate();
    }
}
