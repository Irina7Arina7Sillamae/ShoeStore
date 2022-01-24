
package facade;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;



public abstract class AbstractFasade<T> {
    protected abstract EntityManager getEntityManager();
    private Class<T> entityClass;

    public AbstractFasade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    
    
    
    public void create(T entity){
        getEntityManager().getTransaction().begin();
        getEntityManager().persist(entity);
        getEntityManager().getTransaction().commit();   
    }          
    public void edit(T entity){
        getEntityManager().getTransaction().begin();
        getEntityManager().merge(entity);
        getEntityManager().getTransaction().commit(); 
    }
    public T find(long id){
        return getEntityManager().find(entityClass, id);
    }
    public List<T> findAll(){
        return getEntityManager().createQuery("SELECT entity FROM " + entityClass.getName() + "entity")
                .getResultList();
    }
}