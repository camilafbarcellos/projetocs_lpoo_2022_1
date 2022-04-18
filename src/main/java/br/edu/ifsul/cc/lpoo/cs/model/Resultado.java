
package br.edu.ifsul.cc.lpoo.cs.model;

/**
 *
 * @author Camila
 */
public class Resultado { // É uma chave primária composta
    private ResultadoID id; // @EmbeddedId (embarcada)
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
