
package br.com.movelearn.model;

public class Curso {

    private Long cursoId;
    private String titulo;
    private String descricao;
    private String nivel;

    public Curso() {}

    public Curso(Long cursoId, String titulo,
                 String descricao, String nivel) {
        this.cursoId = cursoId;
        this.titulo = titulo;
        this.descricao = descricao;
        this.nivel = nivel;
    }

    public Long getCursoId() {
        return cursoId;
    }

    public void setCursoId(Long cursoId) {
        this.cursoId = cursoId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
}
