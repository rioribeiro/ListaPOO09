package template;

import io.javelit.core.Jt;
import model.Cliente;
import service.Service;

import java.util.List;

public class ManterClienteUI {
    public static void render() {
        Jt.markdown("## Cadastro de Clientes").use();
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
        List<Cliente> clientes = Service.cliente_listar();
        if (clientes.isEmpty()) {
            Jt.text("Nenhum cliente cadastrado").use();
            return;
        }

        StringBuilder tabela = new StringBuilder("| ID | Nome | E-mail | Telefone |\n|---|---|---|---|\n");
        for (Cliente cliente : clientes) {
            tabela.append(String.format("| %d | %s | %s | %s |\n",
                    cliente.getId(), cliente.getNome(), cliente.getEmail(), cliente.getFone()));
        }
        Jt.markdown(tabela.toString()).use();
    }

    private static void inserir() {
        Jt.markdown("### Inserir").use();
        var form = Jt.form().use();
        String nome = Jt.textInput("Nome").use(form);
        String email = Jt.textInput("E-mail").use(form);
        String fone = Jt.textInput("Telefone").use(form);

        if (Jt.formSubmitButton("Cadastrar cliente").use(form)) {
            try {
                Service.cliente_inserir(nome, email, fone);
                Jt.success("Cliente cadastrado com sucesso").use();
            } catch (Exception exception) {
                Jt.error(exception.getMessage()).use();
            }
        }
    }

    private static void atualizar() {
        Jt.markdown("### Atualizar").use();
        List<Cliente> clientes = Service.cliente_listar();
        if (clientes.isEmpty()) {
            Jt.text("Nenhum cliente cadastrado").use();
            return;
        }

        List<String> opcoes = clientes.stream()
                .map(cliente -> cliente.getId() + " - " + cliente.getNome())
                .toList();
        String selecao = Jt.selectbox("Escolha o cliente", opcoes).use();
        Cliente cliente = Service.cliente_listar_id(parseId(selecao));

        if (cliente != null) {
            var form = Jt.form().use();
            String nome = Jt.textInput("Novo nome").value(cliente.getNome()).use(form);
            String email = Jt.textInput("Novo e-mail").value(cliente.getEmail()).use(form);
            String fone = Jt.textInput("Novo telefone").value(cliente.getFone()).use(form);

            if (Jt.formSubmitButton("Salvar alteracoes").use(form)) {
                try {
                    Service.cliente_atualizar(cliente.getId(), nome, email, fone);
                    Jt.success("Cliente atualizado com sucesso").use();
                } catch (Exception exception) {
                    Jt.error(exception.getMessage()).use();
                }
            }
        }
    }

    private static void excluir() {
        Jt.markdown("### Excluir").use();
        List<Cliente> clientes = Service.cliente_listar();
        if (clientes.isEmpty()) {
            Jt.text("Nenhum cliente cadastrado").use();
            return;
        }

        List<String> opcoes = clientes.stream()
                .map(cliente -> cliente.getId() + " - " + cliente.getNome())
                .toList();
        String selecao = Jt.selectbox("Escolha o cliente", opcoes).use();
        int id = parseId(selecao);

        var form = Jt.form().use();
        if (Jt.formSubmitButton("Excluir cliente").use(form)) {
            try {
                Service.cliente_excluir(id);
                Jt.success("Cliente excluido com sucesso").use();
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
