
package br.edu.ifsul.cc.lpoo.cs.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Camila
 */

@Entity
@Table(name = "tb_itenscompra")
public class ItensCompra implements Serializable {
    @Id
    @SequenceGenerator(name = "seq_itenscompra", sequenceName = "seq_itenscompra_id", allocationSize = 1)
    @GeneratedValue(generator = "seq_itenscompra", strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(precision = 2, nullable = false)
    private Float quantidade;
/*
    @Column(nullable = false)
    private String quantidade;
*/

    @Column(nullable = false, precision = 2)
    private Float valor;

    @ManyToOne
    @JoinColumn(name = "artefato_id", nullable = false)
    private Artefato artefato; // Associação

    @ManyToOne
    @JoinColumn(name = "compra_id", nullable = false)
    private Compra compra; // Ref. a entidade forte da composição
    
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
    public Float getQuantidade() {
        return quantidade;
    }

    /**
     * @param quantidade the quantidade to set
     */
    public void setQuantidade(Float quantidade) {
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

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }
}
