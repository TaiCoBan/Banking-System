package me.project.bankingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    private Long id;
    private String senderName;
    private String receiverName;
    private Long senderAccountId;
    private Long receiverAccountId;
    private long amount;
    private String content;
    private LocalDateTime timestamp;
}
