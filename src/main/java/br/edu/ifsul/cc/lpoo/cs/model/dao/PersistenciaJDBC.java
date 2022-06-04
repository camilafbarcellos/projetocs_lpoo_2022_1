package br.edu.ifsul.cc.lpoo.cs.model.dao;

import br.edu.ifsul.cc.lpoo.cs.model.Arma;
import br.edu.ifsul.cc.lpoo.cs.model.Artefato;
import br.edu.ifsul.cc.lpoo.cs.model.Calibre;
import br.edu.ifsul.cc.lpoo.cs.model.Compra;
import br.edu.ifsul.cc.lpoo.cs.model.Endereco;
import br.edu.ifsul.cc.lpoo.cs.model.ItensCompra;
import br.edu.ifsul.cc.lpoo.cs.model.Jogador;
import br.edu.ifsul.cc.lpoo.cs.model.Municao;
import br.edu.ifsul.cc.lpoo.cs.model.Partida;
import br.edu.ifsul.cc.lpoo.cs.model.Patente;
import br.edu.ifsul.cc.lpoo.cs.model.Tipo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
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
            PreparedStatement ps = this.con.prepareStatement("select nickname, data_cadastro, data_ultimo_login, pontos, senha, endereco_id from tb_jogador where nickname = ? ");

            ps.setString(1, id.toString());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Jogador j = new Jogador();
                j.setNickname(rs.getString("nickname"));

                Calendar dtCad = Calendar.getInstance();
                dtCad.setTimeInMillis(rs.getDate("data_cadastro").getTime());
                j.setData_cadastro(dtCad);

                if (rs.getDate("data_ultimo_login") != null) {
                    Calendar dtU = Calendar.getInstance();
                    dtU.setTimeInMillis(rs.getDate("data_ultimo_login").getTime());
                    j.setData_ultimo_login(dtU);
                }

                j.setPontos(rs.getInt("pontos"));
                j.setSenha(rs.getString("senha"));
                Endereco end = new Endereco();
                end.setId(rs.getInt("endereco_id"));
                j.setEndereco(end);

                PreparedStatement ps2 = this.con.prepareStatement("select p.id, p.cor, p.nome from tb_jogador_patente jp, tb_patente p where p.id=jp.patente_id and jp.jogador_nickname = ? ");
                ps2.setString(1, id.toString());

                ResultSet rs2 = ps.executeQuery();

                while (rs2.next()) {

                    Patente p = new Patente();
                    p.setId(rs2.getInt("id"));
                    p.setNome(rs2.getString("nome"));
                    p.setCor(rs2.getString("cor"));

                    j.setPatente(p);
                }

            }
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
        } else if (c == Patente.class) {
            PreparedStatement ps = this.con.prepareStatement("select id, cor, nome from tb_patente where id = ?");

            ps.setInt(1, Integer.parseInt(id.toString()));

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Endereco e = new Endereco();
                e.setId(rs.getInt("id"));
                e.setCep(rs.getString("cep"));
                e.setComplemento(rs.getString("complemento"));

                ps.close();

                return e;
            }
        } else if (c == Artefato.class) {

            PreparedStatement ps = this.con.prepareStatement("select id, nome, peso, valor, tipo from tb_artefato where id = ?");

            ps.setInt(1, Integer.parseInt(id.toString()));

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                if (rs.getString("tipo").equals("A")) {
                    Arma a = new Arma();
                    a.setId(rs.getInt("id"));
                    a.setNome(rs.getString("nome"));
                    a.setValor(rs.getFloat("valor"));
                    a.setTipoArtefato("A");

                    PreparedStatement ps2 = this.con.prepareStatement("select comprimento_cano, tipo from tb_arma where id = ?");

                    ps2.setInt(1, Integer.parseInt(id.toString()));

                    ResultSet rs2 = ps2.executeQuery();

                    if (rs2.next()) {

                        a.setComprimento_cano(rs2.getFloat("comprimento_cano"));
                        if (rs2.getString("tipo").equals(Tipo.FOGO)) {
                            a.setTipo(Tipo.FOGO);
                        } else if (rs2.getString("tipo").equals(Tipo.BRANCA)) {
                            a.setTipo(Tipo.BRANCA);
                        }
                    }
                    rs2.close();

                    return a;

                } else if (rs.getString("tipo").equals("M")) {
                    Municao m = new Municao();
                    m.setId(rs.getInt("id"));
                    m.setNome(rs.getString("nome"));
                    m.setValor(rs.getFloat("valor"));
                    m.setTipoArtefato("M");

                    PreparedStatement ps2 = this.con.prepareStatement("select calibre, explosiva from tb_municao where id = ?");

                    ps2.setInt(1, Integer.parseInt(id.toString()));

                    ResultSet rs2 = ps2.executeQuery();

                    if (rs2.next()) {

                        m.setExplosiva(rs2.getBoolean("explosiva"));
                        if (rs2.getString("calibre").equals(Calibre.C03)) {
                            m.setCalibre(Calibre.C03);
                        } else if (rs2.getString("calibre").equals(Calibre.C05)) {
                            m.setCalibre(Calibre.C05);
                        } else if (rs2.getString("calibre").equals(Calibre.C08)) {
                            m.setCalibre(Calibre.C08);
                        }
                    }
                    rs2.close();

                    return m;
                }

            }

            ps.close();

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
        } else if (o instanceof Jogador) {
            Jogador j = (Jogador) o;

            if (j.getData_cadastro() == null) { //insert

                PreparedStatement ps = this.con.prepareStatement("insert into tb_jogador "
                        + "(nickname, data_cadastro, pontos, senha, endereco_id) values "
                        + "(?, now(), ?, ?, ? ) ");

                ps.setString(1, j.getNickname());
                ps.setInt(2, j.getPontos());
                ps.setString(3, j.getSenha());
                ps.setInt(4, j.getEndereco().getId());

                ps.execute();

                if (j.getPatentes() != null && !j.getPatentes().isEmpty()) {

                    for (Patente p : j.getPatentes()) {

                        PreparedStatement ps2 = this.con.prepareStatement("insert into tb_jogador_patente "
                                + "(jogador_nickname, patente_id) values "
                                + "(?, ? ) ");

                        ps2.setString(1, j.getNickname());
                        ps2.setInt(2, p.getId());

                        ps2.execute();

                    }

                }
            } else { //update

                //alterar as colunas : data_cadastro, pontos, senha, endereco_id
                PreparedStatement ps = this.con.prepareStatement("update tb_jogador set "
                        + "pontos = ?, senha = ?, endereco_id = ? where nickname = ? ");

                ps.setInt(1, j.getPontos());
                ps.setString(2, j.getSenha());
                ps.setInt(3, j.getEndereco().getId());
                ps.setString(4, j.getNickname());

                ps.execute();

                PreparedStatement ps2 = this.con.prepareStatement("delete from tb_jogador_patente where jogador_nickname = ? ");
                ps2.setString(1, j.getNickname());

                if (j.getPatentes() != null && !j.getPatentes().isEmpty()) {

                    for (Patente p : j.getPatentes()) {

                        PreparedStatement ps3 = this.con.prepareStatement("insert into tb_jogador_patente "
                                + "(jogador_nickname, patente_id) values "
                                + "(?, ? ) ");

                        ps3.setString(1, j.getNickname());
                        ps3.setInt(2, p.getId());

                        ps3.execute();

                    }

                }

            }
        } else if (o instanceof Patente) {

            Patente p = (Patente) o; //converter o para o e que é do tipo Endereco
            if (p.getId() == null) {

                PreparedStatement ps
                        = this.con.prepareStatement(
                                "insert into tb_patente "
                                + "(id, cor, nome) values "
                                + "(nextval('seq_patente_id'), ?, ?) "
                                + "returning id");

                ps.setString(1, p.getCor());
                ps.setString(2, p.getNome());

                //executa o comando e recupera o retorno. 
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    //seta no objeto e, para disponibilizar o acesso ao id gerado.
                    p.setId(rs.getInt(1));
                }

            } else {

                //update.
                PreparedStatement ps
                        = this.con.prepareStatement(
                                "udpate tb_patente  set "
                                + "cor =  ?, nome = ? where id = ?");

                ps.setString(1, p.getCor());
                ps.setString(2, p.getNome());
                ps.setInt(3, p.getId());

                //executa o comando e recupera o retorno. 
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    //seta no objeto e, para disponibilizar o acesso ao id gerado.
                    p.setId(rs.getInt(1));
                }
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
        } else if (o instanceof Jogador) {

            //remove registro(s) em tb_jogador_patente
            //remove registro em tb_jogador
            Jogador j = (Jogador) o;
            PreparedStatement ps = this.con.prepareStatement("delete from tb_jogador_patente where jogador_nickname = ?");
            ps.setString(1, j.getNickname());
            ps.execute();
            ps.close();

            ps = this.con.prepareStatement("delete from tb_jogador where nickname = ?");
            ps.setString(1, j.getNickname());
            ps.execute();
            ps.close();

        } else if (o instanceof Patente) {

            Patente p = (Patente) o; //converter o para o e que é do tipo Endereco            

        }
        //testar as demais instancias para as classes do pacote model
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

    @Override
    public List<Jogador> listJogadores() throws Exception {

        List<Jogador> lista = null;

        PreparedStatement ps = this.con.prepareStatement("select j.nickname, j.data_cadastro, j.data_ultimo_login, j.pontos, j.senha, "
                + "e.id as endereco_id, e.cep "
                + "from tb_jogador j left join tb_endereco e "
                + "on j.endereco_id=e.id "
                + "order by j.data_cadastro asc");

        ResultSet rs = ps.executeQuery();//executa a query        

        lista = new ArrayList();
        while (rs.next()) {

            Jogador j = new Jogador();
            j.setNickname(rs.getString("nickname"));

            Calendar dtCad = Calendar.getInstance();
            dtCad.setTimeInMillis(rs.getDate("data_cadastro").getTime());
            j.setData_cadastro(dtCad);

            if (rs.getDate("data_ultimo_login") != null) {
                Calendar dtU = Calendar.getInstance();
                dtU.setTimeInMillis(rs.getDate("data_ultimo_login").getTime());
                j.setData_ultimo_login(dtU);
            }

            j.setPontos(rs.getInt("pontos"));
            j.setSenha(rs.getString("senha"));
            Endereco end = new Endereco();
            end.setId(rs.getInt("endereco_id"));
            end.setCep(rs.getString("cep"));
            j.setEndereco(end);

            PreparedStatement ps2 = this.con.prepareStatement("select p.id, p.cor, p.nome from tb_jogador_patente jp, tb_patente p where p.id=jp.patente_id and jp.jogador_nickname = ? ");
            ps2.setString(1, j.getNickname());

            ResultSet rs2 = ps2.executeQuery();

            while (rs2.next()) {

                Patente p = new Patente();
                p.setId(rs2.getInt("id"));
                p.setNome(rs2.getString("nome"));
                p.setCor(rs2.getString("cor"));

                j.setPatente(p);
            }

            PreparedStatement ps3 = this.con.prepareStatement("select a.id, a.nome, a.peso, a.valor, a.tipo from tb_jogador_artefato ja, tb_artefato a where a.id=ja.artefato_id and ja.jogador_nickname = ? ");
            ps3.setString(1, j.getNickname());

            ResultSet rs3 = ps3.executeQuery();

            while (rs3.next()) {

                if (rs3.getString("tipo").equals("A")) {

                    Arma a = new Arma();
                    a.setId(rs3.getInt("id"));
                    a.setNome(rs3.getString("nome"));
                    a.setValor(rs3.getFloat("valor"));
                    a.setTipoArtefato("A");

                    PreparedStatement ps4 = this.con.prepareStatement("select comprimento_cano, tipo from tb_arma where id = ?");

                    ps4.setInt(1, a.getId());

                    ResultSet rs4 = ps4.executeQuery();

                    if (rs4.next()) {

                        a.setComprimento_cano(rs4.getFloat("comprimento_cano"));
                        if (rs4.getString("tipo").equals(Tipo.FOGO)) {
                            a.setTipo(Tipo.FOGO);
                        } else if (rs4.getString("tipo").equals(Tipo.BRANCA)) {
                            a.setTipo(Tipo.BRANCA);
                        }
                    }
                    rs4.close();

                    //adiciona a Arma na lista de armas do jogador.
                    j.setArtefato(a);

                } else if (rs3.getString("tipo").equals("M")) {

                    Municao m = new Municao();
                    m.setId(rs3.getInt("id"));
                    m.setNome(rs3.getString("nome"));
                    m.setValor(rs3.getFloat("valor"));
                    m.setTipoArtefato("M");

                    PreparedStatement ps4 = this.con.prepareStatement("select calibre, explosiva from tb_municao where id = ?");

                    ps4.setInt(1, m.getId());

                    ResultSet rs4 = ps4.executeQuery();

                    if (rs2.next()) {

                        m.setExplosiva(rs4.getBoolean("explosiva"));
                        if (rs4.getString("calibre").equals(Calibre.C03)) {
                            m.setCalibre(Calibre.C03);
                        } else if (rs4.getString("calibre").equals(Calibre.C05)) {
                            m.setCalibre(Calibre.C05);
                        } else if (rs4.getString("calibre").equals(Calibre.C08)) {
                            m.setCalibre(Calibre.C08);
                        }
                    }
                    rs4.close();

                    j.setArtefato(m);
                }

            }

            lista.add(j);

        }
        return lista;

    }

    @Override
    public List<Patente> listPatentes() throws Exception {

        List<Patente> lista = null;

        PreparedStatement ps = this.con.prepareStatement("select id, cor, nome from tb_patente order by id asc");

        ResultSet rs = ps.executeQuery();//executa a query        

        lista = new ArrayList();
        while (rs.next()) {

            Patente p = new Patente();
            p.setId(rs.getInt("id"));
            p.setCor(rs.getString("cor"));
            p.setNome(rs.getString("nome"));

            lista.add(p);

        }

        rs.close();

        return lista;

    }

    /*
    @Override
    public List<Artefato> listArtefatos() throws Exception {

        List<Artefato> lista = null;

        PreparedStatement ps = this.con.prepareStatement("select id, nome, peso, valor from tb_artefato order by id asc");

        ResultSet rs = ps.executeQuery(); // executa a query        

        lista = new ArrayList();
        while (rs.next()) {

            Artefato a = new Artefato();
            a.setId(rs.getInt("id"));
            a.setNome(rs.getString("nome"));
            a.setPeso(rs.getFloat("peso"));
            a.setValor(rs.getFloat("valor"));

            lista.add(a);

        }

        rs.close();

        return lista;

    }
     */
    
    @Override
    public List<Compra> listCompras() throws Exception {

        List<Compra> lista = null;

        PreparedStatement ps = this.con.prepareStatement("select id, data, total, itens, jogador from tb_artefato order by id asc");

        ResultSet rs = ps.executeQuery(); // executa a query        

        lista = new ArrayList();
        while (rs.next()) {

            Compra c = new Compra();
            c.setId(rs.getInt("id"));
            //c.setData(rs.getDate("data")); // não funfa, converter de Calendar para Date
            Calendar data = Calendar.getInstance();
            data.setTimeInMillis(rs.getDate("data").getTime());
            c.setData(data);
            c.setTotal(rs.getFloat("total"));
            // c.setItens((List<ItensCompra>) rs.getObject("itens")); // fazer abaixo           
            PreparedStatement ps2 = this.con.prepareStatement("select ic.id, ic.quantidade, ic.valor from tb_compra_itenscompra cic, tb_compra c where c.id=cic.itens_id and cic.compra_id = ? ");
            ps2.setInt(1, c.getId());
            ResultSet rs2 = ps2.executeQuery();
            while (rs2.next()) {

                ItensCompra ic = new ItensCompra();
                ic.setId(rs2.getInt("id"));
                ic.setQuantidade(rs2.getFloat("quantidade"));
                ic.setValor(rs2.getFloat("valor"));

                c.setItem(ic);
            }
            lista.add(c);
            // c.setJogador((Jogador) rs.getObject("jogador")); // fazer abaixo

        }

        rs.close();

        return lista;

    }

}
