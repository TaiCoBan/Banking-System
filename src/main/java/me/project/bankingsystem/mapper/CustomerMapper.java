package me.project.bankingsystem.mapper;

import me.project.bankingsystem.dto.CustomerDto;
import me.project.bankingsystem.entity.Account;
import me.project.bankingsystem.entity.Customer;
import me.project.bankingsystem.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerMapper {

    @Autowired
    private CustomerRepo repo;

    public CustomerDto toDto(Customer customer) {
        List<Long> accountId = new ArrayList<>();
        for (Account account : customer.getAccount_id()) {
            accountId.add(account.getId());
        }
        return new CustomerDto(customer.getFirstName(), customer.getLastName(), customer.getBirth(), customer.getPhoneNumber(), accountId);
    }
}
