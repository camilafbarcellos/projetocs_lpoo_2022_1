package br.edu.ifsul.cc.lpoo.cs;

import br.edu.ifsul.cc.lpoo.cs.gui.JFramePrincipal;
import br.edu.ifsul.cc.lpoo.cs.gui.JMenuBarHome;
import br.edu.ifsul.cc.lpoo.cs.gui.JPanelHome;
import br.edu.ifsul.cc.lpoo.cs.gui.autenticacao.JPanelAutenticacao;
import br.edu.ifsul.cc.lpoo.cs.gui.jogador.JPanelAJogador;
import br.edu.ifsul.cc.lpoo.cs.gui.jogador.designer.JPanelDJogador;
import br.edu.ifsul.cc.lpoo.cs.model.Jogador;
import br.edu.ifsul.cc.lpoo.cs.model.dao.PersistenciaJDBC;
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
    
    private JMenuBarHome menuBar;
    
    private JPanelHome telaHome; // tela inicial

    private JPanelAJogador telaJogador; // tela de CRUD do jogador

    private JPanelDJogador telaJogadorDesigner; // tela de CRUD do jogador feita no modo designer
    
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
    
    public void fecharBD() {
        
        System.out.println("Fechando conexão com o Banco de Dados");
        getConexaoJDBC().fecharConexao();
    }
    
    public void showTela(String nomeTela) {
        // tela de jogador é subdividida em duas -> listagem e formulário CRUD
        if (nomeTela.equals("tela_jogador")) { // caso chamada a tela de Jogador
            // "sub-baralho" para alternar entre as duas cartas de telas
            telaJogador.showTela("tela_jogador_listagem"); // prevalece mostrar a listagem
            frame.showTela(nomeTela);
        } else if (nomeTela.equals("tela_jogador_designer")) { // caso chamada a tela de Jogador designer
            // "sub-baralho" para alternar entre as duas cartas de telas
            telaJogador.showTela("listagem"); // prevalece mostrar a listagem
            frame.showTela(nomeTela);
        }
        
        // método para mostrar outras telas -> Logout
        frame.showTela(nomeTela); // exibe a tela passada por parâmetro
    }
    
    public void initComponents() {

        //"caminho feliz" : passo 5
        frame = new JFramePrincipal(this); // inicializa a interface gráfica
        // this indica a própria classe Controle
        // FramePrincipal recebe uma instância de Controle

        // telaAutenticacao
        telaAutenticacao = new JPanelAutenticacao(this); // inicializa a tela

        // menuBar
        menuBar = new JMenuBarHome(this);

        // telaHome
        telaHome = new JPanelHome(this);

        // telaJogador
        telaJogador = new JPanelAJogador(this);
        
        // telaJogadorDesigner
        telaJogadorDesigner = new JPanelDJogador(this);

        // baralho de cartas de telas
        frame.addTela(telaAutenticacao, "tela_autenticacao"); // adiciona e nomeia a tela ao baralho de cartas        
        frame.addTela(telaHome, "tela_home");
        frame.addTela(telaJogador, "tela_jogador");
        frame.addTela(telaJogadorDesigner, "tela_jogador_designer");

        // carta de tela padrão
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

                JOptionPane.showMessageDialog(telaAutenticacao, "Jogador " + j.getNickname() + " autenticado com sucesso!", "Autenticação", JOptionPane.INFORMATION_MESSAGE);
                
                frame.setJMenuBar(menuBar); // adiciona o menu de barra no frame
                frame.showTela("tela_home"); // muda a tela para o painel de boas vindas (home)
                
            } else { // retorno nulo

                JOptionPane.showMessageDialog(telaAutenticacao, "Dados inválidos!", "Autenticação", JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(telaAutenticacao, "Erro ao executar a autenticação no Banco de Dados!", "Autenticação", JOptionPane.ERROR_MESSAGE);
            
        }
    }
    
}
