
package entity;

import java.io.Serializable;


public class Buyer implements Serializable {
    private String name;
    private String phone;
    private int purse;

    public Buyer() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getPurse() {
        return purse;
    }

    public void setPurse(int purse) {
        this.purse = purse;
    }

    @Override
    public String toString() {
        return "* Покупатель * " + " имя: " + name + " / тел: " + phone + " / деньги: " + purse + "eur" ;
    }
    
    
}
