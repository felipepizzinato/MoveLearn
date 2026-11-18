package br.com.movelearn.dao;

import br.com.movelearn.factory.ConnectionFactory;
import br.com.movelearn.model.ProgressoAula;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** CRUD JDBC da tabela TB_ML_PROGRESSO_AULA. As regras de negocio ficam nos services. */
public class ProgressoAulaDAO {

    public void salvar(ProgressoAula objeto) throws SQLException {
        if (objeto == null) throw new IllegalArgumentException("Objeto obrigatorio");
        String sql = "INSERT INTO TB_ML_PROGRESSO_AULA (progresso_id, concluido, dt_conclusao, matricula_id, aula_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection()) {
            long id = proximoId(conn);
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, id);
                ps.setString(2, objeto.getConcluido());
                if (objeto.getDtConclusao() == null) ps.setNull(3, Types.TIMESTAMP);
                else ps.setTimestamp(3, Timestamp.valueOf(objeto.getDtConclusao()));
                if (objeto.getMatriculaId() == null) ps.setNull(4, Types.NUMERIC);
                else ps.setLong(4, objeto.getMatriculaId());
                if (objeto.getAulaId() == null) ps.setNull(5, Types.NUMERIC);
                else ps.setLong(5, objeto.getAulaId());
                ps.executeUpdate();
            }
            objeto.setProgressoId(id);
        }
    }

    public ProgressoAula buscarPorId(Long id) throws SQLException {
        if (id == null) throw new IllegalArgumentException("ID obrigatorio");
        String sql = "SELECT * FROM TB_ML_PROGRESSO_AULA WHERE progresso_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    public List<ProgressoAula> listarTodos() throws SQLException {
        String sql = "SELECT * FROM TB_ML_PROGRESSO_AULA ORDER BY progresso_id";
        List<ProgressoAula> lista = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public boolean atualizar(ProgressoAula objeto) throws SQLException {
        if (objeto == null || objeto.getProgressoId() == null)
            throw new IllegalArgumentException("Objeto e ID obrigatorios");
        String sql = "UPDATE TB_ML_PROGRESSO_AULA SET concluido = ?, dt_conclusao = ?, matricula_id = ?, aula_id = ? WHERE progresso_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, objeto.getConcluido());
            if (objeto.getDtConclusao() == null) ps.setNull(2, Types.TIMESTAMP);
            else ps.setTimestamp(2, Timestamp.valueOf(objeto.getDtConclusao()));
            if (objeto.getMatriculaId() == null) ps.setNull(3, Types.NUMERIC);
            else ps.setLong(3, objeto.getMatriculaId());
            if (objeto.getAulaId() == null) ps.setNull(4, Types.NUMERIC);
            else ps.setLong(4, objeto.getAulaId());
            ps.setLong(5, objeto.getProgressoId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean excluir(Long id) throws SQLException {
        if (id == null) throw new IllegalArgumentException("ID obrigatorio");
        String sql = "DELETE FROM TB_ML_PROGRESSO_AULA WHERE progresso_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public List<ProgressoAula> listarPorMatriculaId(Long id) throws SQLException {
        if (id == null) throw new IllegalArgumentException("ID de filtro obrigatorio");
        String sql = "SELECT * FROM TB_ML_PROGRESSO_AULA WHERE matricula_id = ? ORDER BY progresso_id";
        List<ProgressoAula> lista = new ArrayList<>();
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
        try (PreparedStatement ps = conn.prepareStatement("SELECT SEQ_ML_PROGRESSO_AULA.NEXTVAL FROM DUAL");
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getLong(1);
        }
    }

    private ProgressoAula mapear(ResultSet rs) throws SQLException {
        ProgressoAula objeto = new ProgressoAula();
        objeto.setProgressoId(rs.getLong("progresso_id"));
        objeto.setConcluido(rs.getString("concluido"));
        Timestamp dtConclusaoTs = rs.getTimestamp("dt_conclusao");
        objeto.setDtConclusao(dtConclusaoTs == null ? null : dtConclusaoTs.toLocalDateTime());
        long matriculaIdValor = rs.getLong("matricula_id");
        objeto.setMatriculaId(rs.wasNull() ? null : matriculaIdValor);
        long aulaIdValor = rs.getLong("aula_id");
        objeto.setAulaId(rs.wasNull() ? null : aulaIdValor);
        return objeto;
    }
}
