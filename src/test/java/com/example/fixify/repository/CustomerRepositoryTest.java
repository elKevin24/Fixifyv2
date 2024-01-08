package com.example.fixify.repository;

import com.example.fixify.models.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
 class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
     void testSaveCustomer() {
        // Crear un objeto Customer
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john.doe@example.com");
        customer.setPhone("1234567890");

        // Guardar el Customer en la base de datos
        customerRepository.save(customer);

        // Recuperar el Customer de la base de datos
        Customer savedCustomer = customerRepository.findByFirstNameAndLastName("John", "Doe");

        // Verificar que el Customer se haya guardado correctamente
        assertNotNull(savedCustomer);
        assertEquals("John", savedCustomer.getFirstName());
        assertEquals("Doe", savedCustomer.getLastName());
        assertEquals("john.doe@example.com", savedCustomer.getEmail());
        assertEquals("1234567890", savedCustomer.getPhone());
    }
}