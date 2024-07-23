package me.project.bankingsystem.service;

import me.project.bankingsystem.entity.Transaction;

import java.util.List;
import java.util.Map;

public interface TransactionService {
    Transaction deposit(Long accId, Long amount, String content);
    List<Transaction> getAll(Long cusId);

    Transaction withdraw(Long money, Long accId, Long atmId);
}
