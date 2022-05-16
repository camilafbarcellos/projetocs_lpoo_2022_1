package br.edu.ifsul.cc.lpoo.cs.test;

import br.edu.ifsul.cc.lpoo.cs.model.Artefato;
import br.edu.ifsul.cc.lpoo.cs.model.Endereco;
import br.edu.ifsul.cc.lpoo.cs.model.Jogador;
import br.edu.ifsul.cc.lpoo.cs.model.Patente;
import br.edu.ifsul.cc.lpoo.cs.model.dao.PersistenciaJDBC;
import java.util.List;
import org.junit.Test;

/**
 *
 * @author 20202pf.cc0003
 */
public class TestPersistenceJDBC {

    //@Test
    public void testConexao() throws Exception {
        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        if (persistencia.conexaoAberta()) {
            System.out.println("Abriu a conexão com o BD via JDBC");
            persistencia.fecharConexao();
        } else {
            System.out.println("Não abriu a conexão com o  BD via JDBC");
        }
    }

    /*
        Atividade de aula - 09/05/2022
    
        Implememtar um método de teste para realizar as seguintes operações:
    
        1) Abrir conexão via JDBC
        2) Selecionar um determinado registro na tabela tb_endereco
        3) Caso encontre, alterar e remover
        4) Caso não encontre, inserir novo registro
        5) Fechar a conexão
    
     */
    //@Test
    public void testPersistenciaEndereco() throws Exception {
        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        // 1) Abrir conexão via JDBC
        if (persistencia.conexaoAberta()) {
            System.out.println("Abriu a conexão com o BD via JDBC");
            // 5) Fechar a conexão
            persistencia.fecharConexao();
        } else {
            System.out.println("Não abriu a conexão com o  BD via JDBC");
        }
    }

    //@Test
    public void testListPersistenciaPatente() throws Exception {

        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        if (persistencia.conexaoAberta()) {

            List<Patente> lista = persistencia.listPatentes();

            if (!lista.isEmpty()) {

                for (Patente p : lista) {

                    System.out.println("Endereco: " + p.getId() + " CEP: " + p.getNome());

                    persistencia.remover(p);
                }

            } else {

                System.out.println("Não encontrou o patente");

                Patente p = new Patente();
                p.setNome("patente bronze");
                persistencia.persist(p); //insert na tabela.                
                System.out.println("Cadastrou o Patente " + p.getId());

                p.setNome("Patente Bronze");
                persistencia.persist(p); //update na tabela.
                System.out.println("Cadastrou o Patente " + p.getId());

            }

            persistencia.fecharConexao();
        } else {
            System.out.println("Nao abriu a conexao com o BD via JDBC");
        }
    }

    //@Test
    public void testListPersistenciaJogadorPatente() throws Exception {

        // recupera a lista de jogadores
        //imprimir na tela os dados de cada jogador e as suas respectivas patentes
        //alterar o jogador ao algum dado da tabela associativa.    
        //remove as patentes do jogador (tb_jogador_patente), uma a uma 
        //caso a lista de jogadores esteja vazia, insere um ou mais jogadores , bem como, vincula ao menos uma patente no jogador (tb_jogador_patente)        
        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        if (persistencia.conexaoAberta()) {
            System.out.println("abriu a conexao com o BD via JDBC");

            List<Jogador> list = persistencia.listJogadores();

            if (!list.isEmpty()) {

                for (Jogador j : list) {

                    System.out.println("Jogador : " + j.getNickname());
                    System.out.println("Endereço: " + j.getEndereco().getCep());

                    if (j.getPatentes() != null) {
                        for (Patente p : j.getPatentes()) {

                            System.out.println("\tPatente : " + p.getNome());

                        }
                    }

                    j.setPontos(999);

                    persistencia.persist(j);

                    System.out.println(" Jogador alterado : " + j.getNickname());

                    persistencia.remover(j);

                    System.out.println(" Jogador removido : " + j.getNickname());

                }

            } else {

                Jogador j = new Jogador();
                j.setNickname("teste@");
                j.setSenha("123456");
                j.setPontos(0);
                Endereco end = new Endereco();
                end.setCep("99010010");

                persistencia.persist(end);
                j.setEndereco(end);

                Patente p = new Patente();
                p.setNome("Patente de teste");
                persistencia.persist(p);

                j.setPatente(p);
                persistencia.persist(j);

                System.out.println("Incluiu o jogaador " + j.getNickname() + " com " + j.getPatentes().size() + " patentes.");

            }

            persistencia.fecharConexao();

        } else {
            System.out.println("Nao abriu a conexao com o BD via JDBC");
        }

    }

    //@Test
    public void testListPersistenciaJogadorArtefato() throws Exception {

        // 1) Atividade de revisão para a avaliação da primeira etapa. 
        // recupera a lista de jogadores
        // imprimir na tela os dados de cada jogador e as suas respectivos artefatos (arma e/ou municao)
        // remove os artefatos do jogador (tb_jogador_artefato), um a um
        // caso a lista de jogadores esteja vazia, gera um jogador contendo dois artefatos. 
        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        if (persistencia.conexaoAberta()) {
            System.out.println("abriu a conexao com o BD via JDBC");
            // recupera a lista de jogadores
            List<Jogador> list = persistencia.listJogadores();

            if (!list.isEmpty()) {
                // imprimr os dados do jogador e seus artefatos
                for (Jogador j : list) {
                    // dados do jogador
                    System.out.println("Jogador : " + j.getNickname());
                    System.out.println("Endereço: " + j.getEndereco().getCep());

                    if (j.getArtefatos() != null) {
                        for (Artefato a : j.getArtefatos()) {
                            // artefatos
                            System.out.println("\tArtefato : " + a.getNome());
                            //persistencia.remover(a); // remove o artefato

                        }
                    }
                    
                    // remove o artefato do jogador (remove todos ao remover o próprio jogador)
                    persistencia.remover(j);

                    System.out.println(" Jogador removido : " + j.getNickname());
                }

            } else { // caso lista vazia
                // gera um jogador
                Jogador j = new Jogador();
                j.setNickname("teste@");
                j.setSenha("123456");
                j.setPontos(0);
                Endereco end = new Endereco();
                end.setCep("99010010");
                // persiste o jogador
                persistencia.persist(end);
                j.setEndereco(end);
                // gera um artefato
                Artefato a = new Artefato();
                a.setNome("Artefato de teste");
                persistencia.persist(a);

                j.setArtefato(a);
                persistencia.persist(j);

                System.out.println("Incluiu o jogador " + j.getNickname() + " com " + j.getArtefatos().size() + " artefatos.");

            }

            persistencia.fecharConexao();

        } else {
            System.out.println("Nao abriu a conexao com o BD via JDBC");
        }
    }

    //@Test
    public void testListPersistenciaCompra() throws Exception {

        // 2) Atividade de revisão para a avaliação da primeira etapa. 
        // recupera a lista de compras
        // imprimir na tela os dados de cada compra e as seus respectivos itens
        // remova os itens e a compra
        // caso a lista de compra esteja vazia, gera uma compra contendo itens. 


    }

}
