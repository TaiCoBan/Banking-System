package me.project.bankingsystem.service.impl;

import me.project.bankingsystem.entity.Customer;
import me.project.bankingsystem.exception.NotFoundException;
import me.project.bankingsystem.repository.CustomerRepo;
import me.project.bankingsystem.service.CustomerService;
import me.project.bankingsystem.service.util.CustomerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
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
    public Customer get() {
        return CustomerUtil.getCurrentCustomer();
    }

    @Override
    public Customer update(Long cusId, Customer customer) {
        Customer current = CustomerUtil.getCurrentCustomer();

        if (current.getId() == cusId || current.getRoles().contains("ADMIN")) {

            Optional<Customer> exist = repo.findById(cusId);

            if (exist.isPresent()) {
                Customer newCus = exist.get();

                newCus.setFirstName(customer.getFirstName());
                newCus.setLastName(customer.getLastName());
                newCus.setBirth(customer.getBirth());
                newCus.setPhoneNumber(customer.getPhoneNumber());
                newCus.setPassword(passwordEncoder.encode(customer.getPassword()));

                return repo.save(newCus);
            }

        }

        throw new NotFoundException("Customer Not Found");
    }

    @Override
    public void delete(Long id) {
        if (repo.findById(id).isEmpty()) {
            throw new NotFoundException("Customer Not Found");
        }
        repo.deleteById(id);
    }
}
