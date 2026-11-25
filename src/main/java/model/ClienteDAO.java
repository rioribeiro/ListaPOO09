package model;

import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    private static final List<Cliente> objetos = new ArrayList<>();
    private static int proximoId = 1;

    public static void inserir(Cliente cliente) {
        if (cliente.getId() == 0) {
            cliente.setId(proximoId);
        }
        proximoId = Math.max(proximoId, cliente.getId() + 1);
        objetos.add(cliente);
    }

    public static List<Cliente> listar() {
        return new ArrayList<>(objetos);
    }

    public static Cliente listar(int id) {
        return objetos.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }

    public static void atualizar(Cliente cliente) {
        for (int i = 0; i < objetos.size(); i++) {
            if (objetos.get(i).getId() == cliente.getId()) {
                objetos.set(i, cliente);
                return;
            }
        }
    }

    public static void excluir(int id) {
        objetos.removeIf(c -> c.getId() == id);
    }
}
