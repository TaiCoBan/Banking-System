package me.project.bankingsystem.service.impl;

import me.project.bankingsystem.entity.Account;
import me.project.bankingsystem.exception.NotFoundException;
import me.project.bankingsystem.repository.AccountRepo;
import me.project.bankingsystem.service.AccountService;
import me.project.bankingsystem.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("account_service_impl")
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepo repo;


    @Override
    public Account save(Account account) {
        return repo.save(account);
    }

    @Override
    public Optional<Account> findById(Long id) {
        Optional<Account> account = repo.findById(id);

        if (account.isEmpty()) {
            throw new NotFoundException("Account Not Found");
        }
        return account;
    }

    @Override
    public Account update(Long id, Account account) {
        Optional<Account> exist = repo.findById(id);

        if (exist.isEmpty()) {
            throw new NotFoundException("Account Not Found");
        }

        Account newAccount = exist.get();
        newAccount.setAccountNumber(account.getAccountNumber());

        return repo.save(newAccount);
    }

    @Override
    public void delete(Long id) {
        Optional<Account> account = repo.findById(id);

        if (account.isEmpty()) {
            throw new NotFoundException("Account Not Found");
        } else {
            if (account.get().getBalance() == 0) {
                repo.deleteById(id);
            }
        }
    }
}
