package br.com.movelearn.dao;

import br.com.movelearn.factory.ConnectionFactory;
import br.com.movelearn.model.Transporte;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** CRUD JDBC da tabela TB_ML_TRANSPORTE. As regras de negocio ficam nos services. */
public class TransporteDAO {

    public void salvar(Transporte objeto) throws SQLException {
        if (objeto == null) throw new IllegalArgumentException("Objeto obrigatorio");
        String sql = "INSERT INTO TB_ML_TRANSPORTE (transporte_id, nome, permite_leitura) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection()) {
            long id = proximoId(conn);
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, id);
                ps.setString(2, objeto.getNome());
                ps.setString(3, objeto.getPermiteLeitura());
                ps.executeUpdate();
            }
            objeto.setTransporteId(id);
        }
    }

    public Transporte buscarPorId(Long id) throws SQLException {
        if (id == null) throw new IllegalArgumentException("ID obrigatorio");
        String sql = "SELECT * FROM TB_ML_TRANSPORTE WHERE transporte_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    public List<Transporte> listarTodos() throws SQLException {
        String sql = "SELECT * FROM TB_ML_TRANSPORTE ORDER BY transporte_id";
        List<Transporte> lista = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public boolean atualizar(Transporte objeto) throws SQLException {
        if (objeto == null || objeto.getTransporteId() == null)
            throw new IllegalArgumentException("Objeto e ID obrigatorios");
        String sql = "UPDATE TB_ML_TRANSPORTE SET nome = ?, permite_leitura = ? WHERE transporte_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, objeto.getNome());
            ps.setString(2, objeto.getPermiteLeitura());
            ps.setLong(3, objeto.getTransporteId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean excluir(Long id) throws SQLException {
        if (id == null) throw new IllegalArgumentException("ID obrigatorio");
        String sql = "DELETE FROM TB_ML_TRANSPORTE WHERE transporte_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private long proximoId(Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT SEQ_ML_TRANSPORTE.NEXTVAL FROM DUAL");
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getLong(1);
        }
    }

    private Transporte mapear(ResultSet rs) throws SQLException {
        Transporte objeto = new Transporte();
        objeto.setTransporteId(rs.getLong("transporte_id"));
        objeto.setNome(rs.getString("nome"));
        objeto.setPermiteLeitura(rs.getString("permite_leitura"));
        return objeto;
    }
}
