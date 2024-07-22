package me.project.bankingsystem.service.impl;

import me.project.bankingsystem.dto.TransactionDto;
import me.project.bankingsystem.entity.Account;
import me.project.bankingsystem.entity.Customer;
import me.project.bankingsystem.entity.Transaction;
import me.project.bankingsystem.exception.InvalidParamException;
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
    public TransactionDto deposit(Long fromCusId, Long toCusId, Long toAccId, Long amount, String content) {
        Customer currentCus = CustomerUtil.getCurrentCustomer();

        if (currentCus.getId() != fromCusId) {
            throw new UnauthorizedException("Customer Unauthorized id = " + fromCusId);
        } else if (amount <= 0) {
            throw new InvalidParamException("Amount must be greater than 0");
        }

        Account account = accountRepo.findById(toAccId).orElseThrow(() -> new NotFoundException("Account Not Found"));

        // Cong tien vao TK KH
        account.setBalance(account.getBalance() + amount);
        accountRepo.save(account);

        // Cong tien vao TK NH
        Account bank = accountRepo.findByAccountNumber("bank");
        bank.setBalance(bank.getBalance() + amount);
        accountRepo.save(bank);

        // Log lich su giao dich
        Customer sender = currentCus;
        Customer receiver = customerRepo.findById(toCusId).get();
        Account senderAcc = null;
        Account receiverAcc = account;
        // amount
        // content
        return logTransaction(sender, receiver, senderAcc, receiverAcc, amount, content);
    }


    private TransactionDto logTransaction(Customer sender,
                                          Customer receiver,
                                          Account senderAcc,
                                          Account receiverAcc,
                                          long amount,
                                          String content
                                          ) {
        Transaction transaction = new Transaction();

        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setSenderAccount(senderAcc);
        transaction.setReceiverAccount(receiverAcc);
        transaction.setAmount(amount);
        transaction.setContent(content);
        transaction.setTimestamp(LocalDateTime.now());

        transactionRepo.save(transaction);
        return transactionMapper.toDto(transaction);
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
