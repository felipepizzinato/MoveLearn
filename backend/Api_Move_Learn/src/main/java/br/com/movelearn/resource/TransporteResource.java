package br.com.movelearn.resource;
import br.com.movelearn.dao.TransporteDAO;
import br.com.movelearn.model.Transporte;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;
@Path("/transportes")
@Produces(MediaType.APPLICATION_JSON) @Consumes(MediaType.APPLICATION_JSON)
public class TransporteResource {
    private final TransporteDAO dao = new TransporteDAO();
    @GET public List<Transporte> listar() throws SQLException { return dao.listarTodos(); }
    @GET @Path("/{id}") public Response buscar(@PathParam("id") Long id) throws SQLException {
        Transporte t = dao.buscarPorId(id);
        return t == null ? Response.status(404).build() : Response.ok(t).build();
    }
    @POST public Response cadastrar(Transporte t) throws SQLException {
        t.setTransporteId(null); dao.salvar(t);
        return Response.status(201).entity(t).build();
    }
    @PUT @Path("/{id}") public Response atualizar(@PathParam("id") Long id, Transporte t) throws SQLException {
        t.setTransporteId(id);
        return dao.atualizar(t) ? Response.ok(t).build() : Response.status(404).build();
    }
    @DELETE @Path("/{id}") public Response excluir(@PathParam("id") Long id) throws SQLException {
        return dao.excluir(id) ? Response.noContent().build() : Response.status(404).build();
    }
}
