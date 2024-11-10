package org.example;

//EXCLUIR ESTA PAGINA, JA ESTOU USANDO PRODUTO DAO, PELO QUE ENTENDI NAO PRECISA DE PRODUTO DAO E PRODUTO REPOSITORY AO MSM TEMPO

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    // Você pode adicionar métodos personalizados, se necessário
}

