package br.com.movelearn.resource;
import br.com.movelearn.dao.CursoDAO;
import br.com.movelearn.model.Curso;
import br.com.movelearn.service.CursoService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;
@Path("/cursos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CursoResource {
    private final CursoDAO dao = new CursoDAO();
    private final CursoService service = new CursoService();

    @GET public List<Curso> listar() throws SQLException { return service.listarCursos(); }
    @GET @Path("/{id}") public Response buscar(@PathParam("id") Long id) throws SQLException {
        Curso curso = service.buscarCurso(id);
        return curso == null ? Response.status(404).build() : Response.ok(curso).build();
    }
    @POST public Response cadastrar(Curso curso) throws SQLException {
        curso.setCursoId(null);
        dao.salvar(curso);
        return Response.status(201).entity(curso).build();
    }
    @PUT @Path("/{id}") public Response atualizar(@PathParam("id") Long id, Curso curso) throws SQLException {
        curso.setCursoId(id);
        return dao.atualizar(curso) ? Response.ok(curso).build() : Response.status(404).build();
    }
    @DELETE @Path("/{id}") public Response excluir(@PathParam("id") Long id) throws SQLException {
        return dao.excluir(id) ? Response.noContent().build() : Response.status(404).build();
    }
    @GET @Path("/{id}/aulas") public List<br.com.movelearn.model.Aula> aulas(@PathParam("id") Long id) throws SQLException {
        return service.listarAulas(id);
    }
}
