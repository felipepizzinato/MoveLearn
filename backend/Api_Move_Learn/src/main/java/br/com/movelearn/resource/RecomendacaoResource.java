package br.com.movelearn.resource;
import br.com.movelearn.model.Aula;
import br.com.movelearn.service.AprendizagemService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
@Path("/recomendacoes")
@Produces(MediaType.APPLICATION_JSON)
public class RecomendacaoResource {
    private final AprendizagemService service = new AprendizagemService();
    @GET public Response recomendar(@QueryParam("matriculaId") Long matriculaId,
                                    @QueryParam("trajetoId") Long trajetoId) throws SQLException {
        if (matriculaId == null || trajetoId == null)
            throw new BadRequestException("Informe matriculaId e trajetoId");
        Aula aula = service.recomendarProximaAula(matriculaId, trajetoId);
        return aula == null ? Response.noContent().build() : Response.ok(aula).build();
    }
}
