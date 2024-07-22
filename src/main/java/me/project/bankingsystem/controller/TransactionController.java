package me.project.bankingsystem.controller;

import me.project.bankingsystem.dto.DepositRequest;
import me.project.bankingsystem.entity.Response;
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

    @PostMapping("deposit/{fromCusId}/{toCusId}/{toAccId}")
    public ResponseEntity<?> deposit(@PathVariable Long fromCusId,
                                     @PathVariable Long toCusId,
                                     @PathVariable Long toAccId,
                                     @RequestBody DepositRequest depositRequest) {
        Long amount = depositRequest.getAmount();
        String content = depositRequest.getContent();
        return ResponseEntity.ok().body(service.deposit(fromCusId, toCusId, toAccId, amount, content));
    }

}
