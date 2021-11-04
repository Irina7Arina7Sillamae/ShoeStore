
package entity;

import java.util.Date;


public class History {
    private Buyer buyer;
    private Model model;
    private Date purchaseDate;

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

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    @Override
    public String toString() {
        return "History{" + "buyer=" + buyer + ", model=" + model + ", purchaseDate=" + purchaseDate + '}';
    }

   
    
    
}