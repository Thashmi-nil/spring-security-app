package com.example.demo.config;

import com.example.demo.services.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //in-memory authorization
        auth.inMemoryAuthentication().withUser("Sanjana").password(passwordEncoder()
                .encode("san@123"))
                .authorities("USER","ADMIN"); //username & password

        //database authorization
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }
    @Bean
    public PasswordEncoder passwordEncoder(){   //encode the password
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().anyRequest().permitAll();  //any request come in to the system will permit

        http.authorizeRequests().anyRequest().authenticated();//any request come in to the system will authenticated
        http.formLogin();
    }
}
