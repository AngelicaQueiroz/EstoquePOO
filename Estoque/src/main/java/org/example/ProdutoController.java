package org.example;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    @GetMapping("produtos")
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



    // Salva um novo produto via POST
    @PostMapping("produtos/salvar")
    public String salvarProduto(@ModelAttribute Produto produto) {
        produtoService.adicionarProduto(produto); // Usa o serviço para adicionar o produto
        return "redirect:/listar.html"; // Redireciona para a listagem
    }

    @PostMapping("produtos/excluir/{id}")
    public String excluirProdutoPost(@PathVariable String id, @RequestParam("_method") String method) {
        if ("delete".equalsIgnoreCase(method)) {
            produtoService.removerProduto(id);  // Chama o serviço para excluir o produto
        }
        return "redirect:/produtos";  // Redireciona para a lista de produtos
    }

    // Serve a página de formulário
    @GetMapping("produtos/novo")
    public String novoProduto(Model model) {
        model.addAttribute("produto", new Produto("0", "", 0.0, 0));
        return "formulario.html";
    }

    @GetMapping("/produtos/editar/{id}")
    public String editarProduto(@PathVariable("id") String id, Model model) {

        System.out.println("Recebido ID:" + id);

        Produto produto = produtoService.obterProdutoPorID(id);
        if (produto == null) {
            throw new RuntimeException("Produto não encontrado teste");
        }

        System.out.println("Produto encontrada: " + produto);

        model.addAttribute("produto", produto);
        return "formulario"; // pode omitir .html, o Spring Boot completa automaticamente
    }

    // Atualizar o produto após a edição
    @PostMapping("/produtos/editar")
    public String atualizarProduto(@ModelAttribute("produto") Produto produto) {
        produtoService.alterarProduto(produto);
        return "redirect:/listar.html"; // redireciona para a página de lista de produtos após salvar
    }

}




