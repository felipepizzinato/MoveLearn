package br.com.movelearn.service;

import br.com.movelearn.dao.AulaDAO;
import br.com.movelearn.dao.MatriculaDAO;
import br.com.movelearn.dao.ProgressoAulaDAO;
import br.com.movelearn.model.Aula;
import br.com.movelearn.model.Matricula;
import br.com.movelearn.model.ProgressoAula;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProgressoService {
    private final ProgressoAulaDAO progressos = new ProgressoAulaDAO();
    private final MatriculaDAO matriculas = new MatriculaDAO();
    private final AulaDAO aulas = new AulaDAO();

    public ProgressoAula concluirAula(Long matriculaId, Long aulaId) throws SQLException {
        Matricula m = matriculas.buscarPorId(matriculaId);
        Aula a = aulas.buscarPorId(aulaId);
        if (m == null || a == null) throw new IllegalArgumentException("Matricula ou aula inexistente.");
        if (!m.getCursoId().equals(a.getCursoId()))
            throw new IllegalArgumentException("Aula nao pertence ao curso da matricula.");
        if (!"ATIVA".equals(m.getStatus())) throw new IllegalArgumentException("Matricula nao esta ativa.");
        for (ProgressoAula p : progressos.listarPorMatriculaId(matriculaId)) {
            if (aulaId.equals(p.getAulaId())) {
                if (!"S".equalsIgnoreCase(p.getConcluido())) {
                    p.setConcluido("S");
                    p.setDtConclusao(LocalDateTime.now());
                    progressos.atualizar(p);
                }
                return p;
            }
        }
        ProgressoAula novo = new ProgressoAula(null, "S", LocalDateTime.now(), matriculaId, aulaId);
        progressos.salvar(novo);
        return novo;
    }

    public double percentualConcluido(Long matriculaId) throws SQLException {
        Matricula m = matriculas.buscarPorId(matriculaId);
        if (m == null) throw new IllegalArgumentException("Matricula inexistente.");
        List<Aula> todas = aulas.listarPorCursoId(m.getCursoId());
        if (todas.isEmpty()) return 0.0;
        Set<Long> ids = new HashSet<>();
        for (ProgressoAula p : progressos.listarPorMatriculaId(matriculaId)) {
            if ("S".equalsIgnoreCase(p.getConcluido())) ids.add(p.getAulaId());
        }
        long feitas = todas.stream().filter(a -> ids.contains(a.getAulaId())).count();
        return 100.0 * feitas / todas.size();
    }
}
