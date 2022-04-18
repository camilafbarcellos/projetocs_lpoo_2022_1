
package br.edu.ifsul.cc.lpoo.cs.model;

/**
 *
 * @author Camila
 */
public class ResultadoID {
    private Objetivo objetivo; // Associação
    private Round round; // Associação
    private Resultado resultado; // Associação
    
    public ResultadoID() {
        
    }

    /**
     * @return the objetivo
     */
    public Objetivo getObjetivo() {
        return objetivo;
    }

    /**
     * @param objetivo the objetivo to set
     */
    public void setObjetivo(Objetivo objetivo) {
        this.objetivo = objetivo;
    }

    /**
     * @return the round
     */
    public Round getRound() {
        return round;
    }

    /**
     * @param round the round to set
     */
    public void setRound(Round round) {
        this.round = round;
    }

    /**
     * @return the resultado
     */
    public Resultado getResultado() {
        return resultado;
    }

    /**
     * @param resultado the resultado to set
     */
    public void setResultado(Resultado resultado) {
        this.resultado = resultado;
    }
    
    
}
