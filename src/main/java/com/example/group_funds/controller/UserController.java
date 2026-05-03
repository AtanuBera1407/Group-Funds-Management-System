package com.example.group_funds.controller;

import com.example.group_funds.dto.UserSummaryDTO;
import com.example.group_funds.entity.User;
import com.example.group_funds.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public User createUser(@RequestBody User user) {
        if (user.getTotalPrincipal() == null)
            user.setTotalPrincipal(1000.0);

        if (user.getTotalProfit() == null)
            user.setTotalProfit(0.0);

        return userService.createUser(user);
    }

    @GetMapping
    public List<UserSummaryDTO> getUsers() {
        return userService.getAllUsersSummary();
    }
}
