
package facade;

import entity.Buyer;
import javax.persistence.EntityManager;
import tools.Singleton;




public class BuyerFacade extends AbstractFacade<Buyer>{
    
    private EntityManager em;

    public BuyerFacade(Class<Buyer>entityClass) {
        super(entityClass);
        Singleton singleton = Singleton.getInstance();
        em = singleton.getEntityManager();
    }

    @Override
    protected EntityManager getEntityManager() {
       return em;
    }
    
}
