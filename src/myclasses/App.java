
package myclasses;

import entity.Buyer;
import entity.History;
import entity.Model;
import interfases.Keeping;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;


public class App {
    private Scanner scanner = new Scanner(System.in);
    private List<Model> models = new ArrayList<>();
    private List<Buyer> buyers = new ArrayList<>();
    private List<History> histories = new ArrayList<>();
    private Keeping keeping = (Keeping) new KeeperToFile();
    
    public App() {
        models = keeping.loadModels();
        buyers = keeping.loadBuyers();
        histories = keeping.loadHistories();
    }

    public void run() {
        String repeat = "r";
        do{
            System.out.println("Выберите номер задачи:");
            System.out.println("0: Закончить программу");
            System.out.println("1: Добавить модель");
            System.out.println("2: Список полученных моделей");
            System.out.println("3: Добавить покупателя");
            System.out.println("4: Список покупателей");
            System.out.println("5: Покупка покупателем обуви");
            System.out.println("6: Список проданной обуви");
            System.out.println("7: Доход магазина за все время работы");
            System.out.println("8: Добавить денег покупателю ");
            
            int task = scanner.nextInt(); scanner.nextLine();
            switch (task) {
                case 0:
                    repeat="q";
                    System.out.println("Пока!");
                    break;
                case 1:
                    System.out.println("*** Добавление модели ***");
                    models.add(addModel());
                    keeping.saveModels(models);
                    break;
                case 2:
                    System.out.println("-------------------");
                    System.out.println("*** Список полученных моделей ***");
                    for (int i = 0; i < models.size(); i++) {
                        if(models.get(i) != null && models.get(i).getCount() > 0){
                            System.out.println(models.get(i).toString());
                        }
                        
                    }
                    System.out.println("-------------------");
                    break;
                case 3:
                    System.out.println("*** Добавление покупателя ***");
                    buyers.add(addBuyer());
                    keeping.saveBuyers(buyers);
                    break;
                case 4:
                    System.out.println("---------------");
                    System.out.println("*** Список покупателей ***");
                    for (int i = 0; i < buyers.size(); i++) {
                        if(buyers.get(i) != null){
                            System.out.println(buyers.get(i).toString());
                        }
                    }
                    System.out.println("---------------");
                    break;
                case 5:
                    System.out.println("---------------");
                    System.out.println("*** Покупка покупателем обуви ***");
                    History history = addHistory();
                    histories.add(history);
                    keeping.saveHistories(histories);
                    System.out.println("-------------------");
                    System.out.println("*** Товар *** производитель:  "+history.getModel().getManufacturer()
                                        + " / цвет: " + history.getModel().getColor()
                                        + " / размер: " + history.getModel().getSize()
                                        + " / цена: " + history.getModel().getPrice() + "eur"
                                        + " / продан покупателю "+history.getBuyer().getName()
                                        + " / тел: " +history.getBuyer().getPhone()
                    );
                    System.out.println("-------------------");
                    break;
                case 6:
                    System.out.println("-------------------");
                    System.out.println("*** Список проданной обуви ***");  
                    System.out.println("-------------------");
                    int n = 0;
                    for (int i = 0; i < histories.size(); i++) {
                        if(histories.get(i) != null
                            && histories.get(i).getModel().getCount() 
                            <  histories.get(i).getModel().getQuantity()
                         ){
                    System.out.printf("%d. * Книгу \"%s\" читает %s %s%n"
                        ,i+1
                        ,histories.get(i).getModel().getManufacturer()
                        ,histories.get(i).getModel().getColor()
                        ,histories.get(i).getModel().getPrice()
                        ,histories.get(i).getModel().getSize()
                        ,histories.get(i).getBuyer().getName()
                        ,histories.get(i).getBuyer().getPhone()
                    );
                    n++;
                        }
                    }
                
            if(n < 1){
                System.out.println("*** Товара нет! ***");
                System.out.println("-------------------");
            }
                return;
                   /* case 7:
                    System.out.println("*** Доход магазина за все время работы ***");
                    
                case 8:
                    System.out.println("*** Добавить денег покупателю ***");
                     */
                default:
                    System.out.println("*** Выберите цифру из списка! ***");;
            }
        }while("r".equals(repeat));
    }
    private Model addModel(){
        Model model = new Model();
        System.out.print("Введите производителя: ");
        model.setManufacturer(scanner.nextLine());
        System.out.print("Введите цвет модели: ");
        model.setColor(scanner.nextLine());
        System.out.print("Введите размер модели: ");
        model.setSize(scanner.nextInt());scanner.nextLine();
        System.out.print("Введите цену модели: ");
        model.setPrice(scanner.nextInt());scanner.nextLine();
        System.out.print("Введите количество полученной обуви: ");
        model.setQuantity(scanner.nextInt());scanner.nextLine();
        model.setCount(model.getQuantity());

        return model;
    }
   
    
    private Buyer addBuyer(){
        Buyer buyer = new Buyer();
        System.out.println("Имя покупателя: ");
        buyer.setName(scanner.nextLine());
        System.out.println("Телефон покупателя: ");
        buyer.setPhone(scanner.nextLine()); 
        System.out.println("Деньги покупателя: ");
        buyer.setPurse(scanner.nextInt());scanner.nextLine();
        
        return buyer;
    }

        private History addHistory() {
        History history = new History();
        System.out.println("*** Список обуви ***");
        for (int i = 0; i < models.size(); i++) {
            if(models.get(i) != null){
                System.out.println(i+1
                        +". " + "производитель: " + models.get(i).getManufacturer()
                        +" / " + "цвет: " + models.get(i).getColor()
                        +" / " + "цена: " + models.get(i).getPrice() + "eur"
                        +" / " + "размер: " + models.get(i).getSize()
                        +" / " + "в наличии: " + models.get(i).getCount() + "шт"
                        
                );
            }
        }
        System.out.println("---------------");
        System.out.print("*** Выберите номер модели: ***   ");
        int numberModel = scanner.nextInt(); scanner.nextLine();
        System.out.println("---------------");
        System.out.println("*** Список покупателей ***");
        for (int i = 0; i < buyers.size(); i++) {
            if(buyers.get(i) != null){
                System.out.println(i+1+". "+buyers.get(i).toString());
            }
        }
        System.out.println("---------------");
        System.out.print("*** Выберите номер покупателя: ***   ");
        int numberBuyer = scanner.nextInt(); scanner.nextLine();
        history.setModel(models.get(numberModel-1));
        history.setBuyer(buyers.get(numberBuyer-1));
        Calendar c = new GregorianCalendar();
        history.setDateOfSale(c.getTime());
        
        return history;             
    }
    
}
