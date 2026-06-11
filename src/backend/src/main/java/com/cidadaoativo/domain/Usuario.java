package com.cidadaoativo.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String id;
    private String nome;
    private String email;
    private String telefone;
    private final String endereco;
    private final String bairro;
    private final TipoUsuario tipoUsuario;
    private final LocalDateTime dataCadastro;

    public Usuario(String id, String nome, String email, String telefone,
                   String endereco, String bairro, TipoUsuario tipoUsuario) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.bairro = bairro;
        this.tipoUsuario = tipoUsuario;
        this.dataCadastro = LocalDateTime.now();
    }

    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getTelefone() { return telefone; }
    public String getEndereco() { return endereco; }
    public String getBairro() { return bairro; }
    public TipoUsuario getTipoUsuario() { return tipoUsuario; }
    public LocalDateTime getDataCadastro() { return dataCadastro; }

    public void setNome(String nome) { this.nome = nome; }
    public void setEmail(String email) { this.email = email; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    @Override
    public String toString() {
        return "Usuario{id='" + id + "', nome='" + nome + "', email='" + email +
                "', tipo=" + tipoUsuario + '}';
    }
}
