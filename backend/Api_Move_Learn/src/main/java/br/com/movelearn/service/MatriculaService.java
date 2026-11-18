package br.com.movelearn.service;

import br.com.movelearn.dao.CursoDAO;
import br.com.movelearn.dao.MatriculaDAO;
import br.com.movelearn.dao.UsuarioDAO;
import br.com.movelearn.model.Matricula;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class MatriculaService {
    private final MatriculaDAO dao = new MatriculaDAO();
    private final UsuarioDAO usuarios = new UsuarioDAO();
    private final CursoDAO cursos = new CursoDAO();

    public Matricula matricular(Long usuarioId, Long cursoId) throws SQLException {
        if (usuarioId == null || cursoId == null) throw new IllegalArgumentException("Usuario e curso obrigatorios.");
        if (usuarios.buscarPorId(usuarioId) == null || cursos.buscarPorId(cursoId) == null)
            throw new IllegalArgumentException("Usuario ou curso inexistente.");
        for (Matricula m : dao.listarPorUsuarioId(usuarioId)) {
            if (cursoId.equals(m.getCursoId())) return m; // Evita duplicacao de matricula.
        }
        Matricula m = new Matricula(null, usuarioId, cursoId, LocalDateTime.now(), "ATIVA");
        dao.salvar(m);
        return m;
    }
    public Matricula buscar(Long matriculaId) throws SQLException { return dao.buscarPorId(matriculaId); }
    public List<Matricula> listarPorUsuario(Long usuarioId) throws SQLException { return dao.listarPorUsuarioId(usuarioId); }
}
