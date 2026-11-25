package template;

import io.javelit.core.Jt;

import java.util.List;

public class IndexUI {
    public static void main(String[] args) {
        Jt.title("Sistema de Comercio Eletronico").use();
        Jt.markdown("Painel administrativo").use();

        String menu = Jt.selectbox("Menu do Administrador",
                List.of(
                        "Cadastro de Clientes",
                        "Cadastro de Categorias",
                        "Cadastro de Produtos",
                        "Reajustar Precos"
                )).use();

        if (menu != null) {
            switch (menu) {
                case "Cadastro de Clientes" -> ManterClienteUI.render();
                case "Cadastro de Categorias" -> ManterCategoriaUI.render();
                case "Cadastro de Produtos" -> ManterProdutoUI.render();
                case "Reajustar Precos" -> ReajustarProdutoUI.render();
            }
        }
    }
}
