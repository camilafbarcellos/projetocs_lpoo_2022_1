package br.edu.ifsul.cc.lpoo.cs;

import br.edu.ifsul.cc.lpoo.cs.gui.JFramePrincipal;
import br.edu.ifsul.cc.lpoo.cs.model.dao.PersistenciaJDBC;

/**
 *
 * @author Camila
 */
public class Controle {

    // instâncias

    private PersistenciaJDBC conexaoJDBC; // objeto referente aos dados

    private JFramePrincipal frame; // frame principal da minha aplicação gráfica

    public Controle() {
        // construtor em branco
    }

    public boolean conectarBD() throws Exception {

        conexaoJDBC = new PersistenciaJDBC(); // inicializa o construtor da conexão JDBC

        if (getConexaoJDBC() != null) { // conexão true

            return getConexaoJDBC().conexaoAberta();
        }

        return false;
    }

    public void initComponents() {

        //"caminho feliz" : passo 5
        frame = new JFramePrincipal(this); // inicializa a interface gráfica

        frame.setVisible(true); // torna visível o jframe -> mostra

    }

    /**
     * @return the conexaoJDBC
     */
    public PersistenciaJDBC getConexaoJDBC() {
        return conexaoJDBC;
    }

}
