package br.com.movelearn.resource;
import br.com.movelearn.model.Trajeto;
import br.com.movelearn.service.TrajetoService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
@Path("/trajetos")
@Produces(MediaType.APPLICATION_JSON) @Consumes(MediaType.APPLICATION_JSON)
public class TrajetoResource {
    private final TrajetoService service = new TrajetoService();
    @GET public List<Trajeto> listar(@QueryParam("usuarioId") Long usuarioId) throws SQLException {
        if (usuarioId == null) throw new BadRequestException("Informe usuarioId");
        return service.listarPorUsuario(usuarioId);
    }
    @GET @Path("/{id}") public Response buscar(@PathParam("id") Long id) throws SQLException {
        Trajeto t = service.buscar(id);
        return t == null ? Response.status(404).build() : Response.ok(t).build();
    }
    @POST public Response cadastrar(Trajeto t) throws SQLException {
        try { return Response.status(201).entity(service.cadastrar(t)).build(); }
        catch (IllegalArgumentException e) { throw new BadRequestException(e.getMessage()); }
    }
    @PUT @Path("/{id}") public Response atualizar(@PathParam("id") Long id, Trajeto t) throws SQLException {
        if (t == null) throw new BadRequestException("Trajeto obrigatorio");
        t.setTrajetoId(id);
        try { return service.atualizar(t) ? Response.ok(t).build() : Response.status(404).build(); }
        catch (IllegalArgumentException e) { throw new BadRequestException(e.getMessage()); }
    }
    @DELETE @Path("/{id}") public Response excluir(@PathParam("id") Long id) throws SQLException {
        return service.excluir(id) ? Response.noContent().build() : Response.status(404).build();
    }
    @GET @Path("/{id}/meta") public Response meta(@PathParam("id") Long id) throws SQLException {
        Trajeto t = service.buscar(id);
        if (t == null) return Response.status(404).build();
        return Response.ok(Map.of("minutosSemanais", service.metaSemanalMinutos(t))).build();
    }
}
