package me.project.bankingsystem.mapper;

import me.project.bankingsystem.dto.TransactionDto;
import me.project.bankingsystem.entity.Transaction;
import me.project.bankingsystem.repository.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    @Autowired
    private TransactionRepo repo;

    public TransactionDto toDto(Transaction transaction) {

        return null;
    }
}
