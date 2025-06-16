package com.ball_story.home.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class AccountJoinRequest {
    private String name;
    private String phone;
    private Long profileImgId;
}
