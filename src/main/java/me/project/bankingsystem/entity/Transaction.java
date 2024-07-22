package me.project.bankingsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sender_id", referencedColumnName = "id", nullable = false)
    private Customer sender;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "receiver_id", referencedColumnName = "id", nullable = false)
    private Customer receiver;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sender_account_id", referencedColumnName = "id", nullable = true)
    private Account senderAccount;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "receiver_account_id", referencedColumnName = "id", nullable = false)
    private Account receiverAccount;

    @Column(name = "amount", nullable = false)
    private long amount;

    @Column(name = "content")
    private String content;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;
}
