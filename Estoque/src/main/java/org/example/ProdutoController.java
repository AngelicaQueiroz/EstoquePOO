package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService; // Injetando ProdutoService

    // Serve a página inicial
    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    // Serve a página de listagem
    @GetMapping("/produtos")
    public String listarProdutos(Model model) {
        List<Produto> produtos = produtoService.listarTodosProdutos(); // Obtém a lista de produtos do serviço
        model.addAttribute("produtos", produtos); // Adiciona a lista ao modelo
        return "listar.html"; // Retorna a página de listagem
    }

    // Serve a API de listagem de produtos (para JavaScript)
    @GetMapping("/produtos/api")
    @ResponseBody
    public List<Produto> listarProdutosApi() {
        return produtoService.listarTodosProdutos(); // Usa o serviço para obter os produtos
    }

    // Serve a página de formulário
    @GetMapping("/produtos/novo")
    public String novoProduto(Model model) {
        model.addAttribute("produto", new Produto(0, "", 0.0, 0));
        return "formulario.html";
    }

    // Salva um novo produto via POST
    @PostMapping("/produtos/salvar")
    public String salvarProduto(@ModelAttribute Produto produto) {
        produtoService.adicionarProduto(produto); // Usa o serviço para adicionar o produto
        return "redirect:/produtos";
    }
}

