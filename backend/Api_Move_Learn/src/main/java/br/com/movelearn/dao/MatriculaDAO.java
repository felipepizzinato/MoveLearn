package br.com.movelearn.dao;

import br.com.movelearn.factory.ConnectionFactory;
import br.com.movelearn.model.Matricula;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** CRUD JDBC da tabela TB_ML_MATRICULA. As regras de negocio ficam nos services. */
public class MatriculaDAO {

    public void salvar(Matricula objeto) throws SQLException {
        if (objeto == null) throw new IllegalArgumentException("Objeto obrigatorio");
        String sql = "INSERT INTO TB_ML_MATRICULA (matricula_id, usuario_id, curso_id, data_matricula, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection()) {
            long id = proximoId(conn);
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, id);
                if (objeto.getUsuarioId() == null) ps.setNull(2, Types.NUMERIC);
                else ps.setLong(2, objeto.getUsuarioId());
                if (objeto.getCursoId() == null) ps.setNull(3, Types.NUMERIC);
                else ps.setLong(3, objeto.getCursoId());
                if (objeto.getDataMatricula() == null) ps.setNull(4, Types.TIMESTAMP);
                else ps.setTimestamp(4, Timestamp.valueOf(objeto.getDataMatricula()));
                ps.setString(5, objeto.getStatus());
                ps.executeUpdate();
            }
            objeto.setMatriculaId(id);
        }
    }

    public Matricula buscarPorId(Long id) throws SQLException {
        if (id == null) throw new IllegalArgumentException("ID obrigatorio");
        String sql = "SELECT * FROM TB_ML_MATRICULA WHERE matricula_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    public List<Matricula> listarTodos() throws SQLException {
        String sql = "SELECT * FROM TB_ML_MATRICULA ORDER BY matricula_id";
        List<Matricula> lista = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public boolean atualizar(Matricula objeto) throws SQLException {
        if (objeto == null || objeto.getMatriculaId() == null)
            throw new IllegalArgumentException("Objeto e ID obrigatorios");
        String sql = "UPDATE TB_ML_MATRICULA SET usuario_id = ?, curso_id = ?, data_matricula = ?, status = ? WHERE matricula_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            if (objeto.getUsuarioId() == null) ps.setNull(1, Types.NUMERIC);
            else ps.setLong(1, objeto.getUsuarioId());
            if (objeto.getCursoId() == null) ps.setNull(2, Types.NUMERIC);
            else ps.setLong(2, objeto.getCursoId());
            if (objeto.getDataMatricula() == null) ps.setNull(3, Types.TIMESTAMP);
            else ps.setTimestamp(3, Timestamp.valueOf(objeto.getDataMatricula()));
            ps.setString(4, objeto.getStatus());
            ps.setLong(5, objeto.getMatriculaId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean excluir(Long id) throws SQLException {
        if (id == null) throw new IllegalArgumentException("ID obrigatorio");
        String sql = "DELETE FROM TB_ML_MATRICULA WHERE matricula_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public List<Matricula> listarPorUsuarioId(Long id) throws SQLException {
        if (id == null) throw new IllegalArgumentException("ID de filtro obrigatorio");
        String sql = "SELECT * FROM TB_ML_MATRICULA WHERE usuario_id = ? ORDER BY matricula_id";
        List<Matricula> lista = new ArrayList<>();
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
        try (PreparedStatement ps = conn.prepareStatement("SELECT SEQ_ML_MATRICULA.NEXTVAL FROM DUAL");
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getLong(1);
        }
    }

    private Matricula mapear(ResultSet rs) throws SQLException {
        Matricula objeto = new Matricula();
        objeto.setMatriculaId(rs.getLong("matricula_id"));
        long usuarioIdValor = rs.getLong("usuario_id");
        objeto.setUsuarioId(rs.wasNull() ? null : usuarioIdValor);
        long cursoIdValor = rs.getLong("curso_id");
        objeto.setCursoId(rs.wasNull() ? null : cursoIdValor);
        Timestamp dataMatriculaTs = rs.getTimestamp("data_matricula");
        objeto.setDataMatricula(dataMatriculaTs == null ? null : dataMatriculaTs.toLocalDateTime());
        objeto.setStatus(rs.getString("status"));
        return objeto;
    }
}
