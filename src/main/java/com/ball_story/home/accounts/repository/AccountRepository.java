package com.ball_story.home.accounts.repository;

import com.ball_story.home.accounts.entity.Account;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountRepository {
    void join(Account account);
}
