package myclasses;

import entity.Buyer;
import entity.History;
import entity.Model;
import interfases.Keeping;
import java.util.ArrayList;
import java.util.Calendar;
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
            System.out.println("--------------------");
            System.out.println("*** Выберите номер задачи: ***");
            System.out.println("0: Закончить программу");
            System.out.println("1: Добавить модель");
            System.out.println("2: Список обуви");
            System.out.println("3: Добавить покупателя");
            System.out.println("4: Список покупателей");
            System.out.println("5: Покупка покупателем обуви");
            System.out.println("6: Список продаж");
            System.out.println("7: Список доходов по датам продаж");
            System.out.println("8: Доход за все время продаж");
            System.out.println("9: Добавление денег покупателю ");
            
            int task = scanner.nextInt(); scanner.nextLine();
            int n = 0;
            switch (task) {
                 
                case 0:
                    repeat="q";
                    System.out.println("Пока!");
                    break;
                case 1:
                    addModel();
                    break;
                case 2:
                    printListModels();
                    break;
                case 3:
                    addBuyer();
                    break;
                case 4:
                    printListBuyers();
                    break;
                case 5:
                    addHistory();
                    break;
                case 6:
                    printListSales();
                    break;
                
                case 7:
                    printListIncomeByDateOfSale();
                    break;
                    
                case 8:
                    printListTotalIncome();
                    break;
                case 9:
                    addMoney();
                    break;
                 
                default:
                    System.out.println("*** Выберите цифру из списка! ***");
            }
        }while("r".equals(repeat));
    }
    
    private void addModel(){
        System.out.println("--------------------");
        System.out.println("*** Добавление модели обуви ***");
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
        models.add(model);
        keeping.saveModels(models);
    }
   
    
    private void addBuyer(){
        System.out.println("-------------------");
        System.out.println("*** Добавление покупателя ***");
        Buyer buyer = new Buyer();
        System.out.println("Имя покупателя: ");
        buyer.setName(scanner.nextLine());
        System.out.println("Телефон покупателя: ");
        buyer.setPhone(scanner.nextLine()); 
        System.out.println("Деньги покупателя: ");
        buyer.setMoney(scanner.nextInt());scanner.nextLine();
        buyers.add(buyer);
        keeping.saveBuyers(buyers);   
    }

        private void addHistory() {
        System.out.println("---------------");
        System.out.println("*** Продажа обуви ***");
        System.out.println("*** Список обуви ***");
        History history = new History();
        int n = 0;
        for (int i = 0; i < models.size(); i++) {
            if(models.get(i) != null && models.get(i).getCount() > 0){
                System.out.println((i+1)
                        +". " + "производитель: " + models.get(i).getManufacturer()
                        +" / " + "цвет: " + models.get(i).getColor()
                        +" / " + "цена: " + models.get(i).getPrice() + "eur"
                        +" / " + "размер: " + models.get(i).getSize()
                        +" / " + "в наличии: " + models.get(i).getCount() + "шт"     
                );
                 n++;
            }
        } 
         if (n < 1){
            System.out.println("*** Нет обуви для продажи! ***");
            return;
            }
        
        System.out.println("---------------");
        System.out.print("*** Выберите номер модели: ***   ");
        int numberModel = scanner.nextInt(); scanner.nextLine();
        System.out.println("---------------");
        System.out.println("*** Продажа обуви ***");
        System.out.println("*** Список покупателей ***");
     
        for (int i = 0; i < buyers.size(); i++) {
            if(buyers.get(i) != null /*&& buyers.get(i).getMoney() > models.get(i).getPrice()*/){
                System.out.println(i+1+". "+buyers.get(i).toString());
                
                 n++;
            }
        } 
         if (n < 1){
            System.out.println("*** Покупателей нет! ***");
            return;
            }
        
        System.out.println("---------------");
        System.out.print("*** Выберите номер покупателя: ***   ");
        int numberBuyer = scanner.nextInt(); scanner.nextLine();
        history.setModel(models.get(numberModel-1));
        history.setBuyer(buyers.get(numberBuyer-1));
        Calendar c = new GregorianCalendar();
        history.setDateOfSale(c.getTime());
        history.getModel().setCount(history.getModel().getCount() - 1);
        history.getBuyer().setMoney(history.getBuyer().getMoney() - history.getModel().getPrice());
        keeping.saveModels(models);
        keeping.saveBuyers(buyers);
        histories.add(history);
        keeping.saveHistories(histories);
        System.out.println("-------------------");
        System.out.println("* Обувь продана:   " + history.getModel().getManufacturer()
                + " цена: " + history.getModel().getPrice() + "eur"
                + " / цвет: " + history.getModel().getColor()
                + " / размер: " + history.getModel().getSize()
                + " / покупатель: "+history.getBuyer().getName()
                + " / тел: " + history.getBuyer().getPhone()
        );
        
       
     
    }
        private void printListModels() {
            System.out.println("-------------------");
            System.out.println("*** Список обуви в продаже ***");
            int n = 0;
                for (int i = 0; i < models.size(); i++) {
                    if(models.get(i) != null && models.get(i).getCount() > 0){
                        System.out.println(models.get(i).toString());
                         n++;
                    }
                } 
        if (n < 1){
            System.out.println("*** Нет обуви для продажи! ***");
           
        }      
                    
        }
        private void printListBuyers() {
            System.out.println("---------------");
            System.out.println("*** Список покупателей ***");
            int n = 0;
                for (int i = 0; i < buyers.size(); i++) {
                    if(buyers.get(i) != null){
                        System.out.println(buyers.get(i).toString());
                        n++;
                    }
                } 
        if (n < 1){
            System.out.println("*** Покупателей нет! ***");
            
        }
        }
        
        
        
        private void printListSales(){
            System.out.println("-------------------");
            System.out.println("*** Список продаж ***");
            int n = 0;
            for (int i = 0; i < histories.size(); i++) {
                if(histories.get(i).getModel() != null && histories.get(i).getModel().getCount() > 0) {                          
                    System.out.println( i+1 + ". " 
                        + "производитель: " + histories.get(i).getModel().getManufacturer()
                        + " / цвет: " +  histories.get(i).getModel().getColor()
                        + " / цена: " + histories.get(i).getModel().getPrice() + "eur "
                        + " / размер: " + histories.get(i).getModel().getSize()
                        + " / покупатель: " + histories.get(i).getBuyer().getName()
                        + " / тел: " + histories.get(i).getBuyer().getPhone()
                    );
                    n++;
                }
            } 
            if (n < 1){
                System.out.println("*** Товар пока не продавался! ***");
            }
        }
        
        private void printListIncomeByDateOfSale() {
            System.out.println("-------------------");
            System.out.println("*** Список доходов по датам продаж ***");                      
            int n = 0;
            for (int i = 0; i < histories.size(); i++) {
                if(histories.get(i).getModel() != null && histories.get(i).getModel().getCount()> 0) {
                    System.out.println( "*** сумма: " + histories.get(i).getModel().getPrice() + "eur *** " + histories.get(i).getDateOfSale()
                    );
            n++;
                    }
            } 
            if (n < 1){
            System.out.println("*** Товар пока не продавался! ***");
                
            }
        }
        
        private void printListTotalIncome() {
            System.out.println("-------------------");
                System.out.println("*** Доход за время продаж ***");
                int n = 0;
                int sum = 0; 
                for (int i = 0; i < histories.size(); i++) {
                    if(histories.get(i).getModel() != null && histories.get(i).getModel().getCount()> 0) {
                        sum = sum + histories.get(i).getModel().getPrice(); 
                    n++;
                    } 
                } 
                System.out.println("*** общая сумма продаж:   " + sum + "   eur"); 
                if (n < 1){
                    System.out.println("*** Товар пока не продавался! ***");
                }
        }
        
        private void addMoney(){
            System.out.println("---------------");
            System.out.println("*** Добавить денег покупателю ***");
            History history = new History(); 
            System.out.println("*** Список покупателей ***");
            int n = 0;
                for (int i = 0; i < buyers.size(); i++) {
                    if(buyers.get(i) != null){
                        System.out.println((i+1) + ".   " + buyers.get(i).toString());
                         n++;
            }
        } 
         if (n < 1){
            System.out.println("*** Покупателей нет! ***");
            return;
            }
        
           System.out.println("---------------");
           System.out.print("*** Выберите номер покупателя:   ***   ");
           int numBuyer = scanner.nextInt(); scanner.nextLine();
           System.out.println("*** Укажите сумму, которую хотите добавить   ***");
           int addMoney = scanner.nextInt();scanner.nextLine();
           history.setBuyer(buyers.get(numBuyer-1));
           history.getBuyer().setMoney(history.getBuyer().getMoney() + addMoney);
           histories.add(history);
           keeping.saveBuyers(buyers);
           keeping.saveHistories(histories);
           System.out.println("--------------------");
           System.out.println( " Покупателю: " + buyers.get(numBuyer-1).getName()
                        + " / Добавлены денеги в сумме: " + addMoney + " eur"
                        + " / Всего у покупателя: " +  buyers.get(numBuyer-1).getMoney() + " eur");                
            }  
    
        }
       
        
        
       
        
       
        