package me.project.bankingsystem.service.impl;

import me.project.bankingsystem.entity.ATM;
import me.project.bankingsystem.entity.Account;
import me.project.bankingsystem.entity.Transaction;
import me.project.bankingsystem.exception.InvalidParamException;
import me.project.bankingsystem.exception.NotFoundException;
import me.project.bankingsystem.repository.ATMRepo;
import me.project.bankingsystem.repository.AccountRepo;
import me.project.bankingsystem.repository.TransactionRepo;
import me.project.bankingsystem.service.ATMService;
import me.project.bankingsystem.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ATMServiceImpl implements ATMService {

    @Autowired
    private ATMRepo repo;

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private TransactionRepo transactionRepo;

    @Override
    public ATM install(ATM atm) {
        atm.setInstalledDate(LocalDateTime.now());
        return repo.save(atm);
    }

    @Override
    public String unistall(Long id) {
        if (repo.findById(id).isEmpty()) {
            throw new NotFoundException("ATM Not Found id = " + id);
        }
        if (repo.findById(id).get().getCashAmount() == 0) {
            repo.deleteById(id);
            return "Uninstall ATM Successfully id = " + id;
        }
        return null;
    }

    @Override
    public String cashToATM(Long cash, Long atmId) {
        ATM atm = repo.findById(atmId).orElseThrow(() -> new NotFoundException("ATM Not Found id = " + atmId));

        Account bank = accountRepo.findByAccountNumber("bank");

        if (bank.getBalance() >= cash) {

            atm.setCashAmount(atm.getCashAmount() + cash);
            repo.save(atm);
            bank.setBalance(bank.getBalance() - cash);
            accountRepo.save(bank);

            return "Cash to ATM Successfully id = " + atmId;

        }

        throw new InvalidParamException("Invalid Cash");
    }

    @Override
    public Transaction withdraw(Long accId, Long atmId, Long amount) {
        Account account = accountRepo.findById(accId).orElseThrow(() -> new NotFoundException("Account Not Found id = " + accId));
        ATM atm = repo.findById(atmId).orElseThrow(() -> new NotFoundException("ATM Not Found id = " + atmId));

        if (account.getBalance() >= amount && atm.getCashAmount() >= amount) {
            account.setBalance(account.getBalance() - amount);
            accountRepo.save(account);

            atm.setCashAmount(atm.getCashAmount() - amount);
            repo.save(atm);

            Transaction transaction = new Transaction();
            transaction.setAccountId(accId);
            transaction.setTransactionType("withdraw");
            transaction.setAmount(amount);
            transaction.setContent("withdraw from atm");
            transaction.setTimestamp(LocalDateTime.now());
            transaction.setStatus("-" + amount);

            return transactionRepo.save(transaction);
        }

        throw new InvalidParamException("Invalid Amount");
    }

}
