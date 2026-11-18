
package br.com.movelearn.model;

import java.time.LocalDateTime;

public class Matricula {

    private Long matriculaId;
    private Long usuarioId;
    private Long cursoId;
    private LocalDateTime dataMatricula;
    private String status;

    public Matricula() {}

    public Matricula(Long matriculaId, Long usuarioId,
                     Long cursoId, LocalDateTime dataMatricula,
                     String status) {
        this.matriculaId = matriculaId;
        this.usuarioId = usuarioId;
        this.cursoId = cursoId;
        this.dataMatricula = dataMatricula;
        this.status = status;
    }

    public Long getMatriculaId() {
        return matriculaId;
    }

    public void setMatriculaId(Long matriculaId) {
        this.matriculaId = matriculaId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getCursoId() {
        return cursoId;
    }

    public void setCursoId(Long cursoId) {
        this.cursoId = cursoId;
    }

    public LocalDateTime getDataMatricula() {
        return dataMatricula;
    }

    public void setDataMatricula(LocalDateTime dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
