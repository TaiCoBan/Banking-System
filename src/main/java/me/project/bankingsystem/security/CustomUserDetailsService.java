package me.project.bankingsystem.security;

import me.project.bankingsystem.entity.Customer;
import me.project.bankingsystem.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerRepo repo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> customer = repo.findByUsername(username);

        return customer.map(customer1 -> new CustomUserDetails(customer1))
                .orElseThrow(() -> new UsernameNotFoundException("Customer Not Found"));
    }
}
