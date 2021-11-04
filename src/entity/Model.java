
package entity;


public class Model {
    private String manufacturer;
    private String color;
    private int size;
    private int price;
    private int quantity;
    private int count;
 

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "*Model*  " + " производитель: " + manufacturer 
                + " / цвет: " + color 
                + " / размер: " + size 
                + " / цена: " + price + " eur" 
                + " / количество: " + quantity + "шт " 
                + " /в наличии: " + count + "шт";
    }
    
   
    
}
