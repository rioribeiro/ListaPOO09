package template;

import io.javelit.core.Jt;
import model.Categoria;
import model.Produto;
import service.Service;

import java.util.List;

public class ManterProdutoUI {
    public static void render() {
        Jt.markdown("## Cadastro de Produtos").use();
        String operacao = Jt.selectbox("Selecione a operacao",
                List.of("Listar", "Inserir", "Atualizar", "Excluir")).use();

        if (operacao != null) {
            switch (operacao) {
                case "Listar" -> listar();
                case "Inserir" -> inserir();
                case "Atualizar" -> atualizar();
                case "Excluir" -> excluir();
            }
        }
    }

    private static void listar() {
        List<Produto> produtos = Service.produto_listar();
        if (produtos.isEmpty()) {
            Jt.text("Nenhum produto cadastrado").use();
            return;
        }
        StringBuilder tabela = new StringBuilder("| ID | Descricao | Preco | Estoque | Categoria |\n|---|---|---|---|---|\n");
        for (Produto produto : produtos) {
            tabela.append(String.format("| %d | %s | %.2f | %d | %d |\n",
                    produto.getId(), produto.getDescricao(), produto.getPreco(),
                    produto.getEstoque(), produto.getIdCategoria()));
        }
        Jt.markdown(tabela.toString()).use();
    }

    private static void inserir() {
        Jt.markdown("### Inserir").use();
        List<Categoria> categorias = Service.categoria_listar();
        if (categorias.isEmpty()) {
            Jt.warning("Cadastre uma categoria antes de inserir produtos").use();
            return;
        }
        var form = Jt.form().use();
        String descricao = Jt.textInput("Descricao").use(form);
        double preco = numero(Jt.numberInput("Preco").value(0.0).use(form));
        Integer estoqueValor = Jt.numberInput("Estoque", Integer.class).value(0).use(form);
        int estoque = estoqueValor == null ? 0 : estoqueValor;
        List<String> opcoes = categorias.stream()
                .map(categoria -> categoria.getId() + " - " + categoria.getDescricao())
                .toList();
        int idCategoria = parseId(Jt.selectbox("Categoria", opcoes).use(form));

        if (Jt.formSubmitButton("Cadastrar produto").use(form)) {
            try {
                Service.produto_inserir(descricao, preco, estoque, idCategoria);
                Jt.success("Produto cadastrado com sucesso").use();
            } catch (Exception exception) {
                Jt.error(exception.getMessage()).use();
            }
        }
    }

    private static void atualizar() {
        Jt.markdown("### Atualizar").use();
        List<Produto> produtos = Service.produto_listar();
        if (produtos.isEmpty()) {
            Jt.text("Nenhum produto cadastrado").use();
            return;
        }
        List<Categoria> categorias = Service.categoria_listar();
        if (categorias.isEmpty()) {
            Jt.warning("Cadastre uma categoria antes de atualizar produtos").use();
            return;
        }
        List<String> opcoes = produtos.stream()
                .map(produto -> produto.getId() + " - " + produto.getDescricao())
                .toList();
        Produto produto = Service.produto_listar_id(parseId(Jt.selectbox("Escolha o produto", opcoes).use()));
        if (produto != null) {
            var form = Jt.form().use();
            String descricao = Jt.textInput("Nova descricao").value(produto.getDescricao()).use(form);
            double preco = numero(Jt.numberInput("Novo preco").value(produto.getPreco()).use(form));
            Integer estoqueValor = Jt.numberInput("Novo estoque", Integer.class)
                    .value(produto.getEstoque()).use(form);
            int estoque = estoqueValor == null ? 0 : estoqueValor;
            List<String> categoriasOpcoes = categorias.stream()
                    .map(categoria -> categoria.getId() + " - " + categoria.getDescricao())
                    .toList();
            int indexCategoria = 0;
            for (int i = 0; i < categorias.size(); i++) {
                if (categorias.get(i).getId() == produto.getIdCategoria()) {
                    indexCategoria = i;
                    break;
                }
            }
            int idCategoria = parseId(Jt.selectbox("Nova categoria", categoriasOpcoes)
                    .index(indexCategoria).use(form));
            if (Jt.formSubmitButton("Salvar alteracoes").use(form)) {
                try {
                    Service.produto_atualizar(produto.getId(), descricao, preco, estoque, idCategoria);
                    Jt.success("Produto atualizado com sucesso").use();
                } catch (Exception exception) {
                    Jt.error(exception.getMessage()).use();
                }
            }
        }
    }

    private static void excluir() {
        Jt.markdown("### Excluir").use();
        List<Produto> produtos = Service.produto_listar();
        if (produtos.isEmpty()) {
            Jt.text("Nenhum produto cadastrado").use();
            return;
        }
        List<String> opcoes = produtos.stream()
                .map(produto -> produto.getId() + " - " + produto.getDescricao())
                .toList();
        int id = parseId(Jt.selectbox("Escolha o produto", opcoes).use());
        var form = Jt.form().use();
        if (Jt.formSubmitButton("Excluir produto").use(form)) {
            try {
                Service.produto_excluir(id);
                Jt.success("Produto excluido com sucesso").use();
            } catch (Exception exception) {
                Jt.error(exception.getMessage()).use();
            }
        }
    }

    private static double numero(Number value) {
        return value == null ? 0.0 : value.doubleValue();
    }

    private static int parseId(String selecao) {
        if (selecao == null) {
            return 0;
        }
        return Integer.parseInt(selecao.substring(0, selecao.indexOf(" - ")));
    }
}
