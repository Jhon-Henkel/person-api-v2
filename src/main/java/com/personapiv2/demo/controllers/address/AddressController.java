package com.personapiv2.demo.controllers.address;

import com.personapiv2.demo.services.address.AddressService;
import com.personapiv2.demo.services.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/address")
public class AddressController {
    @Autowired
    private PersonService personService;
    @Autowired
    private AddressService addressService;
}
