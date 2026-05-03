package com.example.group_funds.service;

import com.example.group_funds.entity.Installment;
import com.example.group_funds.entity.User;
import com.example.group_funds.repository.InstallmentRepository;
import com.example.group_funds.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class InstallmentService {
    private final InstallmentRepository installmentRepository;
    private final UserRepository userRepository;

    public InstallmentService(InstallmentRepository installmentRepository,
                              UserRepository userRepository) {
        this.installmentRepository = installmentRepository;
        this.userRepository = userRepository;
    }

    public void generateMonthlyInstallments() {

        LocalDate now = LocalDate.now();

        List<User> users = userRepository.findAll();

        for (User user : users) {

            Installment installment = new Installment();

            installment.setAmount(1000);
            installment.setMonth(now.getMonthValue());
            installment.setYear(now.getYear());
            installment.setStatus("PENDING");
            installment.setUser(user);

            installmentRepository.save(installment);
        }
    }

    public Installment payInstallment(Long installmentId) {

        Installment installment = installmentRepository.findById(installmentId)
                .orElseThrow();

        installment.setStatus("PAID");
        installment.setPaidDate(LocalDate.now());

        User user = installment.getUser();

        user.setTotalPrincipal(user.getTotalPrincipal() + installment.getAmount());

        userRepository.save(user);

        return installmentRepository.save(installment);
    }
}
