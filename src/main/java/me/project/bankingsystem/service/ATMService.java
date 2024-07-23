package me.project.bankingsystem.service;

import me.project.bankingsystem.entity.ATM;
import me.project.bankingsystem.entity.Transaction;
import org.springframework.http.ResponseEntity;

public interface ATMService {
    ATM install(ATM atm);
    String unistall(Long id);

    String cashToATM(Long cash, Long atmId);

    Transaction withdrawATM(Long accId, Long atmId, Long amount);
}
