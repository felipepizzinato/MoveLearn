package br.com.movelearn.service;

import br.com.movelearn.dao.AulaDAO;
import br.com.movelearn.dao.CursoDAO;
import br.com.movelearn.model.Aula;
import br.com.movelearn.model.Curso;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

public class CursoService {
    private final CursoDAO cursoDAO = new CursoDAO();
    private final AulaDAO aulaDAO = new AulaDAO();

    public List<Curso> listarCursos() throws SQLException { return cursoDAO.listarTodos(); }
    public Curso buscarCurso(Long cursoId) throws SQLException { return cursoDAO.buscarPorId(cursoId); }
    public List<Aula> listarAulas(Long cursoId) throws SQLException {
        if (cursoDAO.buscarPorId(cursoId) == null)
            throw new IllegalArgumentException("Curso nao encontrado.");
        List<Aula> aulas = aulaDAO.listarPorCursoId(cursoId);
        aulas.sort(Comparator.comparing(Aula::getOrdem));
        return aulas;
    }
}
