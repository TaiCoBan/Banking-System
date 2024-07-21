package me.project.bankingsystem.service.impl;

import me.project.bankingsystem.entity.Account;
import me.project.bankingsystem.entity.Customer;
import me.project.bankingsystem.exception.NotFoundException;
import me.project.bankingsystem.repository.AccountRepo;
import me.project.bankingsystem.service.AccountService;
import me.project.bankingsystem.service.CustomerService;
import me.project.bankingsystem.service.util.CustomerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("account_service_impl")
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepo repo;

    @Autowired
    private CustomerUtil customerUtil;

    private final Customer currentCustomer = customerUtil.getCurrentCustomer();

    @Override
    public Account save(Account account) {
        account.setCustomer(currentCustomer);
        return repo.save(account);
    }

    @Override
    public Optional<Account> findById(Long id) {
        Optional<Account> account = repo.findById(id);

        if (account.get().getCustomer().equals(currentCustomer)) {
            return account;
        }

        throw new NotFoundException("Customer Not Found");
    }

    @Override
    public List<Account> findAll(Long id) {
        if (repo.findById(id).get().equals(currentCustomer)) {
            return repo.findAll();
        }
        throw new NotFoundException("Customer Not Found");
    }

    @Override
    public Account update(Long id, Account account) {
        Optional<Account> currentAccount = repo.findById(id);

        if (currentAccount.isEmpty()) {
            throw new NotFoundException("Account Not Found");
        }
        if (!currentAccount.get().getCustomer().equals(currentCustomer)) {
            throw new NotFoundException("Customer Not Found");
        }

        Account newAcc = currentAccount.get();
        newAcc.setAccountNumber(account.getAccountNumber());

        return repo.save(account);
    }

    @Override
    public void delete(Long id) {
        Optional<Account> account = repo.findById(id);

        if (!account.get().getCustomer().equals(currentCustomer)) {
            throw new NotFoundException("Customer Not Found");
        }
        if (account.isEmpty()) {
            throw new NotFoundException("Account Not Found");
        } else {
            if (account.get().getBalance() == 0) {
                repo.deleteById(id);
            }
        }
    }

}
