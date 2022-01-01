package org.gorany.bookbook.config.auth;

import lombok.RequiredArgsConstructor;
import org.gorany.bookbook.api.domain.user.Role;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity //Spring Security 설정 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .headers().frameOptions().disable()
        .and()
            .authorizeRequests()
            .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**")
            .permitAll()
            .antMatchers("/api/v1/**")
            .hasRole(Role.USER.name())
            .anyRequest().authenticated()
        .and()
            .logout()
            .logoutSuccessUrl("/")
        .and()
            .oauth2Login()
            .userInfoEndpoint()
            .userService(userService);
    }
}
