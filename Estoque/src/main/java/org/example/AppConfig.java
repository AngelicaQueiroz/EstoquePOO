package org.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ProdutoDAO produtoDAO() {
        String nomeBanco = "nomeBanco";
        return new ProdutoDAO(nomeBanco);
    }
}

