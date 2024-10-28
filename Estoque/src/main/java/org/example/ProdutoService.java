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

    public Produto obterProdutoPorID(int id) {
        return produtoDAO.obterProdutoPorID(id);
    }

    public List<Produto> listarTodosProdutos() {
        return produtoDAO.obterTodosProdutos();
    }

    public void atualizarProduto(int id, String nome, Double preco, Integer quantidade) {
        produtoDAO.alterarProdutos(id, nome, preco, quantidade);
    }

    public void removerProduto(int id) {
        produtoDAO.deletarProduto(id);
    }
}


