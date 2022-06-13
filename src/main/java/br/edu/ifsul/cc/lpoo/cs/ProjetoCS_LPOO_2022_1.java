package br.edu.ifsul.cc.lpoo.cs;

import javax.swing.JOptionPane;

/**
 *
 * @author Camila
 */
public class ProjetoCS_LPOO_2022_1 {

    private Controle controle; // inicializa a variável de controle que vai intermediar a manipulaçãp da gui

    // método construtor
    public ProjetoCS_LPOO_2022_1() {

        //primeiramente - estabelecer uma conexao com o banco de dados -> sem ela não é necessário exibir a gui
        try {
            controle = new Controle(); //cria a instancia e atribui para o atributo controle.

            //"caminho feliz" : passo 3
            if (controle.conectarBD()) { // caso a conexão retorne verdadeiro

                //"caminho feliz" : passo 4
                controle.initComponents();

            } else { // caso a conexão retorne falso

                JOptionPane.showMessageDialog(null, "Não conectou no Banco de Dados!", "Banco de Dados", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) { // tratamento de exceção (diferente do throws que apenas jogava para cima)
                                       // pai -> indica a centralização na tela
            JOptionPane.showMessageDialog(null, "Erro ao tentar conectar no Banco de Dados: "
                                        + ex.getLocalizedMessage(), "Banco de Dados", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace(); // imprime a pilha de erros
        }

    }

    public static void main(String[] args) {
        /* 
           cria uma instância sem atribuí-la a um objeto
           a main é estática, por isso devemos definir dessa forma
           variáveis estáticas consomem mais memória e isso é ruim!
        */
        new ProjetoCS_LPOO_2022_1(); // executa o construtor
    }
}
