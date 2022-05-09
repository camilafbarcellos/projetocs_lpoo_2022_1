package br.edu.ifsul.cc.lpoo.cs.test;

import br.edu.ifsul.cc.lpoo.cs.model.dao.PersistenciaJDBC;
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
    @Test
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
}
