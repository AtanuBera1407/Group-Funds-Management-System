package com.example.group_funds.repository;

import com.example.group_funds.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<BankAccount, Long> {
}
