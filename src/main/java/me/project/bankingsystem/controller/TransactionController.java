package me.project.bankingsystem.controller;

import me.project.bankingsystem.dto.DepositRequest;
import me.project.bankingsystem.entity.Response;
import me.project.bankingsystem.entity.Transaction;
import me.project.bankingsystem.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/transactions/")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @PostMapping("deposit/{accId}")
    public ResponseEntity<?> deposit(@PathVariable Long accId,
                                     @RequestBody DepositRequest depositRequest) {
        Long amount = depositRequest.getAmount();
        String content = depositRequest.getContent();
        return ResponseEntity.ok().body(service.deposit(accId, amount, content));
    }

    @PostMapping("withdraw/{accId}/{atmId}")
    public ResponseEntity<?> withdraw(@RequestBody Map<String, Long> amount,
                                @PathVariable Long accId,
                                @PathVariable Long atmId) {
        Long money = amount.get("amount");
        return ResponseEntity.ok().body(service.withdraw(money, accId, atmId));
    }
}
