package template;

import io.javelit.core.Jt;
import model.Categoria;
import service.Service;

import java.util.List;

public class ManterCategoriaUI {
    public static void render() {
        Jt.markdown("## Cadastro de Categorias").use();
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
        List<Categoria> categorias = Service.categoria_listar();
        if (categorias.isEmpty()) {
            Jt.text("Nenhuma categoria cadastrada").use();
            return;
        }
        StringBuilder tabela = new StringBuilder("| ID | Descricao |\n|---|---|\n");
        for (Categoria categoria : categorias) {
            tabela.append(String.format("| %d | %s |\n", categoria.getId(), categoria.getDescricao()));
        }
        Jt.markdown(tabela.toString()).use();
    }

    private static void inserir() {
        Jt.markdown("### Inserir").use();
        var form = Jt.form().use();
        String descricao = Jt.textInput("Descricao").use(form);
        if (Jt.formSubmitButton("Cadastrar categoria").use(form)) {
            try {
                Service.categoria_inserir(descricao);
                Jt.success("Categoria cadastrada com sucesso").use();
            } catch (Exception exception) {
                Jt.error(exception.getMessage()).use();
            }
        }
    }

    private static void atualizar() {
        Jt.markdown("### Atualizar").use();
        List<Categoria> categorias = Service.categoria_listar();
        if (categorias.isEmpty()) {
            Jt.text("Nenhuma categoria cadastrada").use();
            return;
        }
        List<String> opcoes = categorias.stream()
                .map(categoria -> categoria.getId() + " - " + categoria.getDescricao())
                .toList();
        String selecao = Jt.selectbox("Escolha a categoria", opcoes).use();
        Categoria categoria = Service.categoria_listar_id(parseId(selecao));
        if (categoria != null) {
            var form = Jt.form().use();
            String descricao = Jt.textInput("Nova descricao")
                    .value(categoria.getDescricao()).use(form);
            if (Jt.formSubmitButton("Salvar alteracoes").use(form)) {
                try {
                    Service.categoria_atualizar(categoria.getId(), descricao);
                    Jt.success("Categoria atualizada com sucesso").use();
                } catch (Exception exception) {
                    Jt.error(exception.getMessage()).use();
                }
            }
        }
    }

    private static void excluir() {
        Jt.markdown("### Excluir").use();
        List<Categoria> categorias = Service.categoria_listar();
        if (categorias.isEmpty()) {
            Jt.text("Nenhuma categoria cadastrada").use();
            return;
        }
        List<String> opcoes = categorias.stream()
                .map(categoria -> categoria.getId() + " - " + categoria.getDescricao())
                .toList();
        String selecao = Jt.selectbox("Escolha a categoria", opcoes).use();
        int id = parseId(selecao);
        var form = Jt.form().use();
        if (Jt.formSubmitButton("Excluir categoria").use(form)) {
            try {
                Service.categoria_excluir(id);
                Jt.success("Categoria excluida com sucesso").use();
            } catch (Exception exception) {
                Jt.error(exception.getMessage()).use();
            }
        }
    }

    private static int parseId(String selecao) {
        if (selecao == null) {
            return 0;
        }
        return Integer.parseInt(selecao.substring(0, selecao.indexOf(" - ")));
    }
}
