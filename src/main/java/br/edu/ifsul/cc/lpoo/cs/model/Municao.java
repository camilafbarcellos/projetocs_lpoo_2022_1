
package br.edu.ifsul.cc.lpoo.cs.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 *
 * @author Camila
 */
@Entity
@Table(name = "tb_municao")
@DiscriminatorValue("M")
public class Municao extends Artefato { // Herança
    @Column(nullable = false)
    private Boolean explosiva;
       
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Calibre calibre; // Enum
    
    public Municao() {
        
    }

    /**
     * @return the explosiva
     */
    public Boolean getExplosiva() {
        return explosiva;
    }

    /**
     * @param explosiva the explosiva to set
     */
    public void setExplosiva(Boolean explosiva) {
        this.explosiva = explosiva;
    }

    public Calibre getCalibre() {
        return calibre;
    }

    public void setCalibre(Calibre calibre) {
        this.calibre = calibre;
    }
    
    
}
