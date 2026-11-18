package br.com.movelearn.dao;

import br.com.movelearn.factory.ConnectionFactory;
import br.com.movelearn.model.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** CRUD JDBC da tabela TB_ML_USUARIO. As regras de negocio ficam nos services. */
public class UsuarioDAO {

    public void salvar(Usuario objeto) throws SQLException {
        if (objeto == null) throw new IllegalArgumentException("Objeto obrigatorio");
        String sql = "INSERT INTO TB_ML_USUARIO (usuario_id, nome, email, senha_hash, dt_criacao) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection()) {
            long id = proximoId(conn);
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, id);
                ps.setString(2, objeto.getNome());
                ps.setString(3, objeto.getEmail());
                ps.setString(4, objeto.getSenhaHash());
                if (objeto.getDtCriacao() == null) ps.setNull(5, Types.TIMESTAMP);
                else ps.setTimestamp(5, Timestamp.valueOf(objeto.getDtCriacao()));
                ps.executeUpdate();
            }
            objeto.setUsuarioId(id);
        }
    }

    public Usuario buscarPorId(Long id) throws SQLException {
        if (id == null) throw new IllegalArgumentException("ID obrigatorio");
        String sql = "SELECT * FROM TB_ML_USUARIO WHERE usuario_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    public List<Usuario> listarTodos() throws SQLException {
        String sql = "SELECT * FROM TB_ML_USUARIO ORDER BY usuario_id";
        List<Usuario> lista = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public boolean atualizar(Usuario objeto) throws SQLException {
        if (objeto == null || objeto.getUsuarioId() == null)
            throw new IllegalArgumentException("Objeto e ID obrigatorios");
        String sql = "UPDATE TB_ML_USUARIO SET nome = ?, email = ?, senha_hash = ?, dt_criacao = ? WHERE usuario_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, objeto.getNome());
            ps.setString(2, objeto.getEmail());
            ps.setString(3, objeto.getSenhaHash());
            if (objeto.getDtCriacao() == null) ps.setNull(4, Types.TIMESTAMP);
            else ps.setTimestamp(4, Timestamp.valueOf(objeto.getDtCriacao()));
            ps.setLong(5, objeto.getUsuarioId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean excluir(Long id) throws SQLException {
        if (id == null) throw new IllegalArgumentException("ID obrigatorio");
        String sql = "DELETE FROM TB_ML_USUARIO WHERE usuario_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private long proximoId(Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT SEQ_ML_USUARIO.NEXTVAL FROM DUAL");
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getLong(1);
        }
    }

    private Usuario mapear(ResultSet rs) throws SQLException {
        Usuario objeto = new Usuario();
        objeto.setUsuarioId(rs.getLong("usuario_id"));
        objeto.setNome(rs.getString("nome"));
        objeto.setEmail(rs.getString("email"));
        objeto.setSenhaHash(rs.getString("senha_hash"));
        Timestamp dtCriacaoTs = rs.getTimestamp("dt_criacao");
        objeto.setDtCriacao(dtCriacaoTs == null ? null : dtCriacaoTs.toLocalDateTime());
        return objeto;
    }
}
