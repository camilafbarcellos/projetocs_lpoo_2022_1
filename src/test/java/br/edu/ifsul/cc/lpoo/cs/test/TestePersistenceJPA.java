package br.edu.ifsul.cc.lpoo.cs.test;

import br.edu.ifsul.cc.lpoo.cs.model.Endereco;
import br.edu.ifsul.cc.lpoo.cs.model.dao.PersistenciaJPA;
import org.junit.Test;

/**
 *
 * @author 20202pf.cc0003
 */
public class TestePersistenceJPA {

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

    @Test
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

}
