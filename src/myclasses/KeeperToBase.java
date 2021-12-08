
package myclasses;

import entity.Buyer;
import entity.History;
import entity.Model;
import interfases.Keeping;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;





public class KeeperToBase implements Keeping{
       private EntityManagerFactory emf = Persistence.createEntityManagerFactory("ShoeStorePU");
       private EntityManager em = emf.createEntityManager();
       private EntityTransaction tx = em.getTransaction();

    @Override
    public void saveModels(List<Model> models) {
        tx.begin();
            for (int i = 0; i < models.size(); i++) {
                if(models.get(i).getId() == null) {
                em.persist(models.get(i)); 
                }
            }
        tx.commit();
    }

    @Override
    public List<Model> loadModels() {
       try {
           return (List<Model>)em.createQuery("SELECT model FROM Model model")
                   .getResultList();
       } catch (Exception e) {
           System.out.println("Таблица MODEL пуста");
       }
       return new ArrayList<>();
    }

    @Override
    public void saveBuyers(List<Buyer> buyers) {
        tx.begin();
            for (int i = 0; i < buyers.size(); i++) {
                if(buyers.get(i).getId() == null) {
                em.persist(buyers.get(i)); 
                }
            }
        tx.commit();
    }

    @Override
    public List<Buyer> loadBuyers() {
         try {
           return (List<Buyer>)em.createQuery("SELECT buyer FROM Buyer buyer")
                   .getResultList();
       } catch (Exception e) {
           System.out.println("Таблица BUYER пуста");
       }
       return new ArrayList<>();
    }

    @Override
    public void saveHistories(List<History> histories) {
        tx.begin();
            for (int i = 0; i < histories.size(); i++) {
                if(histories.get(i).getId() == null) {
                em.persist(histories.get(i)); 
                }
            }
        tx.commit();
    }

    @Override
    public List<History> loadHistories() {
         try {
           return (List<History>)em.createQuery("SELECT history FROM History history")
                   .getResultList();
       } catch (Exception e) {
           System.out.println("Таблица HISTORY пуста");
       }
       return new ArrayList<>();
    }

}