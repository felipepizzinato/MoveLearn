package br.com.movelearn.dao;

import br.com.movelearn.factory.ConnectionFactory;
import br.com.movelearn.model.Curso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** CRUD JDBC da tabela TB_ML_CURSO. As regras de negocio ficam nos services. */
public class CursoDAO {

    public void salvar(Curso objeto) throws SQLException {
        if (objeto == null) throw new IllegalArgumentException("Objeto obrigatorio");
        String sql = "INSERT INTO TB_ML_CURSO (curso_id, titulo, descricao, nivel) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection()) {
            long id = proximoId(conn);
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, id);
                ps.setString(2, objeto.getTitulo());
                ps.setString(3, objeto.getDescricao());
                ps.setString(4, objeto.getNivel());
                ps.executeUpdate();
            }
            objeto.setCursoId(id);
        }
    }

    public Curso buscarPorId(Long id) throws SQLException {
        if (id == null) throw new IllegalArgumentException("ID obrigatorio");
        String sql = "SELECT * FROM TB_ML_CURSO WHERE curso_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    public List<Curso> listarTodos() throws SQLException {
        String sql = "SELECT * FROM TB_ML_CURSO ORDER BY curso_id";
        List<Curso> lista = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public boolean atualizar(Curso objeto) throws SQLException {
        if (objeto == null || objeto.getCursoId() == null)
            throw new IllegalArgumentException("Objeto e ID obrigatorios");
        String sql = "UPDATE TB_ML_CURSO SET titulo = ?, descricao = ?, nivel = ? WHERE curso_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, objeto.getTitulo());
            ps.setString(2, objeto.getDescricao());
            ps.setString(3, objeto.getNivel());
            ps.setLong(4, objeto.getCursoId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean excluir(Long id) throws SQLException {
        if (id == null) throw new IllegalArgumentException("ID obrigatorio");
        String sql = "DELETE FROM TB_ML_CURSO WHERE curso_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private long proximoId(Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT SEQ_ML_CURSO.NEXTVAL FROM DUAL");
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getLong(1);
        }
    }

    private Curso mapear(ResultSet rs) throws SQLException {
        Curso objeto = new Curso();
        objeto.setCursoId(rs.getLong("curso_id"));
        objeto.setTitulo(rs.getString("titulo"));
        objeto.setDescricao(rs.getString("descricao"));
        objeto.setNivel(rs.getString("nivel"));
        return objeto;
    }
}
