package com.personapiv2.demo.services.address;

import com.personapiv2.demo.domain.address.Address;
import com.personapiv2.demo.domain.person.Person;
import com.personapiv2.demo.dto.address.AddressDTO;
import com.personapiv2.demo.exception.AddressNotFoundException;
import com.personapiv2.demo.exception.PersonNotFoundException;
import com.personapiv2.demo.repositories.AddressRepository;
import com.personapiv2.demo.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PersonRepository personRepository;

    public Address update(Long id, AddressDTO address) throws AddressNotFoundException {
        Address addressToUpdate = findById(id);
        addressToUpdate.setNumber(address.number());
        addressToUpdate.setCity(address.city());
        addressToUpdate.setZipCode(address.zipCode());
        addressToUpdate.setPublicPlace(address.publicPlace());
        addressToUpdate.setMainAddress(address.mainAddress());
        return addressRepository.save(addressToUpdate);
    }

    public Address findById(Long id) throws AddressNotFoundException {
        return addressRepository.findById(id).orElseThrow(() -> new AddressNotFoundException(id));
    }

    public Person createPersonAddress(AddressDTO address, Long personId) throws PersonNotFoundException {
        Person person = personRepository.findById(personId).orElseThrow(() -> new PersonNotFoundException(personId));
        List<Address> addresses = person.getAddresses();
        addresses.add(new Address(address));
        List<AddressDTO> addressesDto = AddressDTO.fromDomain(addresses);
        person.setAddresses(addressesDto);
        personRepository.save(person);
        return person;
    }

    public void delete(Long id) {
        addressRepository.deleteById(id);
    }
}
