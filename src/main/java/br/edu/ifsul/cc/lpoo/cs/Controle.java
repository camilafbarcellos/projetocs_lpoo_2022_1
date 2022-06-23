package br.edu.ifsul.cc.lpoo.cs;

import br.edu.ifsul.cc.lpoo.cs.gui.JFramePrincipal;
import br.edu.ifsul.cc.lpoo.cs.gui.autenticacao.JPanelAutenticacao;
import br.edu.ifsul.cc.lpoo.cs.model.Jogador;
import br.edu.ifsul.cc.lpoo.cs.model.dao.PersistenciaJDBC;
import java.sql.SQLException;
import javax.swing.JOptionPane;

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

    public void autenticar(String nickname, String senha) {
        /*
            implementar o método doLogin da classe PersistenciaJDBC;
            chamar o doLogin e verificar o retorno:
            se o retorno for nulo, informar ao usuário;
            se não for nulo, apresentar a tela de boas vindas e o menu.
         */

        try {

            Jogador j = conexaoJDBC.doLogin(nickname, senha); // chama o método doLogin

            if (j != null) { // retorno não nulo

                JOptionPane.showMessageDialog(telaAutenticacao, "Validação concluída com sucesso!", "Autenticação", JOptionPane.INFORMATION_MESSAGE);

            } else { // retorno nulo

                JOptionPane.showMessageDialog(telaAutenticacao, "Dados inválidos!", "Autenticação", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(telaAutenticacao, "Erro ao executar a autenticação no Banco de Dados!", "Autenticação", JOptionPane.ERROR_MESSAGE);

        }
    }

}
