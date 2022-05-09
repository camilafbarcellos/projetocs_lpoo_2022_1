package br.edu.ifsul.cc.lpoo.cs.model.dao;

import br.edu.ifsul.cc.lpoo.cs.model.Endereco;
import br.edu.ifsul.cc.lpoo.cs.model.Jogador;
import br.edu.ifsul.cc.lpoo.cs.model.Partida;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Camila
 */
public class PersistenciaJDBC implements InterfacePersistencia {

    /*
      <property name="javax.persistence.jdbc.url" value="jdbc:postgresql://localhost:5432/db_lpoo_cs_2022_1"/>
      <property name="javax.persistence.jdbc.user" value="postgres"/>
      <property name="javax.persistence.jdbc.driver" value="org.postgresql.Driver"/>
      <property name="javax.persistence.jdbc.password" value="postgres"/>
     */
    // definir os dados acima (JPA) de forma estática 
    public static final String URL = "jdbc:postgresql://localhost:5432/db_lpoo_cs_2022_1";
    private final String USER = "postgres";
    private final String DRIVER = "org.postgresql.Driver";
    private final String SENHA = "postgres";
    private Connection con = null;

    public PersistenciaJDBC() throws Exception { // joga a exceção para o SQL
        Class.forName(DRIVER); // carregamento do driver PostgreSQL em tempo de execução
        System.out.println("Tentando estabelecer conexão JDBC com : " + URL + " ...");

        this.con = (Connection) DriverManager.getConnection(URL, USER, SENHA);
        // inicialmente ele aponta err e não reconhece as classes entre ()
        // deve-se importar de java.sql
        // con é semelhante ao Entity Manager do JPA, mas com menos recursos
    }

    @Override
    public Boolean conexaoAberta() {
        try {
            if (con != null) { // o if previne pointer null exception (apontamento para ponteiro nulo)
                return !con.isClosed(); // verifica se a conexão está aberta
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public void fecharConexao() {
        try {
            this.con.close(); // fecha a conexão
            System.out.println("Fechou a conexão JDBC");
        } catch (SQLException e) {
            e.printStackTrace(); // gera uma pilha de erro na saída
        }
    }

    @Override
    public Object find(Class c, Object id) throws Exception { // classe e objeto são genéricos
        // realizar um teste para cada classe possível em model para encontrar a tabela
        if (Jogador.class == c) {

        } else if (Endereco.class == c) {
            PreparedStatement ps = this.con.prepareStatement( // preparador de comandos SQL
                    "select id, cep, complemento " // comando em formato SQL
                    + "from tb_endereco where id = ?"); // ? é o parâmetro que será substituído
            ps.setInt(1, Integer.parseInt(id.toString())); // seta o comando com o parâmetro começando em 1 (? = 1)
            // setString para String e assim por diante
            ResultSet rs = ps.executeQuery(); // leva o resultado da operação para ser executado no PostgreSQL
            /*
            retorna uma matriz com 3 colunas (id | cep | complemento)
                                               1 | ... |    ...
            rs é um ponteiro que começa em -1 e progride sequencialmente para as próximas linhas
             */

            if (rs.next()) { // cada vez que o next() é true, o ponteiro rs aponta para a próxima linha (rs++)
                Endereco e = new Endereco();
                e.setId(rs.getInt("id"));
                e.setCep(rs.getString("cep"));
                e.setComplemento(rs.getString("complemento"));

                ps.close(); // fecha o cursor de parâmetro utilizado

                return e;
            }
        }

        return null;
    }

    @Override
    public void persist(Object o) throws Exception {
        // descobrir a instância do Object o
        if (o instanceof Endereco) { // verifica se "o" é da instância de Endreco
            Endereco e = (Endereco) o; // converter "o" para o "e", que é do tipo Endereco

            //descobrir se é para realizar insert ou update
            if (e.getId() == null) { // caso a chave primária não exista, é insert
                PreparedStatement ps
                        = this.con.prepareStatement( // preparador de comandos SQL
                                "insert into tb_endereco " // comando em formato SQL
                                + "(id, cep, complemento) values "
                                + "(nextval('seq_endereco_id'), ?, ?) " // nextval vai gerar o valor após o existente
                                + "returning id)");

                ps.setString(1, e.getCep()); // 1 indica o primeiro ?
                ps.setString(2, e.getComplemento()); // 2 indica o segundo ?
                ResultSet rs = ps.executeQuery(); // leva o resultado da operação para ser executado no PostgreSQL

                if (rs.next()) {
                    e.setId(rs.getInt(1)); // 1 é a primeira coluna do ResultSet
                }

            } else { // caso a chave primária exista, é update
                PreparedStatement ps
                        = this.con.prepareStatement( // preparador de comandos SQL
                                "update tb_endereco set " // pode alterar todos os campos, menos PK id
                                + "cep = ?, "
                                + "complemento = ? "
                                + "where id = ?");

                ps.setString(1, e.getCep()); // 1 indica o primeiro ?
                ps.setString(2, e.getComplemento()); // 2 indica o primeiro ?
                ps.setInt(3, e.getId()); // 3 indica o primeiro ?

                ps.execute(); // executa o comando execute porque não retorna informação
            }
        }
    }

    @Override
    public void remover(Object o) throws Exception {
        if (o instanceof Endereco) { // verifica se "o" é da instância de Endreco
            Endereco e = (Endereco) o; // converter "o" para o "e", que é do tipo Endereco
            PreparedStatement ps
                    = this.con.prepareStatement( // preparador de comandos SQL
                            "delete from tb_endereco "
                            + "where id = ?");
            ps.setInt(1, e.getId()); // 1 indica o primeiro ?
            ps.execute(); // executa o comando execute porque não retorna informação
        }
    }

    @Override
    public Jogador doLogin(String nickname, String senha) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Endereco> listEnderecos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Partida> listPartidas() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
