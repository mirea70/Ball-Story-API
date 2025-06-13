package com.ball_story.home.account.repository;

import com.ball_story.home.account.entity.Account;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountRepository {
    void join(Account account);
}
