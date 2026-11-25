package model;

import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {
    private static final List<Categoria> objetos = new ArrayList<>();
    private static int proximoId = 1;

    public static void inserir(Categoria categoria) {
        if (categoria.getId() == 0) {
            categoria.setId(proximoId);
        }
        proximoId = Math.max(proximoId, categoria.getId() + 1);
        objetos.add(categoria);
    }

    public static List<Categoria> listar() {
        return new ArrayList<>(objetos);
    }

    public static Categoria listar(int id) {
        return objetos.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }

    public static void atualizar(Categoria categoria) {
        for (int i = 0; i < objetos.size(); i++) {
            if (objetos.get(i).getId() == categoria.getId()) {
                objetos.set(i, categoria);
                return;
            }
        }
    }

    public static void excluir(int id) {
        objetos.removeIf(c -> c.getId() == id);
    }
}
