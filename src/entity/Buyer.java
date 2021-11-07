
package entity;

import java.io.Serializable;


public class Buyer implements Serializable {
    private String name;
    private String phone;
    private int money;
    private int putMoney;
    
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

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "* Покупатель *" + "   имя: " + name + " / тел: " + phone + " / деньги: " + money + " eur" ;
    }

    

    public int getPutMoney() {
        return putMoney;
    }

    public void setPutMoney(int putMoney) {
        this.putMoney = putMoney;
    }
    
    
}
