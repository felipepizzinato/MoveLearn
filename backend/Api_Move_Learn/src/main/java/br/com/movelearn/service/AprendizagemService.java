package br.com.movelearn.service;

import br.com.movelearn.dao.*;
import br.com.movelearn.model.*;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AprendizagemService {
    private final AulaDAO aulas = new AulaDAO();
    private final MatriculaDAO matriculas = new MatriculaDAO();
    private final TrajetoDAO trajetos = new TrajetoDAO();
    private final TransporteDAO transportes = new TransporteDAO();
    private final ProgressoAulaDAO progressos = new ProgressoAulaDAO();

    /** Sugere a proxima aula NA ORDEM do curso, desde que caiba no trajeto. */
    public Aula recomendarProximaAula(Long matriculaId, Long trajetoId) throws SQLException {
        Matricula m = matriculas.buscarPorId(matriculaId);
        Trajeto t = trajetos.buscarPorId(trajetoId);
        if (m == null || t == null) throw new IllegalArgumentException("Matricula ou trajeto inexistente.");
        if (!m.getUsuarioId().equals(t.getUsuarioId()))
            throw new IllegalArgumentException("A matricula e o trajeto devem pertencer ao mesmo usuario.");
        if (!"ATIVA".equals(m.getStatus())) return null;
        Transporte transporte = transportes.buscarPorId(t.getTransporteId());
        if (transporte == null || !"S".equalsIgnoreCase(transporte.getPermiteLeitura()))
            return null; // MVP oferece somente aulas textuais.

        Set<Long> concluidas = new HashSet<>();
        for (ProgressoAula p : progressos.listarPorMatriculaId(matriculaId)) {
            if ("S".equalsIgnoreCase(p.getConcluido())) concluidas.add(p.getAulaId());
        }
        List<Aula> sequencia = aulas.listarPorCursoId(m.getCursoId());
        sequencia.sort(Comparator.comparing(Aula::getOrdem));
        for (Aula a : sequencia) {
            if (!concluidas.contains(a.getAulaId())) {
                return a.getDuracaoMinutos() <= t.getEstudoMinutos() ? a : null;
            }
        }
        return null; // Curso concluido, ou nenhuma aula cadastrada.
    }
}
