package com.ball_story.home.account.entity;

import com.ball_story.home.account.dto.AccountJoinRequest;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
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
