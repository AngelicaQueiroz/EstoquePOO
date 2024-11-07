package org.example;

import java.util.ArrayList;

public class Estoque {
    public ArrayList<Produto> produtos;

    public Estoque() {
        produtos = new ArrayList<>();
    }

    // Adicionar produto ao estoque
    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
    }

    // Remover produto do estoque
    public void removerProduto(String nome) {
        produtos.removeIf(produto -> produto.getNome().equalsIgnoreCase(nome));
    }

    // Atualiza quantidade de Produto
    public void atualizarQuantidade(String id, int quantidade) {
        for (Produto produto : produtos) {
            if (produto.getId().equalsIgnoreCase(id)) {
                produto.setQuantidade(quantidade);
                break;
            }
        }
    }

    // Buscar produto (correção: agora retorna Produto)
    public Produto buscarProduto(String nome) {
        for (Produto produto : produtos) {
            if (produto.getNome().equalsIgnoreCase(nome)) {
                return produto;
            }
        }
        return null; // Retorna null se o produto não for encontrado
    }

    // Exibir todos os produtos
    public void listarProdutos() {
        for (Produto produto : produtos) {
            System.out.println(produto);
        }
    }
}
