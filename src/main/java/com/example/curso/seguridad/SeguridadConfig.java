package com.example.curso.seguridad;

import com.example.curso.service.EmpleadoDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SeguridadConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    EmpleadoDetailsService eDetails;

    @Bean
    public BCryptPasswordEncoder codificador(){
        return new BCryptPasswordEncoder();
    }

    public void configureGlobal(AuthenticationManagerBuilder build) throws Exception{
        build.userDetailsService(eDetails).passwordEncoder(codificador());
    }

}