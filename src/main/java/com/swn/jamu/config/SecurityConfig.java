package com.swn.jamu.config;

import com.swn.jamu.constant.RoleConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers("/css/**", "/img/**", "/js/**", "/plugins/**").permitAll()
                                .requestMatchers("/index").permitAll()
                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers("/dashboard").authenticated()
                                .requestMatchers("/user/**").hasAnyRole(RoleConstant.ADMIN)
                                .requestMatchers("/branch/**").hasAnyRole(RoleConstant.ADMIN)
                                .requestMatchers("/base-jamu/**").hasAnyRole(RoleConstant.ADMIN)
                                .requestMatchers("/jamu/**").hasAnyRole(RoleConstant.ADMIN)
                                .requestMatchers("/branch-procurement/distributor/**").hasAnyRole(RoleConstant.ADMIN)
                                .requestMatchers("/branch-procurement/branch/**").hasAnyRole(RoleConstant.STAFF)
                ).formLogin(
                        form -> form
                                .loginPage("/auth/login")
                                .loginProcessingUrl("/auth/login")
                                .defaultSuccessUrl("/dashboard")
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"))
                                .permitAll()
                );
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}
