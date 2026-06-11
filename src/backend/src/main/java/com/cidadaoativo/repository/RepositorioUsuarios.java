package com.cidadaoativo.repository;

import com.cidadaoativo.domain.TipoUsuario;
import com.cidadaoativo.domain.Usuario;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class RepositorioUsuarios {

    private final Map<String, Usuario> usuarios = new ConcurrentHashMap<>();

    public void salvar(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario nao pode ser nulo");
        }
        usuarios.put(usuario.getId(), usuario);
    }

    public Optional<Usuario> buscarPorId(String id) {
        if (id == null) return Optional.empty();
        return Optional.ofNullable(usuarios.get(id));
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarios.values().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public List<Usuario> buscarPorTipo(TipoUsuario tipo) {
        return usuarios.values().stream()
                .filter(u -> u.getTipoUsuario() == tipo)
                .collect(Collectors.toList());
    }

    public List<Usuario> listarTodos() {
        return new ArrayList<>(usuarios.values());
    }

    public int contar() {
        return usuarios.size();
    }
}
