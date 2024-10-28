package org.example;

import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProdutoDAO implements AutoCloseable {
    private final Connection conexao;

    public ProdutoDAO(String nomeBanco) {
        try {
            // Define o caminho relativo ao diretório do projeto para o banco de dados
            Path currentRelativePath = Paths.get("");
            String projectPath = currentRelativePath.toAbsolutePath().toString();
            // Diretório onde o banco de dados será salvo dentro do projeto
            String dbDirectory = projectPath + File.separator + "db";
            File directory = new File(dbDirectory);
            // Verifica se o diretório existe, caso contrário cria
            if (!directory.exists()) {
                directory.mkdirs();
                System.out.println("Diretório criado: " + dbDirectory);
            }
            // Define a URL para o banco de dados SQLite
            String url = "jdbc:sqlite:" + dbDirectory + File.separator + nomeBanco + ".db"; // Correção de separador
            conexao = DriverManager.getConnection(url);
            criarTabelaProduto();
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ou criar o banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao conectar ou criar o banco de dados", e);
        }
    }

    private void criarTabelaProduto() {
        String sql = "CREATE TABLE IF NOT EXISTS produtos (" // Correção de "pessoas" para "produtos"
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nome TEXT,"
                + "preco REAL," // Corrigido para REAL
                + "quantidade INTEGER" // Corrigido para INTEGER
                + ")";
        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.execute();
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabela produtos: " + e.getMessage());
            throw new RuntimeException("Erro ao criar tabela produtos", e);
        }
    }

    public void inserirProduto(Produto produto) {
        String sql = "INSERT INTO produtos (nome, preco, quantidade) VALUES (?, ?, ?)"; // Removido 'id'
        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setString(1, produto.getNome());
            statement.setDouble(2, produto.getPreco());
            statement.setInt(3, produto.getQuantidade());
            statement.execute();
        } catch (SQLException e) {
            System.err.println("Erro ao inserir produto no banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao inserir produto no banco de dados", e);
        }
    }

    public Produto obterProdutoPorID(int id) {
        String sql = "SELECT id, nome, preco, quantidade FROM produtos WHERE id = ?"; // Removido '*'
        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet result = statement.executeQuery()) {
                if (!result.next()) {
                    return null;
                }
                return new Produto(result.getInt("id"),
                        result.getString("nome"),
                        result.getDouble("preco"),
                        result.getInt("quantidade")); // Corrigido para int
            }
        } catch (SQLException e) {
            System.err.println("Erro ao obter produto por ID: " + e.getMessage());
            throw new RuntimeException("Erro ao obter produto por ID", e);
        }
    }

    public List<Produto> obterTodosProdutos() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT id, nome, preco, quantidade FROM produtos"; // Removido '*'
        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    produtos.add(new Produto(
                            result.getInt("id"),
                            result.getString("nome"),
                            result.getDouble("preco"),
                            result.getInt("quantidade"))); // Corrigido para int
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao obter todos os produtos: " + e.getMessage());
            throw new RuntimeException("Erro ao obter todos os produtos", e);
        }
        return produtos;
    }

    public void alterarProdutos(int id, String nome, Double preco, Integer quantidade) {
        StringBuilder sqlBuilder = new StringBuilder("UPDATE produtos SET ");
        List<Object> parametros = new ArrayList<>();
        if (nome != null) {
            sqlBuilder.append("nome = ?");
            parametros.add(nome);
        }
        if (preco != null) {
            sqlBuilder.append(", preco = ?");
            parametros.add(preco);
        }
        if (quantidade != null) {
            sqlBuilder.append(", quantidade = ?");
            parametros.add(quantidade);
        }
        sqlBuilder.append(" WHERE id = ?");
        parametros.add(id);
        try (PreparedStatement statement = conexao.prepareStatement(sqlBuilder.toString())) {
            for (int i = 0; i < parametros.size(); i++) {
                statement.setObject(i + 1, parametros.get(i));
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao alterar produto: " + e.getMessage());
            throw new RuntimeException("Erro ao alterar produto", e);
        }
    }

    public void deletarProduto(int id) {
        String sql = "DELETE FROM produtos WHERE id = ?";
        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao deletar produto: " + e.getMessage());
            throw new RuntimeException("Erro ao deletar produto", e);
        }
    }

    @Override
    public void close() {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar a conexão: " + e.getMessage());
        }
    }
}
