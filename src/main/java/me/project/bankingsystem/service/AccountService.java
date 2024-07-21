package me.project.bankingsystem.service;

import me.project.bankingsystem.entity.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    Account save(Account account);
    Optional<Account> findById(Long id);
    List<Account> findAll(Long id);
    Account update(Long id, Account account);
    void delete(Long id);
}
