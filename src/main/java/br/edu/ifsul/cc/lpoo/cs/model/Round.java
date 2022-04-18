
package br.edu.ifsul.cc.lpoo.cs.model;

import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Camila
 */
public class Round {
    private Integer id;
    private Integer numero;
    private Calendar inicio;
    private Calendar fim;
    private List<Objetivo> objetivos; // Agregação
    private Modo modo; // Enum
    
    public Round() {
        
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
     * @return the numero
     */
    public Integer getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    /**
     * @return the inicio
     */
    public Calendar getInicio() {
        return inicio;
    }

    /**
     * @param inicio the inicio to set
     */
    public void setInicio(Calendar inicio) {
        this.inicio = inicio;
    }

    /**
     * @return the fim
     */
    public Calendar getFim() {
        return fim;
    }

    /**
     * @param fim the fim to set
     */
    public void setFim(Calendar fim) {
        this.fim = fim;
    }

    /**
     * @return the objetivos
     */
    public List<Objetivo> getObjetivos() {
        return objetivos;
    }

    /**
     * @param objetivos the objetivos to set
     */
    public void setObjetivos(List<Objetivo> objetivos) {
        this.objetivos = objetivos;
    }

    public Modo getModo() {
        return modo;
    }

    public void setModo(Modo modo) {
        this.modo = modo;
    }
    
    
}
