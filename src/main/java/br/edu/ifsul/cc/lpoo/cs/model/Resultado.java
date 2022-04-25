
package br.edu.ifsul.cc.lpoo.cs.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 *
 * @author Camila
 */

@Entity()
@Table(name = "tb_resulado")
public class Resultado implements Serializable { // É uma chave primária composta

    @EmbeddedId
    private ResultadoID id; // @EmbeddedId (embarcada)     
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status; // Enum
    
    public Resultado() {
        
    }

    /**
     * @return the id
     */
    public ResultadoID getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(ResultadoID id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    
    
}
