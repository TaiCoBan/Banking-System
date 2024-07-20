package me.project.bankingsystem.service.impl;

import me.project.bankingsystem.entity.Customer;
import me.project.bankingsystem.exception.NotFoundException;
import me.project.bankingsystem.repository.CustomerRepo;
import me.project.bankingsystem.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("customer_service_impl")
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepo repo;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public Customer save(Customer customer) {
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        return repo.save(customer);
    }

    @Override
    public List<Customer> findAll() {
        return repo.findAll();
    }

    @Override
    public Optional<Customer> findById(Long id) {
        Optional<Customer> customer = repo.findById(id);
        if (customer.isEmpty()) {
            throw new NotFoundException("Customer Not Found");
        }
        return customer;
    }

    @Override
    public Customer update(Long id, Customer customer) {
        Optional<Customer> exist = repo.findById(id);
        if (exist.isEmpty()) {
            throw new NotFoundException("Customer Not Found");
        }

        Customer update = exist.get();
        update.setFirstName(customer.getFirstName());
        update.setLastName(customer.getLastName());
        update.setBirth(customer.getBirth());
        update.setAccount_id(customer.getAccount_id());
        update.setPassword(customer.getPassword());

        return repo.save(update);
    }

    @Override
    public void delete(Long id) {
        if (findById(id).isEmpty()) {
            throw new NotFoundException("Customer Not Found");
        }
        repo.deleteById(id);
    }
}
