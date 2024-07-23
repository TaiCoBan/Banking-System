package me.project.bankingsystem.controller;

import me.project.bankingsystem.entity.ATM;
import me.project.bankingsystem.service.ATMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/ATMs/")
public class ATMController {

    @Autowired
    private ATMService service;

    @PostMapping("install")
    public ResponseEntity<?> install(@RequestBody ATM atm) {
        return ResponseEntity.ok().body(service.install(atm));
    }

    @PostMapping("cash-to-atm/{atmId}")
    public String send(@RequestBody Map<String, Long> req,
                       @PathVariable Long atmId) {

        return service.cashToATM(req.get("cash"), atmId);
    }

    @DeleteMapping("uninstall/{atmId}")
    public String uninstall(@PathVariable Long atmId) {
        return service.unistall(atmId);
    }

    @PostMapping("withdraw/{accId}/{atmId}")
    public ResponseEntity<?> withdraw(@PathVariable Long accId, @PathVariable Long atmId, @RequestBody Map<String, Long> req) {
        return ResponseEntity.ok().body(service.withdrawATM(accId, atmId, req.get("amount")));
    }
}
