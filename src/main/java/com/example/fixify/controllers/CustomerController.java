package com.example.fixify.controllers;

import com.example.fixify.models.Customer;
import com.example.fixify.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @PostMapping
    public Customer addCliente(@RequestBody Customer customer) {
        System.out.println("customer = " + customer);
        return customerService.saveCustomer(customer);
    }
}
