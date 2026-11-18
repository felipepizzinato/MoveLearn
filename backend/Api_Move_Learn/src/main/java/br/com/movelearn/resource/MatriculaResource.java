package br.com.movelearn.resource;
import br.com.movelearn.model.Matricula;
import br.com.movelearn.service.MatriculaService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
@Path("/matriculas")
@Produces(MediaType.APPLICATION_JSON) @Consumes(MediaType.APPLICATION_JSON)
public class MatriculaResource {
    private final MatriculaService service = new MatriculaService();
    @GET public List<Matricula> listar(@QueryParam("usuarioId") Long usuarioId) throws SQLException {
        if (usuarioId == null) throw new BadRequestException("Informe usuarioId");
        return service.listarPorUsuario(usuarioId);
    }
    @GET @Path("/{id}") public Response buscar(@PathParam("id") Long id) throws SQLException {
        Matricula m = service.buscar(id);
        return m == null ? Response.status(404).build() : Response.ok(m).build();
    }
    @POST public Matricula matricular(Map<String,Long> dados) throws SQLException {
        if (dados == null || dados.get("usuarioId") == null || dados.get("cursoId") == null)
            throw new BadRequestException("Informe usuarioId e cursoId");
        try { return service.matricular(dados.get("usuarioId"), dados.get("cursoId")); }
        catch (IllegalArgumentException e) { throw new BadRequestException(e.getMessage()); }
    }
}
