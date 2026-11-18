
package br.com.movelearn.model;

public class Aula {

    private Long aulaId;
    private String titulo;
    private String conteudo;
    private Integer duracaoMinutos;
    private Integer ordem;
    private Long cursoId;

    public Aula() {}

    public Aula(Long aulaId, String titulo, String conteudo,
                Integer duracaoMinutos, Integer ordem, Long cursoId) {
        this.aulaId = aulaId;
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.duracaoMinutos = duracaoMinutos;
        this.ordem = ordem;
        this.cursoId = cursoId;
    }

    public Long getAulaId() {
        return aulaId;
    }

    public void setAulaId(Long aulaId) {
        this.aulaId = aulaId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Integer getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public void setDuracaoMinutos(Integer duracaoMinutos) {
        this.duracaoMinutos = duracaoMinutos;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public Long getCursoId() {
        return cursoId;
    }

    public void setCursoId(Long cursoId) {
        this.cursoId = cursoId;
    }
}
