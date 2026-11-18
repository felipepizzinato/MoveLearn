
package br.com.movelearn.model;

public class Trajeto {

    private Long trajetoId;
    private String nome;
    private Integer duracaoMinutos;
    private Integer estudoMinutos;
    private Integer frequenciaSemanal;
    private Long usuarioId;
    private Long transporteId;

    public Trajeto() {}

    public Trajeto(Long trajetoId, String nome,
                   Integer duracaoMinutos, Integer estudoMinutos,
                   Integer frequenciaSemanal, Long usuarioId,
                   Long transporteId) {
        this.trajetoId = trajetoId;
        this.nome = nome;
        this.duracaoMinutos = duracaoMinutos;
        this.estudoMinutos = estudoMinutos;
        this.frequenciaSemanal = frequenciaSemanal;
        this.usuarioId = usuarioId;
        this.transporteId = transporteId;
    }

    public Long getTrajetoId() {
        return trajetoId;
    }

    public void setTrajetoId(Long trajetoId) {
        this.trajetoId = trajetoId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public void setDuracaoMinutos(Integer duracaoMinutos) {
        this.duracaoMinutos = duracaoMinutos;
    }

    public Integer getEstudoMinutos() {
        return estudoMinutos;
    }

    public void setEstudoMinutos(Integer estudoMinutos) {
        this.estudoMinutos = estudoMinutos;
    }

    public Integer getFrequenciaSemanal() {
        return frequenciaSemanal;
    }

    public void setFrequenciaSemanal(Integer frequenciaSemanal) {
        this.frequenciaSemanal = frequenciaSemanal;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getTransporteId() {
        return transporteId;
    }

    public void setTransporteId(Long transporteId) {
        this.transporteId = transporteId;
    }
}
