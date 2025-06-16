package com.ball_story.home.accounts.entity;

import com.ball_story.home.accounts.dto.AccountJoinRequest;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Account {
    private Long accountId;
    private String name;
    private String phone;
    private Long point;
    private Long profileImgId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public static Account of(AccountJoinRequest request) {
        Account account = new Account();

        account.name = request.getName();
        account.phone = request.getPhone();
        account.point = 0L;
        account.profileImgId = request.getProfileImgId();
        account.createdAt = LocalDateTime.now();
        account.updatedAt = LocalDateTime.now();
        return account;
    }
}
