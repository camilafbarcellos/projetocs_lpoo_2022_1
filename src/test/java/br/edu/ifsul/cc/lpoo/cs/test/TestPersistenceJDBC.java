package br.edu.ifsul.cc.lpoo.cs.test;

import br.edu.ifsul.cc.lpoo.cs.model.Arma;
import br.edu.ifsul.cc.lpoo.cs.model.Artefato;
import br.edu.ifsul.cc.lpoo.cs.model.Calibre;
import br.edu.ifsul.cc.lpoo.cs.model.Compra;
import br.edu.ifsul.cc.lpoo.cs.model.Endereco;
import br.edu.ifsul.cc.lpoo.cs.model.ItensCompra;
import br.edu.ifsul.cc.lpoo.cs.model.Jogador;
import br.edu.ifsul.cc.lpoo.cs.model.Municao;
import br.edu.ifsul.cc.lpoo.cs.model.Patente;
import br.edu.ifsul.cc.lpoo.cs.model.Tipo;
import br.edu.ifsul.cc.lpoo.cs.model.dao.PersistenciaJDBC;
import java.util.Calendar;
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
    
        Implementar um método de teste para realizar as seguintes operações:
    
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

        /*
            recupera a lista de jogadores
            imprimir na tela os dados de cada jogador e as suas respectivas patentes
            alterar o jogador ao algum dado da tabela associativa.    
            remove as patentes do jogador (tb_jogador_patente), uma a uma 
            caso a lista de jogadores esteja vazia, insere um ou mais jogadores , bem como, vincula ao menos uma patente no jogador (tb_jogador_patente)
        */
                
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

                System.out.println("Incluiu o jogador " + j.getNickname() + " com " + j.getPatentes().size() + " patentes.");

            }

            persistencia.fecharConexao();

        } else {
            System.out.println("Nao abriu a conexao com o BD via JDBC");
        }

    }

    /*
        1) Atividade de revisão para a avaliação da primeira etapa. 
        recupera a lista de jogadores
        imprimir na tela os dados de cada jogador e as suas respectivos artefatos (arma e/ou municao)
        remove os artefatos do jogador (tb_jogador_artefato), um a um
        caso a lista de jogadores esteja vazia, gera um jogador contendo dois artefatos.
    
        Correção do professor:
     */
    private Artefato getArtefato(PersistenciaJDBC persistencia, Integer id, String tipo) throws Exception {
        // artefato está envolvido em heranças (arma e municao)
        Artefato a = (Artefato) persistencia.find(Artefato.class, id);
        if (a == null) { // nenhuma arma
            if (tipo.equals("A")) { // se for tipo A-arma
                Arma arma = new Arma(); // cria arma
                arma.setNome("Revolver");
                arma.setPeso(1.5f);
                arma.setValor(1500f);
                arma.setComprimento_cano(1.2f);
                arma.setTipo(Tipo.FOGO);
                arma.setTipoArtefato("A");
                persistencia.persist(arma); // persiste a arma
                return arma;
            } else { // se for tipo M-municao
                Municao municao = new Municao();
                municao.setNome("Bala");
                municao.setPeso(0.5f);
                municao.setValor(25.5f);
                municao.setExplosiva(Boolean.FALSE);
                municao.setCalibre(Calibre.C03);
                municao.setTipoArtefato("M");
                persistencia.persist(municao);
                return municao;
            }

        }

        return a;

    }

    private Endereco getEndereco(PersistenciaJDBC persistencia, Integer id) throws Exception {

        Endereco e = (Endereco) persistencia.find(Endereco.class, id);
        if (e == null) {
            e = new Endereco();
            //e.setId(id); // não precisa pq id é gerado
            e.setCep("99010011");
            e.setComplemento("nenhum");
            persistencia.persist(e);

        }

        return e;

    }

    //@Test
    public void testListPersistenciaJogadorArtefato() throws Exception {

        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        if (persistencia.conexaoAberta()) {
            System.out.println("Abriu a conexão com o BD via JDBC");

            // recupera a lista de jogadores
            List<Jogador> list = persistencia.listJogadores();

            // caso a lista de jogadores esteja vazia
            if (list == null || list.isEmpty()) {

                // gera um jogador contendo dois artefatos
                Jogador j = new Jogador();
                j.setNickname("fulano@");
                j.setSenha("123456");
                j.setPontos(0);
                j.setEndereco(getEndereco(persistencia, 1));
                j.setArtefato(getArtefato(persistencia, 1, "A"));
                j.setArtefato(getArtefato(persistencia, 2, "M"));

                persistencia.persist(j);

                System.out.println("Cadastrou o jogador " + j.getNickname());

            } else {

                // imprimir na tela os dados de cada jogador e as suas respectivos artefatos (arma e/ou municao)
                System.out.println("Listagem de jogadores cadastrados:");
                for (Jogador j : list) {

                    System.out.println("\tJogador: " + j.getNickname());
                    System.out.println("\t\tArtefatos:");
                    if (j.getArtefatos() != null) {
                        for (Artefato a : j.getArtefatos()) {
                            System.out.println("\t\t\tArtefato " + a.getNome());
                        }
                    }

                    // remove os artefatos do jogador (tb_jogador_artefato), um a um
                    persistencia.remover(j);
                    System.out.println("Removeu o jogador " + j.getNickname());
                }

            }

            persistencia.fecharConexao();

        } else {
            System.out.println("Não abriu a conexão com o BD via JDBC");
        }
    }
  
    /*
        2) Atividade de revisão para a avaliação da primeira etapa. 
        recupera a lista de compras
        imprimir na tela os dados de cada compra e as seus respectivos itens
        remova os itens e a compra
        caso a lista de compra esteja vazia, gera uma compra contendo itens.
    */
            
    private Jogador getJogador(PersistenciaJDBC persistencia, String nickname) throws Exception {
        Jogador j = (Jogador) persistencia.find(Jogador.class, nickname);
        if (j == null) {
            j = new Jogador();
            j.setNickname(nickname);
            j.setData_cadastro(Calendar.getInstance());
            j.setSenha("123");
            j.setEndereco(getEndereco(persistencia, 1));
            persistencia.persist(j);
        }

        return j;
    }
    
    private ItensCompra getItem(PersistenciaJDBC persistencia, Integer id) throws Exception {
        ItensCompra i = (ItensCompra) persistencia.find(ItensCompra.class, id);
        if (i == null) {
            i = new ItensCompra();
            i.setQuantidade(5f);
            i.setValor(100f);
            i.setArtefato(getArtefato(persistencia, 1, "M"));
            persistencia.persist(i);
        }

        return i;
    }
    
    @Test
    public void testListPersistenciaCompra() throws Exception {

        PersistenciaJDBC persistencia = new PersistenciaJDBC();
        if (persistencia.conexaoAberta()) {
            System.out.println("Abriu a conexão com o BD via JDBC");

            // recupera a lista de compras
            List<Compra> list = persistencia.listCompras();

            // caso a lista de compras esteja vazia
            if (list == null || list.isEmpty()) {

                // gera uma compra contendo itens
                Compra c = new Compra();
                c.setData_compra(Calendar.getInstance());
                c.setItem(getItem(persistencia, 1));
                c.setJogador(getJogador(persistencia, "joga@dor"));

                persistencia.persist(c);

                System.out.println("Cadastrou a compra " + c.getId());

            } else {

                // imprimir na tela os dados de cada compra e as seus respectivos itens
                System.out.println("Listagem de compras cadastradas:");
                for (Compra c : list) {

                    System.out.println("\tCompra: " + c.getId());
                    System.out.println("\t\tItens:");
                    if (c.getItens()!= null) {
                        for (ItensCompra i : c.getItens()) {
                            System.out.println("\t\t\tItem " + i.getArtefatoNome());
                        }
                    }

                    // remova os itens e a compra
                    persistencia.remover(c);
                    System.out.println("Removeu a compra " + c.getId());
                }

            }

            persistencia.fecharConexao();

        } else {
            System.out.println("Não abriu a conexão com o BD via JDBC");
        }
    }
}
