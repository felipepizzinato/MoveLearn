package br.com.movelearn.resource;
import br.com.movelearn.model.Usuario;
import br.com.movelearn.service.UsuarioService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioResource {
    private final UsuarioService service = new UsuarioService();
    // Nesta etapa: consulta sem expor senhaHash. Cadastro fica no service / teste local.
    @GET public List<Map<String,Object>> listar() throws SQLException {
        return service.listar().stream().map(this::dadosPublicos).toList();
    }
    @GET @Path("/{id}") public Response buscar(@PathParam("id") Long id) throws SQLException {
        Usuario u = service.buscar(id);
        return u == null ? Response.status(404).build() : Response.ok(dadosPublicos(u)).build();
    }
    private Map<String,Object> dadosPublicos(Usuario u) {
        return Map.of("usuarioId", u.getUsuarioId(), "nome", u.getNome(), "email", u.getEmail());
    }
}
