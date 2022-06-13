package br.edu.ifsul.cc.lpoo.cs.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Camila
 */
@Entity
@Table(name = "tb_compra")
public class Compra implements Serializable {

    @Id
    @SequenceGenerator(name = "seq_compra", sequenceName = "seq_compra_id", allocationSize = 1)
    @GeneratedValue(generator = "seq_compra", strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar data_compra;

    @Column(precision = 2, nullable = true)
    private Float total;

    @OneToMany(mappedBy = "compra") // variável compra (Compra) em ItensCompra
    private List<ItensCompra> itens; // Composição

    @ManyToOne
    @JoinColumn(name = "jogador_nickname", nullable = false)
    private Jogador jogador; // Compra é ent. fraca na composição e ref. a forte

    public Compra() {

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
     * @return the data_compra
     */
    public Calendar getData_compra() {
        return data_compra;
    }

    /**
     * @param data_compra the data_compra to set
     */
    public void setData_compra(Calendar data_compra) {
        this.data_compra = data_compra;
    }

    /**
     * @return the total
     */
    public Float getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(Float total) {
        this.total = total;
    }

    /**
     * @return the itens
     */
    public List<ItensCompra> getItens() {
        return itens;
    }

    /**
     * @param itens the itens to set
     */
    public void setItens(List<ItensCompra> itens) {
        this.itens = itens;
    }

    public void setItem(ItensCompra item) {
        if (this.itens == null) {
            this.itens = new ArrayList();
        }

        this.itens.add(item);
    }

    public Jogador getJogador() {
        return jogador;
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }
}
