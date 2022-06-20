package br.edu.ifsul.cc.lpoo.cs;

import br.edu.ifsul.cc.lpoo.cs.gui.JFramePrincipal;
import br.edu.ifsul.cc.lpoo.cs.gui.autenticacao.JPanelAutenticacao;
import br.edu.ifsul.cc.lpoo.cs.model.dao.PersistenciaJDBC;

/**
 *
 * @author Camila
 */
public class Controle {

    // instâncias
    private PersistenciaJDBC conexaoJDBC; // objeto referente aos dados

    private JPanelAutenticacao telaAutenticacao; // tela de autenticação

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
        // this indica a própria classe Controle
        // FramePrincipal recebe uma instância de Controle

        // telaAutenticacao
        telaAutenticacao = new JPanelAutenticacao(this); // inicializa a tela
        frame.addTela(telaAutenticacao, "tela_autenticacao"); // adiciona e nomeia a tela ao baralho de cartas
        frame.showTela("tela_autenticacao"); // busca e exibe a tela -> tela padrão

        frame.setVisible(true); // torna visível o jframe -> mostra a tela que está nele

    }

    /**
     * @return the conexaoJDBC
     */
    public PersistenciaJDBC getConexaoJDBC() {
        return conexaoJDBC;
    }

    public void autenticar(String trim, String trim0) {
        /*
            implementar o método doLogin da classe PersistenciaJDBC;
            chamar o doLogin e verificar o retorno:
            se o retorno for nulo, informar ao usuário;
            se não for nulo, apresentar a tela de boas vindas e o menu.
        */
        
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
