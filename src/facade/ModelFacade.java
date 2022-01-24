
package facade;

import entity.Model;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;


public class ModelFacade extends AbstractFasade<Model>{
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("ShoeStorePU");
    private EntityManager em = emf.createEntityManager();
    private EntityTransaction tx = em.getTransaction();

    public ModelFacade(Class<Model> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
