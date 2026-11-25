package service;

import model.Categoria;
import model.CategoriaDAO;
import model.Cliente;
import model.ClienteDAO;
import model.Produto;
import model.ProdutoDAO;

import java.util.List;

public class Service {
    public static void cliente_inserir(String nome, String email, String fone) {
        validarNome(nome);
        ClienteDAO.inserir(new Cliente(0, nome, email, fone));
    }

    public static List<Cliente> cliente_listar() {
        return ClienteDAO.listar();
    }

    public static Cliente cliente_listar_id(int id) {
        return ClienteDAO.listar(id);
    }

    public static void cliente_atualizar(int id, String nome, String email, String fone) {
        validarNome(nome);
        ClienteDAO.atualizar(new Cliente(id, nome, email, fone));
    }

    public static void cliente_excluir(int id) {
        ClienteDAO.excluir(id);
    }

    public static void categoria_inserir(String descricao) {
        validarDescricao(descricao);
        CategoriaDAO.inserir(new Categoria(0, descricao));
    }

    public static List<Categoria> categoria_listar() {
        return CategoriaDAO.listar();
    }

    public static Categoria categoria_listar_id(int id) {
        return CategoriaDAO.listar(id);
    }

    public static void categoria_atualizar(int id, String descricao) {
        validarDescricao(descricao);
        CategoriaDAO.atualizar(new Categoria(id, descricao));
    }

    public static void categoria_excluir(int id) {
        CategoriaDAO.excluir(id);
    }

    public static void produto_inserir(String descricao, double preco, int estoque, int idCategoria) {
        validarDescricao(descricao);
        validarProduto(preco, estoque);
        if (CategoriaDAO.listar(idCategoria) == null) {
            throw new IllegalArgumentException("Categoria invalida");
        }
        ProdutoDAO.inserir(new Produto(0, descricao, preco, estoque, idCategoria));
    }

    public static List<Produto> produto_listar() {
        return ProdutoDAO.listar();
    }

    public static Produto produto_listar_id(int id) {
        return ProdutoDAO.listar(id);
    }

    public static void produto_atualizar(int id, String descricao, double preco, int estoque, int idCategoria) {
        validarDescricao(descricao);
        validarProduto(preco, estoque);
        if (CategoriaDAO.listar(idCategoria) == null) {
            throw new IllegalArgumentException("Categoria invalida");
        }
        ProdutoDAO.atualizar(new Produto(id, descricao, preco, estoque, idCategoria));
    }

    public static void produto_excluir(int id) {
        ProdutoDAO.excluir(id);
    }

    public static void produto_reajustar(double percentual) {
        for (Produto produto : ProdutoDAO.listar()) {
            double novoPreco = produto.getPreco() * (1 + percentual / 100.0);
            if (novoPreco < 0) {
                novoPreco = 0;
            }
            produto.setPreco(novoPreco);
            ProdutoDAO.atualizar(produto);
        }
    }

    private static void validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Informe o nome");
        }
    }

    private static void validarDescricao(String descricao) {
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("Informe a descricao");
        }
    }

    private static void validarProduto(double preco, int estoque) {
        if (preco < 0) {
            throw new IllegalArgumentException("O preco nao pode ser negativo");
        }
        if (estoque < 0) {
            throw new IllegalArgumentException("O estoque nao pode ser negativo");
        }
    }
}
