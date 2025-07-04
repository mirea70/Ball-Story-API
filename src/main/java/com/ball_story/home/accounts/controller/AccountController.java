package com.ball_story.home.accounts.controller;

import com.ball_story.home.accounts.dto.AccountJoinRequest;
import com.ball_story.home.accounts.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/v1/accounts")
    public ResponseEntity<?> join(@RequestBody AccountJoinRequest request) {
        accountService.join(request);
        return ResponseEntity.ok().build();
    }

}
