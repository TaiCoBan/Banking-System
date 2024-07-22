package me.project.bankingsystem.mapper;

import me.project.bankingsystem.dto.AccountDto;
import me.project.bankingsystem.entity.Account;
import me.project.bankingsystem.repository.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    @Autowired
    private AccountRepo repo;
    public AccountDto toDto(Account account) {
        return new AccountDto(account.getAccountNumber(), account.getBalance());
    }

    public Account toAccount(AccountDto accountDto) {
        return repo.findByAccountNumber(accountDto.getAccountNumber());
    }
}
