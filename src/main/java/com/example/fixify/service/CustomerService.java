package com.example.fixify.service;



import com.example.fixify.models.Customer;
import com.example.fixify.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    public List<Customer> findAllCustomer(){
        return customerRepository.findAll();
    }

    public Customer saveCustomer(Customer customer) {
        // Aquí podrías agregar lógica antes de guardar el ticket
        return customerRepository.save(customer);
    }

    public Customer findByFirstNameAndLastName(String firstName, String lastName) {
        return customerRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    public Customer findByPhone(String phone) {
        // Buscar en el repositorio por teléfono
        return customerRepository.findByPhone(phone).orElse(null);
    }

    public Customer getOrCreateCustomer(Customer customer) {
        Customer existingCustomer = findByPhone(customer.getPhone());
        if (existingCustomer != null) {
            return existingCustomer;
        } else {
            return saveCustomer(customer);
        }
    }

}
