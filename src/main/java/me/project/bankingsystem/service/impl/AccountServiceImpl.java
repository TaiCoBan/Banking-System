package me.project.bankingsystem.service.impl;

import me.project.bankingsystem.dto.AccountDto;
import me.project.bankingsystem.entity.Account;
import me.project.bankingsystem.entity.Customer;
import me.project.bankingsystem.exception.NotFoundException;
import me.project.bankingsystem.mapper.AccountMapper;
import me.project.bankingsystem.repository.AccountRepo;
import me.project.bankingsystem.service.AccountService;
import me.project.bankingsystem.service.util.CustomerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepo repo;

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public Account save(Account account) {
        Customer currentCustomer = CustomerUtil.getCurrentCustomer();
        account.setCustomer(currentCustomer);

        if (account.getAccountNumber() == null || account.getAccountNumber().isEmpty()) {
            throw new IllegalArgumentException("Account number cannot be null or empty");
        }

        return repo.save(account);
    }

    // find account of current customer
    @Override
    public AccountDto findById(Long accId) {
        Optional<Account> account = repo.findById(accId);

        if (account.isPresent() && CustomerUtil.getCurrentCustomer().getAccount_id().contains(account.get())) {
            return accountMapper.toDto(account.get());
        }

        throw new NotFoundException("Not Authorized");
    }

    // find all account of current customer
    @Override
    public List<AccountDto> findAll(Long cusId) {
        if (CustomerUtil.getCurrentCustomer().getId() == cusId) {
            List<AccountDto> list = repo.findAll().stream().map(account -> accountMapper.toDto(account))
                    .collect(Collectors.toList());

            List<AccountDto> r = new ArrayList<>();
            for (AccountDto dto : list) {
                if (accountMapper.toAccount(dto).getCustomer().getId() == cusId) {
                    r.add(dto);
                }
            }

            return r;
        }

        throw new NotFoundException("Not Authorized");
    }

    // update account of current customer
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

    // delete account of current customer
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
