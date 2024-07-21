package me.project.bankingsystem.service.impl;

import me.project.bankingsystem.entity.Account;
import me.project.bankingsystem.entity.Customer;
import me.project.bankingsystem.exception.NotFoundException;
import me.project.bankingsystem.repository.AccountRepo;
import me.project.bankingsystem.service.AccountService;
import me.project.bankingsystem.service.util.CustomerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepo repo;

    @Override
    public Account save(Account account) {
        Customer currentCustomer = CustomerUtil.getCurrentCustomer();
        account.setCustomer(currentCustomer);

        if (account.getAccountNumber() == null || account.getAccountNumber().isEmpty()) {
            throw new IllegalArgumentException("Account number cannot be null or empty");
        }

        return repo.save(account);
    }

    // ERROR
    @Override
    public Account findById(Long accId) {
        Optional<Account> account = repo.findById(accId);

        if (account.isPresent() && CustomerUtil.getCurrentCustomer().getAccount_id().contains(account.get())) {
            return account.get();
        }

        throw new NotFoundException("Account Not Found");
    }

    // ERROR
    @Override
    public List<Account> findAll(Long cusId) {
        if (CustomerUtil.getCurrentCustomer().getId() == cusId) {
            return repo.findAll();
        }

        throw new NotFoundException("Account Not Found");
    }

    @Override
    public Account update(Long accId, Account account) {
        Optional<Account> currentAccount = repo.findById(accId);

        if (currentAccount.isEmpty()) {
            throw new NotFoundException("Account not found");
        }
        if (!currentAccount.get().getCustomer().equals(CustomerUtil.getCurrentCustomer())) {
            throw new NotFoundException("Account not found for the current customer");
        }

        Account newAcc = currentAccount.get();
        newAcc.setAccountNumber(account.getAccountNumber());

        return repo.save(newAcc);
    }

    @Override
    public void delete(Long accId) {
        Optional<Account> account = repo.findById(accId);

        if (account.isEmpty() || !account.get().getCustomer().equals(CustomerUtil.getCurrentCustomer())) {
            throw new NotFoundException("Account not found for the current customer");
        }

        if (account.get().getBalance() == 0) {
            repo.deleteById(accId);
        } else {
            throw new IllegalArgumentException("Account balance must be zero to delete");
        }
    }
}
