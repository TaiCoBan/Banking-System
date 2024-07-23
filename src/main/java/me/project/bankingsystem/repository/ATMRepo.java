package me.project.bankingsystem.repository;

import me.project.bankingsystem.entity.ATM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ATMRepo extends JpaRepository<ATM, Long> {
}
