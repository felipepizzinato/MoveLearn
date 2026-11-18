package br.com.movelearn.dao;

import br.com.movelearn.factory.ConnectionFactory;
import br.com.movelearn.model.Questao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** CRUD JDBC da tabela TB_ML_QUESTAO. As regras de negocio ficam nos services. */
public class QuestaoDAO {

    public void salvar(Questao objeto) throws SQLException {
        if (objeto == null) throw new IllegalArgumentException("Objeto obrigatorio");
        String sql = "INSERT INTO TB_ML_QUESTAO (questao_id, enunciado, ordem, aula_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection()) {
            long id = proximoId(conn);
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, id);
                ps.setString(2, objeto.getEnunciado());
                if (objeto.getOrdem() == null) ps.setNull(3, Types.NUMERIC);
                else ps.setInt(3, objeto.getOrdem());
                if (objeto.getAulaId() == null) ps.setNull(4, Types.NUMERIC);
                else ps.setLong(4, objeto.getAulaId());
                ps.executeUpdate();
            }
            objeto.setQuestaoId(id);
        }
    }

    public Questao buscarPorId(Long id) throws SQLException {
        if (id == null) throw new IllegalArgumentException("ID obrigatorio");
        String sql = "SELECT * FROM TB_ML_QUESTAO WHERE questao_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    public List<Questao> listarTodos() throws SQLException {
        String sql = "SELECT * FROM TB_ML_QUESTAO ORDER BY questao_id";
        List<Questao> lista = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public boolean atualizar(Questao objeto) throws SQLException {
        if (objeto == null || objeto.getQuestaoId() == null)
            throw new IllegalArgumentException("Objeto e ID obrigatorios");
        String sql = "UPDATE TB_ML_QUESTAO SET enunciado = ?, ordem = ?, aula_id = ? WHERE questao_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, objeto.getEnunciado());
            if (objeto.getOrdem() == null) ps.setNull(2, Types.NUMERIC);
            else ps.setInt(2, objeto.getOrdem());
            if (objeto.getAulaId() == null) ps.setNull(3, Types.NUMERIC);
            else ps.setLong(3, objeto.getAulaId());
            ps.setLong(4, objeto.getQuestaoId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean excluir(Long id) throws SQLException {
        if (id == null) throw new IllegalArgumentException("ID obrigatorio");
        String sql = "DELETE FROM TB_ML_QUESTAO WHERE questao_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public List<Questao> listarPorAulaId(Long id) throws SQLException {
        if (id == null) throw new IllegalArgumentException("ID de filtro obrigatorio");
        String sql = "SELECT * FROM TB_ML_QUESTAO WHERE aula_id = ? ORDER BY questao_id";
        List<Questao> lista = new ArrayList<>();
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
        try (PreparedStatement ps = conn.prepareStatement("SELECT SEQ_ML_QUESTAO.NEXTVAL FROM DUAL");
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getLong(1);
        }
    }

    private Questao mapear(ResultSet rs) throws SQLException {
        Questao objeto = new Questao();
        objeto.setQuestaoId(rs.getLong("questao_id"));
        objeto.setEnunciado(rs.getString("enunciado"));
        int ordemValor = rs.getInt("ordem");
        objeto.setOrdem(rs.wasNull() ? null : ordemValor);
        long aulaIdValor = rs.getLong("aula_id");
        objeto.setAulaId(rs.wasNull() ? null : aulaIdValor);
        return objeto;
    }
}
