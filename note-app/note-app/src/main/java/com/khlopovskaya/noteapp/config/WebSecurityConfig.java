package com.khlopovskaya.noteapp.config;


import com.khlopovskaya.noteapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserService userService;

    @Autowired
    public WebSecurityConfig(UserService userService) {
        this.userService = userService;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/").hasAnyAuthority("USER")
                .antMatchers("/login", "/sign").permitAll()
                .antMatchers("/logout").hasAnyAuthority("USER")
                .anyRequest().authenticated()
                .and().httpBasic()
                .and()
                .logout()
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true)
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                .and()
                .formLogin()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .sessionFixation().migrateSession();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userService)
                .passwordEncoder(encoder());
    }

    @Bean
    protected BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}