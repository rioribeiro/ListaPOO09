package model;

import java.util.Objects;

public class Categoria {
    private int id;
    private String descricao;

    public Categoria(int id, String descricao) {
        setId(id);
        setDescricao(descricao);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = Objects.requireNonNullElse(descricao, "").trim();
    }

    @Override
    public String toString() {
        return id + " - " + descricao;
    }
}
