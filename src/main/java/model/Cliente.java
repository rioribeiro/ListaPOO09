package model;

import java.util.Objects;

public class Cliente {
    private int id;
    private String nome;
    private String email;
    private String fone;

    public Cliente(int id, String nome, String email, String fone) {
        setId(id);
        setNome(nome);
        setEmail(email);
        setFone(fone);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = Objects.requireNonNullElse(nome, "").trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = Objects.requireNonNullElse(email, "").trim();
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = Objects.requireNonNullElse(fone, "").trim();
    }

    @Override
    public String toString() {
        return id + " - " + nome;
    }
}
