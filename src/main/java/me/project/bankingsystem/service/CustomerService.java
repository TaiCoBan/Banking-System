package me.project.bankingsystem.service;

import me.project.bankingsystem.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Customer save(Customer customer);
    List<Customer>  findAll();
    Optional<Customer> findById(Long id);
    Customer update(Long id, Customer customer);
    void delete(Long id);
}
