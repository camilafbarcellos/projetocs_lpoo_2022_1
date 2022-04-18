
package br.edu.ifsul.cc.lpoo.cs.model;

/**
 *
 * @author Camila
 */
public class Resultado {
    private ResultadoID id; // @EmbeddedId
    
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
    
    
}
