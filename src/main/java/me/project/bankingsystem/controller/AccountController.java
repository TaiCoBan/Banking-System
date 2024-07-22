package me.project.bankingsystem.controller;

import me.project.bankingsystem.dto.AccountDto;
import me.project.bankingsystem.entity.Account;
import me.project.bankingsystem.entity.Response;
import me.project.bankingsystem.exception.NotFoundException;
import me.project.bankingsystem.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts/")
public class AccountController {

    @Autowired
    private AccountService service;

    @PostMapping("create")
    public ResponseEntity<Response> save(@RequestBody Account account) {
        service.save(account);
        return ResponseEntity.ok().body(new Response(HttpStatus.OK, "Create Account Successfully"));
    }

    @GetMapping("get/{accId}")
    public ResponseEntity<?> findById(@PathVariable Long accId) {
        try {
            return ResponseEntity.ok().body(service.findById(accId));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("get-all/{cusId}")
    public ResponseEntity<?> findAll(@PathVariable Long cusId) {
        return ResponseEntity.ok().body(service.findAll(cusId));
    }

    @PutMapping("update/{accId}")
    public ResponseEntity<Response> update(@PathVariable Long accId, @RequestBody Account account) {
        service.update(accId, account);
        return ResponseEntity.ok().body(new Response(HttpStatus.OK, "Update Account Succesfully"));
    }

    @DeleteMapping("delete/{accId}")
    public ResponseEntity<Response> delete(@PathVariable Long accId) {
        service.delete(accId);
        return ResponseEntity.ok().body(new Response(HttpStatus.OK, "Delete Account Succesfully"));
    }
}
