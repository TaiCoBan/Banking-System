package me.project.bankingsystem.service.impl;

import me.project.bankingsystem.entity.Account;
import me.project.bankingsystem.entity.Customer;
import me.project.bankingsystem.entity.Transaction;
import me.project.bankingsystem.exception.NotFoundException;
import me.project.bankingsystem.exception.UnauthorizedException;
import me.project.bankingsystem.mapper.TransactionMapper;
import me.project.bankingsystem.repository.AccountRepo;
import me.project.bankingsystem.repository.CustomerRepo;
import me.project.bankingsystem.repository.TransactionRepo;
import me.project.bankingsystem.service.TransactionService;
import me.project.bankingsystem.service.util.CustomerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepo repo;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private TransactionRepo transactionRepo;

    @Override
    public Transaction deposit(Long accId, Long amount, String content) {

        Account account = accountRepo.findById(accId).orElseThrow(() -> new NotFoundException("Account Not Found"));
        account.setBalance(account.getBalance() + amount);
        accountRepo.save(account);

        Account bank = accountRepo.findByAccountNumber("bank");
        bank.setBalance(bank.getBalance() + amount);
        accountRepo.save(bank);
        LocalDateTime timestamp = LocalDateTime.now();
        logTransaction(bank.getId(), amount, "", "deposit", timestamp, "+" + amount);

        return logTransaction(accId, amount, content, "deposit", timestamp, "+" + amount);
    }


    private Transaction logTransaction(Long accId, long amount, String content, String type, LocalDateTime timstamp, String status) {
        Transaction transaction = new Transaction();

        transaction.setAccountId(accId);
        transaction.setTransactionType(type);
        transaction.setAmount(amount);
        transaction.setContent(content);
        transaction.setTimestamp(timstamp);
        transaction.setStatus(status);

        return transactionRepo.save(transaction);
    }


    @Override
    public List<Transaction> getAll(Long cusId) {
        Customer currentCustomer = CustomerUtil.getCurrentCustomer();

        if (customerRepo.findById(cusId).get().equals(currentCustomer)) {
            return repo.findAll();
        }
        throw new UnauthorizedException("Customer Unauthorized id = " + cusId);
    }


}
