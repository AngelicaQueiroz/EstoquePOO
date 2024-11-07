package org.example;

public class Produto {
    private String id;
    private String nome;
    private double preco;
    private int quantidade;

    // Construtor
    public Produto(String id,String nome, double preco, int quantidade) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    // Getters e Setters
    public String getId() {return id;}
    public String getNome() {
        return nome;
    }
    public double getPreco() {
        return preco;
    }
    public int getQuantidade() {
        return quantidade;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setPreco(double preco) {
        this.preco = preco;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    // Atualiza a quantidade de produto
    public void adicionarQuantidade(int quantidade) {
        this.quantidade += quantidade;
    }

    public void diminuirQuantidade(int quantidade) {
        if (this.quantidade >= quantidade) {
            this.quantidade -= quantidade;
        } else {
            System.out.println("Quantidade insuficiente no estoque!");
        }
    }

    public void imprimir() {
        System.out.println("id: " + id);
        System.out.println("Nome: " + nome);
        System.out.println("Preço: " + preco);
        System.out.println("Quantidade: " + quantidade);
    }

    @Override
    public String toString() {
        return "Produto: " + nome + ", Preço: " + preco + ", Quantidade: " + quantidade;
    }
}