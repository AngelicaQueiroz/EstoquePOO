package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    // Se necessário, você pode adicionar beans adicionais aqui
    @Bean
    public ProdutoDAO produtoDAO() {
        return new ProdutoDAO("estoque"); // Passa o nome do banco de dados para o DAO
    }

    @Bean
    public ProdutoService produtoService(ProdutoDAO produtoDAO) {
        return new ProdutoService(produtoDAO); // Injeta o DAO no Serviço
    }
}



