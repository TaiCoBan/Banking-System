package me.project.bankingsystem.service;

import me.project.bankingsystem.dto.TransactionDto;
import me.project.bankingsystem.entity.Transaction;

import java.util.List;

public interface TransactionService {
    TransactionDto deposit(Long fromCusId, Long toCusId, Long accId, Long amount, String content);
    List<Transaction> getAll(Long cusId);
}
