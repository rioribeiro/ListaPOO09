package model;

import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
    private static final List<Produto> objetos = new ArrayList<>();
    private static int proximoId = 1;

    public static void inserir(Produto produto) {
        if (produto.getId() == 0) {
            produto.setId(proximoId);
        }
        proximoId = Math.max(proximoId, produto.getId() + 1);
        objetos.add(produto);
    }

    public static List<Produto> listar() {
        return new ArrayList<>(objetos);
    }

    public static Produto listar(int id) {
        return objetos.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    public static void atualizar(Produto produto) {
        for (int i = 0; i < objetos.size(); i++) {
            if (objetos.get(i).getId() == produto.getId()) {
                objetos.set(i, produto);
                return;
            }
        }
    }

    public static void excluir(int id) {
        objetos.removeIf(p -> p.getId() == id);
    }
}
