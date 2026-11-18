package br.com.movelearn.resource;
import br.com.movelearn.dao.AulaDAO;
import br.com.movelearn.dao.QuestaoDAO;
import br.com.movelearn.dao.AlternativaDAO;
import br.com.movelearn.model.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
@Path("/aulas")
@Produces(MediaType.APPLICATION_JSON) @Consumes(MediaType.APPLICATION_JSON)
public class AulaResource {
    private final AulaDAO dao = new AulaDAO();
    private final QuestaoDAO questoes = new QuestaoDAO();
    private final AlternativaDAO alternativas = new AlternativaDAO();
    @GET public List<Aula> listar(@QueryParam("cursoId") Long cursoId) throws SQLException {
        return cursoId == null ? dao.listarTodos() : dao.listarPorCursoId(cursoId);
    }
    @GET @Path("/{id}") public Response buscar(@PathParam("id") Long id) throws SQLException {
        Aula aula = dao.buscarPorId(id);
        return aula == null ? Response.status(404).build() : Response.ok(aula).build();
    }
    @POST public Response cadastrar(Aula aula) throws SQLException {
        aula.setAulaId(null); dao.salvar(aula);
        return Response.status(201).entity(aula).build();
    }
    @PUT @Path("/{id}") public Response atualizar(@PathParam("id") Long id, Aula aula) throws SQLException {
        aula.setAulaId(id);
        return dao.atualizar(aula) ? Response.ok(aula).build() : Response.status(404).build();
    }
    @DELETE @Path("/{id}") public Response excluir(@PathParam("id") Long id) throws SQLException {
        return dao.excluir(id) ? Response.noContent().build() : Response.status(404).build();
    }
    @GET @Path("/{id}/questoes") public List<Map<String,Object>> listarQuestoes(@PathParam("id") Long aulaId) throws SQLException {
        // Envia somente dados publicos, sem entregar qual alternativa e correta.
        List<Map<String,Object>> resultado = new java.util.ArrayList<>();
        for (Questao q : questoes.listarPorAulaId(aulaId)) {
            List<Map<String,Object>> opcoes = new java.util.ArrayList<>();
            for (Alternativa a : alternativas.listarPorQuestaoId(q.getQuestaoId())) {
                opcoes.add(Map.of("alternativaId", a.getAlternativaId(), "texto", a.getTexto()));
            }
            resultado.add(Map.of("questaoId", q.getQuestaoId(), "enunciado", q.getEnunciado(), "alternativas", opcoes));
        }
        return resultado;
    }
    @POST @Path("/{id}/respostas") public Response corrigir(@PathParam("id") Long aulaId, Map<String,Long> resposta) throws SQLException {
        Long questaoId = resposta == null ? null : resposta.get("questaoId");
        Long alternativaId = resposta == null ? null : resposta.get("alternativaId");
        Questao q = questaoId == null ? null : questoes.buscarPorId(questaoId);
        Alternativa a = alternativaId == null ? null : alternativas.buscarPorId(alternativaId);
        if (q == null || a == null || !aulaId.equals(q.getAulaId()) || !questaoId.equals(a.getQuestaoId()))
            throw new BadRequestException("Questao ou alternativa invalida");
        return Response.ok(Map.of("correta", "S".equals(a.getCorreta()))).build();
    }
}
