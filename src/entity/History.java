
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;


@Entity
public class History implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Buyer buyer;
    @OneToOne
    private Model model;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dateOfSale;

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
        return dateOfSale;
    }

    public void setDateOfSale(Date DateOfSale) {
        this.dateOfSale = DateOfSale;
    }

    @Override
    public String toString() {
        return "History{" + "buyer=" + buyer 
                + ", model=" + model 
                + ", DateOfSale=" + dateOfSale + '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
   
    
}