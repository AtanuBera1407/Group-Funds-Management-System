package com.example.group_funds.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    @Column(nullable=false)
    private Double totalPrincipal = 0.0;

    @Column(nullable=false)
    private Double totalProfit = 0.0;

    @PrePersist
    public void prePersist() {
        if (totalPrincipal == null) totalPrincipal = 0.0;
        if (totalProfit == null) totalProfit = 0.0;
    }
}
