
package br.edu.ifsul.cc.lpoo.cs.model;

/**
 *
 * @author Camila
 */
public class ItensCompra {
    private Integer id;
    private String quantidade;
    private Float valor;
    private Artefato artefato; // Associação
    
    public ItensCompra() {
        
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the quantidade
     */
    public String getQuantidade() {
        return quantidade;
    }

    /**
     * @param quantidade the quantidade to set
     */
    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * @return the valor
     */
    public Float getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(Float valor) {
        this.valor = valor;
    }

    /**
     * @return the artefato
     */
    public Artefato getArtefato() {
        return artefato;
    }

    /**
     * @param artefato the artefato to set
     */
    public void setArtefato(Artefato artefato) {
        this.artefato = artefato;
    }
}
