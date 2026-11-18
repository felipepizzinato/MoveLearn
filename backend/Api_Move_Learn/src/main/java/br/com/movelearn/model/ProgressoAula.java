
package br.com.movelearn.model;

import java.time.LocalDateTime;

public class ProgressoAula {

    private Long progressoId;
    private String concluido;
    private LocalDateTime dtConclusao;
    private Long matriculaId;
    private Long aulaId;

    public ProgressoAula() {}

    public ProgressoAula(Long progressoId, String concluido,
                         LocalDateTime dtConclusao,
                         Long matriculaId, Long aulaId) {
        this.progressoId = progressoId;
        this.concluido = concluido;
        this.dtConclusao = dtConclusao;
        this.matriculaId = matriculaId;
        this.aulaId = aulaId;
    }

    public Long getProgressoId() {
        return progressoId;
    }

    public void setProgressoId(Long progressoId) {
        this.progressoId = progressoId;
    }

    public String getConcluido() {
        return concluido;
    }

    public void setConcluido(String concluido) {
        this.concluido = concluido;
    }

    public LocalDateTime getDtConclusao() {
        return dtConclusao;
    }

    public void setDtConclusao(LocalDateTime dtConclusao) {
        this.dtConclusao = dtConclusao;
    }

    public Long getMatriculaId() {
        return matriculaId;
    }

    public void setMatriculaId(Long matriculaId) {
        this.matriculaId = matriculaId;
    }

    public Long getAulaId() {
        return aulaId;
    }

    public void setAulaId(Long aulaId) {
        this.aulaId = aulaId;
    }
}
