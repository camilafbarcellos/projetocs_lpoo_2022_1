package br.edu.ifsul.cc.lpoo.cs.gui.autenticacao;

import br.edu.ifsul.cc.lpoo.cs.Controle;
import br.edu.ifsul.cc.lpoo.cs.util.Util;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 *
 * @author 20202PF.CC0003
 */
public class JPanelAutenticacao extends JPanel implements ActionListener { // herda JPanel e implementa ActionListener
    // ActionListener é uma interface que escuta eventos de botões

    private Controle controle;
    private GridBagLayout gridLayout; // organiza por linhas e colunas
    private GridBagConstraints posicionador;

    private JLabel lblNickname; // rótulo de nickname
    private JLabel lblSenha; // rótulo de senha
    private JTextField txfNickname; // caixa de texto de nickname
    private JPasswordField psfSenha; // caixa de senha de senha
    private JButton btnLogar; // botão de logar
    private Border defaultBorder; // borda da caixa

    //construtor da classe que recebe um parametro.
    public JPanelAutenticacao(Controle controle) {

        this.controle = controle;
        initComponents();
    }

    private void initComponents() {

        gridLayout = new GridBagLayout(); //inicializando o gerenciador de layout
        this.setLayout(gridLayout); //definie o gerenciador para este painel.

        lblNickname = new JLabel("Nickname:"); // texto no rótulo de nickname
        lblNickname.setToolTipText("lblNickname"); //acessibilidade
        posicionador = new GridBagConstraints();
        posicionador.gridy = 0; //policao da linha (vertical)
        posicionador.gridx = 0; // posição da coluna (horizontal)
        this.add(lblNickname, posicionador); //o add adiciona o rotulo no painel

        txfNickname = new JTextField(10);
        txfNickname.setFocusable(true); //acessibilidade - foco do cursor começa em nickname   
        txfNickname.setToolTipText("txfNickname"); //acessibilidade
        Util.considerarEnterComoTab(txfNickname);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 0; //policao da linha (vertical)
        posicionador.gridx = 1; // posição da coluna (horizontal)
        defaultBorder = txfNickname.getBorder();
        this.add(txfNickname, posicionador); //o add adiciona o rotulo no painel        

        lblSenha = new JLabel("Senha:"); // texto no rótulo de senha
        lblSenha.setToolTipText("lblSenha"); //acessibilidade        
        posicionador = new GridBagConstraints();
        posicionador.gridy = 1; //policao da linha (vertical)
        posicionador.gridx = 0; // posição da coluna (horizontal)
        this.add(lblSenha, posicionador); //o add adiciona o rotulo no painel

        psfSenha = new JPasswordField(10);
        psfSenha.setFocusable(true); //acessibilidade    
        psfSenha.setToolTipText("psfSenha"); //acessibilidade  
        Util.considerarEnterComoTab(psfSenha);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 1; //policao da linha (vertical)
        posicionador.gridx = 1; // posição da coluna (horizontal)
        this.add(psfSenha, posicionador); //o add adiciona o rotulo no painel  

        btnLogar = new JButton("Autenticar"); // texto no botão de logar
        btnLogar.setFocusable(true);    //acessibilidade    
        btnLogar.setToolTipText("btnLogar"); //acessibilidade  
        Util.registraEnterNoBotao(btnLogar);
        posicionador = new GridBagConstraints();
        posicionador.gridy = 2; //policao da linha (vertical)
        posicionador.gridx = 1; // posição da coluna (horizontal)
        btnLogar.addActionListener(this); //registrar o botão no Listener
        btnLogar.setActionCommand("comando_autenticar"); // string para distinção de botões (apelido)
        this.add(btnLogar, posicionador); //o add adiciona o rotulo no painel

    }

    public void requestFocus() { // acessibilidade

        txfNickname.requestFocus();
    }

    public void cleanForm() { // acessibilidade

        txfNickname.setText("");
        psfSenha.setText("");

        txfNickname.setBorder(defaultBorder);
        psfSenha.setBorder(defaultBorder);
    }

    @Override
    public void actionPerformed(ActionEvent e) { // ação a ser performada ao clicar no botão

        //testa para verificar se o botão btnLogar foi clicado.
        if (e.getActionCommand().equals(btnLogar.getActionCommand())) {

            //validacao do formulario.
            if (txfNickname.getText().trim().length() > 4) { 
            // captura o texto inserido, desconsidera espaços antes e depois e testa se tamanho > 4
                txfNickname.setBorder(new LineBorder(Color.green, 1)); // borda verde para indicar validade

                if (new String(psfSenha.getPassword()).trim().length() > 3) {
                // captura o texto inserido, desconsidera espaços antes e depois e testa se tamanho > 3
                    psfSenha.setBorder(new LineBorder(Color.green, 1)); // borda verde para indicar validade
                    // chama a função autenticar e passa o nickname e senha por parâmetro
                    controle.autenticar(txfNickname.getText().trim(), new String(psfSenha.getPassword()).trim());

                } else {

                    JOptionPane.showMessageDialog(this, "Informe Senha com 4 ou mais dígitos", "Autenticação", JOptionPane.ERROR_MESSAGE);
                    psfSenha.setBorder(new LineBorder(Color.red, 1)); // borda vermelhar para indicar invalidade
                    psfSenha.requestFocus(); // chama o foco para o campo inválido

                }

            } else {

                JOptionPane.showMessageDialog(this, "Informe Nickname com 4 ou mais dígitos", "Autenticação", JOptionPane.ERROR_MESSAGE);
                txfNickname.setBorder(new LineBorder(Color.red, 1)); // borda vermelhar para indicar invalidade
                txfNickname.requestFocus(); // chama o foco para o campo inválido
            }

        }

    }

}
