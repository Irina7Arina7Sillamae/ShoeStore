
package facade;

import entity.History;
import entity.Model;
import javax.persistence.EntityManager;
import tools.Singleton;


public class HistoryFacade extends AbstractFacade<History>{
    private EntityManager em;

    public HistoryFacade(Class<History>entityClass) {
        super(entityClass);
        Singleton singleton = Singleton.getInstance();
        em = singleton.getEntityManager();
    }

    @Override
    protected EntityManager getEntityManager() {
       return em;
    }
    
     public History find(Model book) {
        try {
            return (History) em.createQuery("SELECT h FROM History h WHERE h.model = :model AND h.returnedDate = null")
                    .setParameter("mode3l", book)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    
}
