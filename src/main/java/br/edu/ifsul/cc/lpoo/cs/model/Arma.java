
package br.edu.ifsul.cc.lpoo.cs.model;

import java.util.List;

/**
 *
 * @author Camila
 */
public class Arma extends Artefato { // Herança
    private Float comprimento_cano;
    private Tipo tipo; // Enum
    private List<Municao> municoes; // Agregação
    
    public Arma() {
        
    }

    /**
     * @return the comprimento_cano
     */
    public Float getComprimento_cano() {
        return comprimento_cano;
    }

    /**
     * @param comprimento_cano the comprimento_cano to set
     */
    public void setComprimento_cano(Float comprimento_cano) {
        this.comprimento_cano = comprimento_cano;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the municoes
     */
    public List<Municao> getMunicoes() {
        return municoes;
    }

    /**
     * @param municoes the municoes to set
     */
    public void setMunicoes(List<Municao> municoes) {
        this.municoes = municoes;
    }

      
    
}
