package me.project.bankingsystem.controller;

import me.project.bankingsystem.entity.Customer;
import me.project.bankingsystem.entity.Response;
import me.project.bankingsystem.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers/")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @PostMapping("add")
    public ResponseEntity<Response> save(@RequestBody Customer customer) {
        service.save(customer);
        return ResponseEntity.ok().body(new Response(HttpStatus.OK, "Add Customer Successfully"));
    }

    @GetMapping("get")
    public ResponseEntity<?> findById() {
        return ResponseEntity.ok().body(service.get());
    }

    @GetMapping("get-all")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @PutMapping("update/{cusId}")
    public ResponseEntity<Response> update(@PathVariable Long cusId, @RequestBody Customer customer) {
        service.update(cusId, customer);
        return ResponseEntity.ok().body(new Response(HttpStatus.OK, "Update Customer Successfully"));
    }

    @DeleteMapping("delete/{cusId}")
    public ResponseEntity<Response> delete(@PathVariable Long cusId) {
        service.delete(cusId);
        return ResponseEntity.ok().body(new Response(HttpStatus.OK, "Delete Customer Successfully"));
    }
}
