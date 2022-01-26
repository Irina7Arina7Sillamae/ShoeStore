
package tools;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class Singleton {
    private static Singleton instance;
    private EntityManager entityManager;
    private Singleton(){
        EntityManagerFactory emf =Persistence.createEntityManagerFactory("ShoeStorePU");
        entityManager = emf.createEntityManager();
    }
    public static Singleton getInstance(){
        if(instance == null){		//если объект еще не создан
            instance = new Singleton();	//создать новый объект
        }
        return instance;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
    
}
