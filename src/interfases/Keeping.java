
package interfases;

import entity.Buyer;
import entity.History;
import entity.Model;
import java.util.List;


public interface Keeping {
    public void saveModels(List<Model> models);
    public List<Model> loadModels();
    public void saveBuyers(List<Buyer> buyers);
    public List<Buyer> loadBuyers();
    public void saveHistories(List<History> histories);
    public List<History> loadHistories();
}
