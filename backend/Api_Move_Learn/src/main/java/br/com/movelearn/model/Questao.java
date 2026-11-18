
package br.com.movelearn.model;

public class Questao {

    private Long questaoId;
    private String enunciado;
    private Integer ordem;
    private Long aulaId;

    public Questao() {}

    public Questao(Long questaoId, String enunciado,
                   Integer ordem, Long aulaId) {
        this.questaoId = questaoId;
        this.enunciado = enunciado;
        this.ordem = ordem;
        this.aulaId = aulaId;
    }

    public Long getQuestaoId() {
        return questaoId;
    }

    public void setQuestaoId(Long questaoId) {
        this.questaoId = questaoId;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public Long getAulaId() {
        return aulaId;
    }

    public void setAulaId(Long aulaId) {
        this.aulaId = aulaId;
    }
}
