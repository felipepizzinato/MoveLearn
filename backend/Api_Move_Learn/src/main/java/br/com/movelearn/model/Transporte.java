
package br.com.movelearn.model;

public class Transporte {

    private Long transporteId;
    private String nome;
    private String permiteLeitura;

    public Transporte() {}

    public Transporte(Long transporteId, String nome,
                      String permiteLeitura) {
        this.transporteId = transporteId;
        this.nome = nome;
        this.permiteLeitura = permiteLeitura;
    }

    public Long getTransporteId() {
        return transporteId;
    }

    public void setTransporteId(Long transporteId) {
        this.transporteId = transporteId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPermiteLeitura() {
        return permiteLeitura;
    }

    public void setPermiteLeitura(String permiteLeitura) {
        this.permiteLeitura = permiteLeitura;
    }
}
