package com.personapiv2.demo.controllers.address;

import com.personapiv2.demo.domain.address.Address;
import com.personapiv2.demo.domain.person.Person;
import com.personapiv2.demo.dto.address.AddressDTO;
import com.personapiv2.demo.exception.AddressNotFoundException;
import com.personapiv2.demo.exception.PersonNotFoundException;
import com.personapiv2.demo.services.address.AddressService;
import com.personapiv2.demo.services.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/address")
public class AddressController {
    @Autowired
    private PersonService personService;
    @Autowired
    private AddressService addressService;

    @GetMapping("/person/{personId}")
    public List<Address> readPersonAddresses(@PathVariable Long personId) throws PersonNotFoundException {
        Person person = personService.findById(personId);
        return person.getAddresses();
    }

    @PutMapping("/{id}")
    public Address update(@PathVariable Long id, @RequestBody AddressDTO address) throws AddressNotFoundException {
        return addressService.update(id, address);
    }

    @PostMapping("/person/{personId}")
    public Person createPersonAddress(@PathVariable Long personId, @RequestBody AddressDTO address) throws PersonNotFoundException {
        return addressService.createPersonAddress(address, personId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        addressService.delete(id);
    }
}
