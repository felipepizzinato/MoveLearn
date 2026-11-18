package br.com.movelearn.service;

import br.com.movelearn.dao.TrajetoDAO;
import br.com.movelearn.dao.TransporteDAO;
import br.com.movelearn.dao.UsuarioDAO;
import br.com.movelearn.model.Trajeto;
import br.com.movelearn.model.Transporte;
import java.sql.SQLException;
import java.util.List;

public class TrajetoService {
    private final TrajetoDAO dao = new TrajetoDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final TransporteDAO transporteDAO = new TransporteDAO();

    public Trajeto cadastrar(Trajeto trajeto) throws SQLException {
        validar(trajeto);
        dao.salvar(trajeto);
        return trajeto;
    }
    public boolean atualizar(Trajeto trajeto) throws SQLException {
        if (trajeto == null || trajeto.getTrajetoId() == null)
            throw new IllegalArgumentException("ID do trajeto obrigatorio.");
        validar(trajeto);
        return dao.atualizar(trajeto);
    }
    public Trajeto buscar(Long id) throws SQLException { return dao.buscarPorId(id); }
    public List<Trajeto> listarPorUsuario(Long usuarioId) throws SQLException { return dao.listarPorUsuarioId(usuarioId); }
    public boolean excluir(Long id) throws SQLException { return dao.excluir(id); }
    public int metaSemanalMinutos(Trajeto trajeto) {
        if (trajeto == null || trajeto.getEstudoMinutos() == null || trajeto.getFrequenciaSemanal() == null)
            throw new IllegalArgumentException("Tempo e frequencia obrigatorios.");
        return trajeto.getEstudoMinutos() * trajeto.getFrequenciaSemanal();
    }
    public void validar(Trajeto t) throws SQLException {
        if (t == null || t.getNome() == null || t.getNome().isBlank()
                || t.getDuracaoMinutos() == null || t.getEstudoMinutos() == null
                || t.getFrequenciaSemanal() == null || t.getUsuarioId() == null || t.getTransporteId() == null)
            throw new IllegalArgumentException("Dados do trajeto incompletos.");
        if (t.getDuracaoMinutos() <= 0 || t.getEstudoMinutos() <= 0 || t.getEstudoMinutos() > t.getDuracaoMinutos()
                || t.getFrequenciaSemanal() < 1 || t.getFrequenciaSemanal() > 7)
            throw new IllegalArgumentException("Duracao, tempo de estudo ou frequencia invalidos.");
        if (usuarioDAO.buscarPorId(t.getUsuarioId()) == null)
            throw new IllegalArgumentException("Usuario inexistente.");
        Transporte transporte = transporteDAO.buscarPorId(t.getTransporteId());
        if (transporte == null) throw new IllegalArgumentException("Transporte inexistente.");
        if (!"S".equalsIgnoreCase(transporte.getPermiteLeitura()))
            throw new IllegalArgumentException("Este transporte nao permite aulas de leitura. MVP somente texto.");
    }
}
