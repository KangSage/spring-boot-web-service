package com.web.board.book.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// Test code에 영향을 주지 않도록 @EnableJpaAuditing의 범위를 축소 시키는 클래스
@Configuration
@EnableJpaAuditing
public class JpaConfig {}
