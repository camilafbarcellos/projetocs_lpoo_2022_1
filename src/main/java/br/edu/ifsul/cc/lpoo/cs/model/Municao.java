
package br.edu.ifsul.cc.lpoo.cs.model;

/**
 *
 * @author Camila
 */
public class Municao extends Artefato { // Herança
    private Boolean explosiva;
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
