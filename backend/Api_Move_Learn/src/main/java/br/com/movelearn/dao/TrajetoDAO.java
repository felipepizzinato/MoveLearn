package br.com.movelearn.dao;

import br.com.movelearn.factory.ConnectionFactory;
import br.com.movelearn.model.Trajeto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** CRUD JDBC da tabela TB_ML_TRAJETO. As regras de negocio ficam nos services. */
public class TrajetoDAO {

    public void salvar(Trajeto objeto) throws SQLException {
        if (objeto == null) throw new IllegalArgumentException("Objeto obrigatorio");
        String sql = "INSERT INTO TB_ML_TRAJETO (trajeto_id, nome, duracao_minutos, estudo_minutos, frequencia_semanal, usuario_id, transporte_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection()) {
            long id = proximoId(conn);
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, id);
                ps.setString(2, objeto.getNome());
                if (objeto.getDuracaoMinutos() == null) ps.setNull(3, Types.NUMERIC);
                else ps.setInt(3, objeto.getDuracaoMinutos());
                if (objeto.getEstudoMinutos() == null) ps.setNull(4, Types.NUMERIC);
                else ps.setInt(4, objeto.getEstudoMinutos());
                if (objeto.getFrequenciaSemanal() == null) ps.setNull(5, Types.NUMERIC);
                else ps.setInt(5, objeto.getFrequenciaSemanal());
                if (objeto.getUsuarioId() == null) ps.setNull(6, Types.NUMERIC);
                else ps.setLong(6, objeto.getUsuarioId());
                if (objeto.getTransporteId() == null) ps.setNull(7, Types.NUMERIC);
                else ps.setLong(7, objeto.getTransporteId());
                ps.executeUpdate();
            }
            objeto.setTrajetoId(id);
        }
    }

    public Trajeto buscarPorId(Long id) throws SQLException {
        if (id == null) throw new IllegalArgumentException("ID obrigatorio");
        String sql = "SELECT * FROM TB_ML_TRAJETO WHERE trajeto_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    public List<Trajeto> listarTodos() throws SQLException {
        String sql = "SELECT * FROM TB_ML_TRAJETO ORDER BY trajeto_id";
        List<Trajeto> lista = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public boolean atualizar(Trajeto objeto) throws SQLException {
        if (objeto == null || objeto.getTrajetoId() == null)
            throw new IllegalArgumentException("Objeto e ID obrigatorios");
        String sql = "UPDATE TB_ML_TRAJETO SET nome = ?, duracao_minutos = ?, estudo_minutos = ?, frequencia_semanal = ?, usuario_id = ?, transporte_id = ? WHERE trajeto_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, objeto.getNome());
            if (objeto.getDuracaoMinutos() == null) ps.setNull(2, Types.NUMERIC);
            else ps.setInt(2, objeto.getDuracaoMinutos());
            if (objeto.getEstudoMinutos() == null) ps.setNull(3, Types.NUMERIC);
            else ps.setInt(3, objeto.getEstudoMinutos());
            if (objeto.getFrequenciaSemanal() == null) ps.setNull(4, Types.NUMERIC);
            else ps.setInt(4, objeto.getFrequenciaSemanal());
            if (objeto.getUsuarioId() == null) ps.setNull(5, Types.NUMERIC);
            else ps.setLong(5, objeto.getUsuarioId());
            if (objeto.getTransporteId() == null) ps.setNull(6, Types.NUMERIC);
            else ps.setLong(6, objeto.getTransporteId());
            ps.setLong(7, objeto.getTrajetoId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean excluir(Long id) throws SQLException {
        if (id == null) throw new IllegalArgumentException("ID obrigatorio");
        String sql = "DELETE FROM TB_ML_TRAJETO WHERE trajeto_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public List<Trajeto> listarPorUsuarioId(Long id) throws SQLException {
        if (id == null) throw new IllegalArgumentException("ID de filtro obrigatorio");
        String sql = "SELECT * FROM TB_ML_TRAJETO WHERE usuario_id = ? ORDER BY trajeto_id";
        List<Trajeto> lista = new ArrayList<>();
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
        try (PreparedStatement ps = conn.prepareStatement("SELECT SEQ_ML_TRAJETO.NEXTVAL FROM DUAL");
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getLong(1);
        }
    }

    private Trajeto mapear(ResultSet rs) throws SQLException {
        Trajeto objeto = new Trajeto();
        objeto.setTrajetoId(rs.getLong("trajeto_id"));
        objeto.setNome(rs.getString("nome"));
        int duracaoMinutosValor = rs.getInt("duracao_minutos");
        objeto.setDuracaoMinutos(rs.wasNull() ? null : duracaoMinutosValor);
        int estudoMinutosValor = rs.getInt("estudo_minutos");
        objeto.setEstudoMinutos(rs.wasNull() ? null : estudoMinutosValor);
        int frequenciaSemanalValor = rs.getInt("frequencia_semanal");
        objeto.setFrequenciaSemanal(rs.wasNull() ? null : frequenciaSemanalValor);
        long usuarioIdValor = rs.getLong("usuario_id");
        objeto.setUsuarioId(rs.wasNull() ? null : usuarioIdValor);
        long transporteIdValor = rs.getLong("transporte_id");
        objeto.setTransporteId(rs.wasNull() ? null : transporteIdValor);
        return objeto;
    }
}
