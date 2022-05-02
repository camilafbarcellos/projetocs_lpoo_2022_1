
package br.edu.ifsul.cc.lpoo.cs.model.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author 20202pf.cc0003
 */
public class PersistenciaJPA implements InterfacePersistencia {
    public EntityManagerFactory factory;    //fabrica de gerenciadores de entidades
    public EntityManager entity;            //gerenciador de entidades JPA

    
    public PersistenciaJPA(){
        
        //parametro: é o nome da unidade de persistencia (Persistence Unit)
        factory = Persistence.createEntityManagerFactory("pu_db_lpoo_cs_2022_1"); // tag name no persistence.xml
        entity = factory.createEntityManager();
    }
}
