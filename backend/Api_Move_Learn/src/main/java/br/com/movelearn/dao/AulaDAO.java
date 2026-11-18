package br.com.movelearn.dao;

import br.com.movelearn.factory.ConnectionFactory;
import br.com.movelearn.model.Aula;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** CRUD JDBC da tabela TB_ML_AULA. As regras de negocio ficam nos services. */
public class AulaDAO {

    public void salvar(Aula objeto) throws SQLException {
        if (objeto == null) throw new IllegalArgumentException("Objeto obrigatorio");
        String sql = "INSERT INTO TB_ML_AULA (aula_id, titulo, conteudo, duracao_minutos, ordem, curso_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection()) {
            long id = proximoId(conn);
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, id);
                ps.setString(2, objeto.getTitulo());
                ps.setString(3, objeto.getConteudo());
                if (objeto.getDuracaoMinutos() == null) ps.setNull(4, Types.NUMERIC);
                else ps.setInt(4, objeto.getDuracaoMinutos());
                if (objeto.getOrdem() == null) ps.setNull(5, Types.NUMERIC);
                else ps.setInt(5, objeto.getOrdem());
                if (objeto.getCursoId() == null) ps.setNull(6, Types.NUMERIC);
                else ps.setLong(6, objeto.getCursoId());
                ps.executeUpdate();
            }
            objeto.setAulaId(id);
        }
    }

    public Aula buscarPorId(Long id) throws SQLException {
        if (id == null) throw new IllegalArgumentException("ID obrigatorio");
        String sql = "SELECT * FROM TB_ML_AULA WHERE aula_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    public List<Aula> listarTodos() throws SQLException {
        String sql = "SELECT * FROM TB_ML_AULA ORDER BY aula_id";
        List<Aula> lista = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public boolean atualizar(Aula objeto) throws SQLException {
        if (objeto == null || objeto.getAulaId() == null)
            throw new IllegalArgumentException("Objeto e ID obrigatorios");
        String sql = "UPDATE TB_ML_AULA SET titulo = ?, conteudo = ?, duracao_minutos = ?, ordem = ?, curso_id = ? WHERE aula_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, objeto.getTitulo());
            ps.setString(2, objeto.getConteudo());
            if (objeto.getDuracaoMinutos() == null) ps.setNull(3, Types.NUMERIC);
            else ps.setInt(3, objeto.getDuracaoMinutos());
            if (objeto.getOrdem() == null) ps.setNull(4, Types.NUMERIC);
            else ps.setInt(4, objeto.getOrdem());
            if (objeto.getCursoId() == null) ps.setNull(5, Types.NUMERIC);
            else ps.setLong(5, objeto.getCursoId());
            ps.setLong(6, objeto.getAulaId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean excluir(Long id) throws SQLException {
        if (id == null) throw new IllegalArgumentException("ID obrigatorio");
        String sql = "DELETE FROM TB_ML_AULA WHERE aula_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public List<Aula> listarPorCursoId(Long id) throws SQLException {
        if (id == null) throw new IllegalArgumentException("ID de filtro obrigatorio");
        String sql = "SELECT * FROM TB_ML_AULA WHERE curso_id = ? ORDER BY aula_id";
        List<Aula> lista = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }

    private long proximoId(Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT SEQ_ML_AULA.NEXTVAL FROM DUAL");
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getLong(1);
        }
    }

    private Aula mapear(ResultSet rs) throws SQLException {
        Aula objeto = new Aula();
        objeto.setAulaId(rs.getLong("aula_id"));
        objeto.setTitulo(rs.getString("titulo"));
        objeto.setConteudo(rs.getString("conteudo"));
        int duracaoMinutosValor = rs.getInt("duracao_minutos");
        objeto.setDuracaoMinutos(rs.wasNull() ? null : duracaoMinutosValor);
        int ordemValor = rs.getInt("ordem");
        objeto.setOrdem(rs.wasNull() ? null : ordemValor);
        long cursoIdValor = rs.getLong("curso_id");
        objeto.setCursoId(rs.wasNull() ? null : cursoIdValor);
        return objeto;
    }
}
