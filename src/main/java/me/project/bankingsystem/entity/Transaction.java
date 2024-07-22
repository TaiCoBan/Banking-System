package me.project.bankingsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sender_id", referencedColumnName = "id", nullable = false, unique = true)
    private Customer sender;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "receiver_id", referencedColumnName = "id", nullable = false, unique = true)
    private Customer receiver;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sender_account_id", referencedColumnName = "id", nullable = false, unique = true)
    private Account senderAccount;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "receiver_account_id", referencedColumnName = "id", nullable = false, unique = true)
    private Account receiverAccount;
}
