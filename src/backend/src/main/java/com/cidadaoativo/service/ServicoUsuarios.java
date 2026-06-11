package com.cidadaoativo.service;

import com.cidadaoativo.domain.TipoUsuario;
import com.cidadaoativo.domain.Usuario;
import com.cidadaoativo.repository.RepositorioUsuarios;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ServicoUsuarios {

    private final RepositorioUsuarios repositorio;

    public ServicoUsuarios(RepositorioUsuarios repositorio) {
        this.repositorio = repositorio;
    }

    public Usuario criarUsuario(String nome, String email, String telefone,
                                String endereco, String bairro, TipoUsuario tipoUsuario) {
        validar(nome, email, tipoUsuario);

        if (repositorio.buscarPorEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email ja cadastrado: " + email);
        }

        String id = UUID.randomUUID().toString();
        Usuario usuario = new Usuario(id, nome, email, telefone, endereco, bairro, tipoUsuario);
        repositorio.salvar(usuario);
        return usuario;
    }

    private void validar(String nome, String email, TipoUsuario tipo) {
        if (nome == null || nome.trim().isEmpty())
            throw new IllegalArgumentException("Nome e obrigatorio");
        if (email == null || !email.contains("@") || !email.contains("."))
            throw new IllegalArgumentException("Email invalido");
        if (tipo == null)
            throw new IllegalArgumentException("Tipo de usuario e obrigatorio");
    }

    public Optional<Usuario> buscarPorId(String id) {
        return repositorio.buscarPorId(id);
    }

    public List<Usuario> listarPorTipo(TipoUsuario tipo) {
        return repositorio.buscarPorTipo(tipo);
    }

    public List<Usuario> listarTodos() {
        return repositorio.listarTodos();
    }
}
