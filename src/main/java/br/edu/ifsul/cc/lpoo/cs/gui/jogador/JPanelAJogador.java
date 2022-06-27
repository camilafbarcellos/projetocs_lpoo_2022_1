
package br.edu.ifsul.cc.lpoo.cs.gui.jogador;

import br.edu.ifsul.cc.lpoo.cs.Controle;
import java.awt.CardLayout;
import javax.swing.JPanel;

/**
 *
 * @author Camila
 */
public class JPanelAJogador extends JPanel {
    
    private CardLayout cardLayout; // estrutura de cartas para organizar formulario e listagem
    private Controle controle;
    
    // cartas do sub-baralho de Jogador
    private JPanelAJogadorFormulario formulario;
    private JPanelAJogadorListagem listagem;
    
    public JPanelAJogador(Controle controle){
        
        this.controle = controle;
        initComponents();
    }
    
    private void initComponents(){
        
        // inicializa o baralho
        cardLayout = new CardLayout();
        this.setLayout(cardLayout);
        
        // inicializa as cartas
        formulario = new JPanelAJogadorFormulario(this, controle);
        listagem = new JPanelAJogadorListagem(this, controle);
        
        // coloca as cartas no baralho
        this.add(getFormulario(), "tela_jogador_formulario");
        this.add(listagem, "tela_jogador_listagem");
                
    }
    
    // metodo para mostrar as cartas do baralho de Jogador
    public void showTela(String nomeTela){
        
        if(nomeTela.equals("tela_jogador_listagem")){
            
            listagem.populaTable();
            
        }else if(nomeTela.equals("tela_jogador_formulario")){
            
            getFormulario().populaComboEndereco();
            getFormulario().populaComboPatente();
        }
        
        cardLayout.show(this, nomeTela);
        
    }

    /**
     * @return the controle
     */
    public Controle getControle() {
        return controle;
    }

    /**
     * @return the formulario
     */
    public JPanelAJogadorFormulario getFormulario() {
        return formulario;
    }
    
    
    
}