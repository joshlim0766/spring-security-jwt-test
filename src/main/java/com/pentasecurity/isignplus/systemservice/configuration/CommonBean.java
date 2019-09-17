package com.pentasecurity.isignplus.systemservice.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Configuration
public class CommonBean {

    @Bean
    public ModelMapper modelMapper () {
        return new ModelMapper();
    }

    @Bean
    PasswordEncoder passwordEncoder () throws NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        return new BCryptPasswordEncoder(16, secureRandom);
    }
}
