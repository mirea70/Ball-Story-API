package com.ball_story.home.accounts.service;

import com.ball_story.home.accounts.dto.AccountJoinRequest;
import com.ball_story.home.accounts.entity.Account;
import com.ball_story.home.accounts.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    @Transactional
    public void join(AccountJoinRequest request) {
        accountRepository.join(
                Account.of(request)
        );
    }
}
