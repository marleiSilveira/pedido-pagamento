package com.marlei.pedido.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ApplicationConfig {
    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}
