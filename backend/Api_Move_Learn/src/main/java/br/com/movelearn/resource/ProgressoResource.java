package br.com.movelearn.resource;
import br.com.movelearn.model.ProgressoAula;
import br.com.movelearn.service.ProgressoService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.Map;
@Path("/progressos")
@Produces(MediaType.APPLICATION_JSON) @Consumes(MediaType.APPLICATION_JSON)
public class ProgressoResource {
    private final ProgressoService service = new ProgressoService();
    @POST public ProgressoAula concluir(Map<String,Long> dados) throws SQLException {
        if (dados == null || dados.get("matriculaId") == null || dados.get("aulaId") == null)
            throw new BadRequestException("Informe matriculaId e aulaId");
        try { return service.concluirAula(dados.get("matriculaId"), dados.get("aulaId")); }
        catch (IllegalArgumentException e) { throw new BadRequestException(e.getMessage()); }
    }
    @GET @Path("/percentual") public Map<String,Double> percentual(@QueryParam("matriculaId") Long id) throws SQLException {
        if (id == null) throw new BadRequestException("Informe matriculaId");
        return Map.of("percentual", service.percentualConcluido(id));
    }
}
