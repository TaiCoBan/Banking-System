package me.project.bankingsystem.controller;

import me.project.bankingsystem.entity.Account;
import me.project.bankingsystem.entity.Response;
import me.project.bankingsystem.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts/")
public class AccountController {

    @Autowired
    private AccountService service;

    @PostMapping("create")
    public ResponseEntity<Response> save(@RequestBody Account account) {
        service.save(account);
        return ResponseEntity.ok().body(new Response(HttpStatus.OK, "Create Account Succesfully"));
    }

    @GetMapping("get/{id}")
    public ResponseEntity<Account> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.findById(id).get());
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Response> update(@PathVariable Long id, @RequestBody Account account) {
        service.update(id, account);
        return ResponseEntity.ok().body(new Response(HttpStatus.OK, "Update Account Succesfully"));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Response> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().body(new Response(HttpStatus.OK, "Delete Account Succesfully"));
    }
}
