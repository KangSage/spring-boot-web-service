package com.web.board.book.config.auth;

import com.web.board.book.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  private final CustomOAuth2UserService customOAuth2UserService;
  private final CustomAccessDeniedHandler customAccessDeniedHandler;
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .headers().frameOptions().disable()
      .and()
        .authorizeRequests()
        .antMatchers(
          "/",
          "/css/**",
          "/images/**",
          "/js/**",
          "/h2-console/**",
          "/webjars/**").permitAll()
        .antMatchers("/api/v1/**").hasRole(Role.USER.name())
        .anyRequest().authenticated()
      .and()
        .exceptionHandling()
          .accessDeniedHandler(customAccessDeniedHandler)
      .and()
        .logout()
          .logoutSuccessUrl("/")
      .and()
        .oauth2Login()
          .userInfoEndpoint()
            .userService(customOAuth2UserService);
  }
}
