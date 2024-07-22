package me.project.bankingsystem.service;

import me.project.bankingsystem.dto.CustomerDto;
import me.project.bankingsystem.entity.Customer;

import java.util.List;

public interface CustomerService {
    Customer save(Customer customer);
    List<CustomerDto> findAll();
    CustomerDto get();
    Customer update(Long id, Customer customer);
    void delete(Long id);
}
