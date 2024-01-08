package com.example.fixify.repository;

import com.example.fixify.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByPhone(String phone);

    Customer findByFirstNameAndLastName(String firstName, String lastName);
}