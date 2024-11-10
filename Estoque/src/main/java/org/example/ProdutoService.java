package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoDAO produtoDAO;

    @Autowired
    public ProdutoService(ProdutoDAO produtoDAO) {
        this.produtoDAO = produtoDAO;
    }

    public void adicionarProduto(Produto produto) {
        produtoDAO.inserirProduto(produto);
    }

    public Produto obterProdutoPorID(String id) {
        return produtoDAO.obterProdutoPorID(id);
    }

    public List<Produto> listarTodosProdutos() {
        return produtoDAO.obterTodosProdutos();
    }

    public void alterarProduto(Produto produto) {
        produtoDAO.alterarProduto(produto);
    }


    public void removerProduto(String id) {
        produtoDAO.deletarProduto(Integer.parseInt(id)); // Passa o id como String, sem conversão
    }


}


