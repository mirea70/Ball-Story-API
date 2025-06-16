package com.ball_story;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ball_story")
public class BallStoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(BallStoryApplication.class, args);
	}

}
