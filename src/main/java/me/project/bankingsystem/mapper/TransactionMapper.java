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
        String senderName = (transaction.getSender() != null) ?
                transaction.getSender().getFirstName() + transaction.getSender().getLastName() : "Unknown Sender";
        String receiverName = (transaction.getReceiver() != null) ?
                transaction.getReceiver().getFirstName() + transaction.getReceiver().getLastName() : "Unknown Receiver";
        Long senderAccountId = (transaction.getSenderAccount() != null) ? transaction.getSenderAccount().getId() : null;
        Long receiverAccountId = (transaction.getReceiverAccount() != null) ? transaction.getReceiverAccount().getId() : null;

        return new TransactionDto(
                transaction.getId(),
                senderName,
                receiverName,
                senderAccountId,
                receiverAccountId,
                transaction.getAmount(),
                transaction.getContent(),
                transaction.getTimestamp()
        );
    }

}
