package me.project.bankingsystem.service.util;

import me.project.bankingsystem.entity.Customer;
import me.project.bankingsystem.exception.NotFoundException;
import me.project.bankingsystem.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomerUtil {

    private static CustomerRepo customerRepo;

    @Autowired
    public CustomerUtil(CustomerRepo repo) {
        CustomerUtil.customerRepo = repo;
    }

    public static Customer getCurrentCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                Optional<Customer> customer = customerRepo.findByUsername(((UserDetails) principal).getUsername());
                return customer.orElseThrow(() -> new NotFoundException("Customer not found with the given username"));
            }
        }
        throw new NotFoundException("Authentication error: Customer not found");
    }
}
