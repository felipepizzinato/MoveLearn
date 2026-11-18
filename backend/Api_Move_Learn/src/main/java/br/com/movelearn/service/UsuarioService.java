package br.com.movelearn.service;

import br.com.movelearn.dao.UsuarioDAO;
import br.com.movelearn.model.Usuario;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class UsuarioService {
    private final UsuarioDAO dao = new UsuarioDAO();

    // Recebe um hash de senha previamente calculado; nunca armazene senha em texto puro.
    public Usuario cadastrar(String nome, String email, String senhaHash) throws SQLException {
        if (vazio(nome) || vazio(email) || !email.contains("@") || vazio(senhaHash))
            throw new IllegalArgumentException("Nome, email valido e hash de senha sao obrigatorios.");
        for (Usuario u : dao.listarTodos()) {
            if (u.getEmail() != null && u.getEmail().equalsIgnoreCase(email.trim()))
                throw new IllegalArgumentException("Email ja cadastrado.");
        }
        Usuario usuario = new Usuario(null, nome.trim(), email.trim(), senhaHash, LocalDateTime.now());
        dao.salvar(usuario);
        return usuario;
    }

    public Usuario buscar(Long id) throws SQLException { return dao.buscarPorId(id); }
    public List<Usuario> listar() throws SQLException { return dao.listarTodos(); }

    public boolean atualizar(Usuario usuario) throws SQLException {
        if (usuario == null || usuario.getUsuarioId() == null || vazio(usuario.getNome()) || vazio(usuario.getEmail())
                || !usuario.getEmail().contains("@") || vazio(usuario.getSenhaHash()))
            throw new IllegalArgumentException("Dados do usuario invalidos.");
        return dao.atualizar(usuario);
    }
    public boolean excluir(Long id) throws SQLException { return dao.excluir(id); }
    private boolean vazio(String s) { return s == null || s.isBlank(); }
}
