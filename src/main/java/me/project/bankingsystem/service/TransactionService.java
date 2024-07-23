package me.project.bankingsystem.service;

import me.project.bankingsystem.entity.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction deposit(Long accId, Long amount, String content);
    List<Transaction> getAll(Long cusId);
}
