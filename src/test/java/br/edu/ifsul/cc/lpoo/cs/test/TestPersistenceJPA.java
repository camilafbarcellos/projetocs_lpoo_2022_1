package br.edu.ifsul.cc.lpoo.cs.test;

import br.edu.ifsul.cc.lpoo.cs.model.Endereco;
import br.edu.ifsul.cc.lpoo.cs.model.Jogador;
import br.edu.ifsul.cc.lpoo.cs.model.Partida;
import br.edu.ifsul.cc.lpoo.cs.model.dao.PersistenciaJPA;
import java.util.Calendar;
import java.util.List;
import org.junit.Test;

/**
 *
 * @author 20202pf.cc0003
 */
public class TestPersistenceJPA {
   
    //@Test 
    public void testConexaoGeracaoTabelas() {
        PersistenciaJPA persistencia = new PersistenciaJPA();
        if (persistencia.conexaoAberta()) {
            System.out.println("Abriu a conexão com o BD via JPA");
            persistencia.fecharConexao();
        } else {
            System.out.println("Não abriu a conexão com o  BD via JPA");
        }

    }

    //@Test
    public void testPersistenciaEndereco() throws Exception { // o throws está jogando o erro para o NetBeans resolver
        PersistenciaJPA persistencia = new PersistenciaJPA();
        // toda persistência exige uma testagem de conexão
        if (persistencia.conexaoAberta()) {
            System.out.println("testPersistenciaEndereco: ");

            Endereco e = (Endereco) persistencia.find(Endereco.class, 1); // vai dar erro na find, então joga pro NetBeans
            if (e == null) { // busca o id=1 e caso não encontre cria com o setado
                System.out.println("Não encontrou o endereço id=1");
                // 1- inicializar o e em new
                e = new Endereco();
                // 2- setar um cep
                e.setCep("99070000");
                // 3- encaminhar o e
                persistencia.persist(e);
            } else { // busca o id=1 e caso encontre mostra e remove
                System.out.println("Encontrou o id=1 para enredereco" + e.getCep());
                persistencia.remover(e);
            }

            persistencia.fecharConexao();
        } else {
            System.out.println("Não abriu a conexão com o  BD via JPA");
        }
    }

    //@Test
    public void testPersistenciaListEndereco() throws Exception {
        PersistenciaJPA persistencia = new PersistenciaJPA();
        if (persistencia.conexaoAberta()) {
            System.out.println("testPersistenciaEndereco:");

            List<Endereco> list = persistencia.listEnderecos();
            if (!list.isEmpty()) {
                for (Endereco end : list) {
                    System.out.println("Endereco: " + end.getCep());
                }
            }
            persistencia.fecharConexao();
        } else {
            System.out.println("Nao abriu a conexao com o BD via JPA");
        }
    }
    
    /*
        Atividade assíncrona - 07/05/2022
    
        Codifique na classe TestPersistenceJPA um teste unitário contendo os seguintes passos via (JPA):
    
        1) Recuperar a lista de partidas.
        2) Se a lista de partidas não for vazia, imprimir na tela os dados de cada objeto, altere e depois remova-o.
        3) Se a lista de partidas for vazia, persistir dois novos objetos de partida.
    
     */
    
    @Test
    public void testPersistenciaListPartida() throws Exception {
        PersistenciaJPA persistencia = new PersistenciaJPA();
        if (persistencia.conexaoAberta()) {
            System.out.println("testPersistenciaListPartida:");
            // 1) recuperar a lista de partida
            List<Partida> list = persistencia.listPartidas();
            if (!list.isEmpty()) { // se a lista não for vazia
                for (Partida end : list) {
                    // 2) imprimir os dados cada objeto
                    System.out.println("Partida: " + end.getId() + " | " + end.getInicio() +
                            " | " + end.getJogador() + "\n");
                    // 2) alterar
                    Partida p = new Partida(); // inicializa partida
                    p.setInicio(Calendar.getInstance()); // seta data na partida
                    Jogador j = new Jogador(); // inicializa jogador que será setado na partida
                    j.setNickname("camilafb"); // seta nickname do jogador
                    j.setSenha("senha@123"); // seta senha do jogador
                    j.setData_cadastro(Calendar.getInstance()); // seta data do jogador
                    Endereco e = new Endereco(); // inicializa endereco que será setado no jogador
                    e.setCep("99064070"); // seta cep do endereco
                    persistencia.persist(e); // persiste endereco
                    j.setEndereco(e); // seta endereco do jogador
                    persistencia.persist(j); // persiste jogador
                    p.setJogador(j); // seta jogador na partida
                    persistencia.persist(p); // persiste partida
                    // 2) remover
                    persistencia.remover(p); // remover partida
                    persistencia.remover(j); // remover jogador
                    persistencia.remover(e); // remover endereco
                }
            } else { // se a lista for vazia
                // 3) persistir dois novos objetos de partida
                Partida p1 = new Partida(); // inicializa as partidas
                Partida p2 = new Partida();
                Jogador j1 = new Jogador(); // inicializa jogadores que serão setados na partidas
                Jogador j2 = new Jogador();
                j1.setNickname("jogador1"); // seta nicknames dos jogadores
                j2.setNickname("jogador2");
                j1.setSenha("123456"); // seta senhas dos jogadores
                j2.setSenha("654321");
                j1.setData_cadastro(Calendar.getInstance()); // seta datas dos jogadores
                j2.setData_cadastro(Calendar.getInstance());
                Endereco e1 = new Endereco(); // inicializa os enderecos
                Endereco e2 = new Endereco();
                e1.setCep("99032330"); // seta ceps dos enderecos
                e2.setCep("99064200");
                persistencia.persist(e1); // persiste os enderecos
                persistencia.persist(e2);
                j1.setEndereco(e1); // seta os enderecos dos jogadores
                j2.setEndereco(e2);
                persistencia.persist(j1); // persiste os jogadores
                persistencia.persist(j2);
                p1.setJogador(j1); // seta jogadores na partidas
                p2.setJogador(j2);
                p1.setInicio(Calendar.getInstance()); // seta datas nas partidas
                p2.setInicio(Calendar.getInstance());
                persistencia.persist(p1); // persiste as partidas
                persistencia.persist(p2);
            }
            persistencia.fecharConexao();
        } else {
            System.out.println("Nao abriu a conexao com o BD via JPA");
        }
    }

}
