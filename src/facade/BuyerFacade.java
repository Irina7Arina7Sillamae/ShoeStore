
package facade;

import entity.Buyer;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;




public class BuyerFacade extends AbstractFasade<Buyer>{
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("ShoeStorePU");
    private EntityManager em = emf.createEntityManager();
    private EntityTransaction tx = em.getTransaction();

    public BuyerFacade(Class<Buyer> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
       return em;
    }
    
}
