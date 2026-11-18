
package br.com.movelearn.model;

public class Alternativa {

    private Long alternativaId;
    private String texto;
    private String correta;
    private Long questaoId;

    public Alternativa() {}

    public Alternativa(Long alternativaId, String texto,
                       String correta, Long questaoId) {
        this.alternativaId = alternativaId;
        this.texto = texto;
        this.correta = correta;
        this.questaoId = questaoId;
    }

    public Long getAlternativaId() {
        return alternativaId;
    }

    public void setAlternativaId(Long alternativaId) {
        this.alternativaId = alternativaId;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getCorreta() {
        return correta;
    }

    public void setCorreta(String correta) {
        this.correta = correta;
    }

    public Long getQuestaoId() {
        return questaoId;
    }

    public void setQuestaoId(Long questaoId) {
        this.questaoId = questaoId;
    }
}
