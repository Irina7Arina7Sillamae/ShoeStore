
package entity;

import java.io.Serializable;
import java.util.Date;


public class History implements Serializable {
    private Buyer buyer;
    private Model model;
    private Date DateOfSale;

    public History() {
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Date getDateOfSale() {
        return DateOfSale;
    }

    public void setDateOfSale(Date DateOfSale) {
        this.DateOfSale = DateOfSale;
    }

    @Override
    public String toString() {
        return "History{" + "buyer=" + buyer + ", model=" + model + ", DateOfSale=" + DateOfSale + '}';
    }

   

   
    
    
}