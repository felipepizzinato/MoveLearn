package br.com.movelearn.dao;

import br.com.movelearn.factory.ConnectionFactory;
import br.com.movelearn.model.Alternativa;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** CRUD JDBC da tabela TB_ML_ALTERNATIVA. As regras de negocio ficam nos services. */
public class AlternativaDAO {

    public void salvar(Alternativa objeto) throws SQLException {
        if (objeto == null) throw new IllegalArgumentException("Objeto obrigatorio");
        String sql = "INSERT INTO TB_ML_ALTERNATIVA (alternativa_id, texto, correta, questao_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection()) {
            long id = proximoId(conn);
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, id);
                ps.setString(2, objeto.getTexto());
                ps.setString(3, objeto.getCorreta());
                if (objeto.getQuestaoId() == null) ps.setNull(4, Types.NUMERIC);
                else ps.setLong(4, objeto.getQuestaoId());
                ps.executeUpdate();
            }
            objeto.setAlternativaId(id);
        }
    }

    public Alternativa buscarPorId(Long id) throws SQLException {
        if (id == null) throw new IllegalArgumentException("ID obrigatorio");
        String sql = "SELECT * FROM TB_ML_ALTERNATIVA WHERE alternativa_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    public List<Alternativa> listarTodos() throws SQLException {
        String sql = "SELECT * FROM TB_ML_ALTERNATIVA ORDER BY alternativa_id";
        List<Alternativa> lista = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public boolean atualizar(Alternativa objeto) throws SQLException {
        if (objeto == null || objeto.getAlternativaId() == null)
            throw new IllegalArgumentException("Objeto e ID obrigatorios");
        String sql = "UPDATE TB_ML_ALTERNATIVA SET texto = ?, correta = ?, questao_id = ? WHERE alternativa_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, objeto.getTexto());
            ps.setString(2, objeto.getCorreta());
            if (objeto.getQuestaoId() == null) ps.setNull(3, Types.NUMERIC);
            else ps.setLong(3, objeto.getQuestaoId());
            ps.setLong(4, objeto.getAlternativaId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean excluir(Long id) throws SQLException {
        if (id == null) throw new IllegalArgumentException("ID obrigatorio");
        String sql = "DELETE FROM TB_ML_ALTERNATIVA WHERE alternativa_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public List<Alternativa> listarPorQuestaoId(Long id) throws SQLException {
        if (id == null) throw new IllegalArgumentException("ID de filtro obrigatorio");
        String sql = "SELECT * FROM TB_ML_ALTERNATIVA WHERE questao_id = ? ORDER BY alternativa_id";
        List<Alternativa> lista = new ArrayList<>();
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
        try (PreparedStatement ps = conn.prepareStatement("SELECT SEQ_ML_ALTERNATIVA.NEXTVAL FROM DUAL");
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getLong(1);
        }
    }

    private Alternativa mapear(ResultSet rs) throws SQLException {
        Alternativa objeto = new Alternativa();
        objeto.setAlternativaId(rs.getLong("alternativa_id"));
        objeto.setTexto(rs.getString("texto"));
        objeto.setCorreta(rs.getString("correta"));
        long questaoIdValor = rs.getLong("questao_id");
        objeto.setQuestaoId(rs.wasNull() ? null : questaoIdValor);
        return objeto;
    }
}
