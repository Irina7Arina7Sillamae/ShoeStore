
package myclasses;

import entity.Buyer;
import entity.History;
import entity.Model;
import interfases.Keeping;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.Date;

public class App {
    private Scanner scanner = new Scanner(System.in);
    //----- Данные магазина -----
    private List<Model> models = new ArrayList<>();
    private List<Buyer> buyers = new ArrayList<>();
    private List<History> histories = new ArrayList<>();
    //----- Сохранение -----
    //private Keeping keeping = new KeeperToFile();
    private Keeping keeping =  new KeeperToBase();
    
    public App() {
        models = keeping.loadModels();
        buyers = keeping.loadBuyers();
        histories = keeping.loadHistories();
    }

    public void run() {
        String repeat = "r";
        do{
            System.out.println("");
            System.out.println("--------------------");
            System.out.println("*** Выберите номер задачи: ***");
            System.out.println("--------------------");
            System.out.println("   0 : Закончить программу");
            System.out.println("   1 : Добавить модель");
            System.out.println("   2 : Список обуви");
            System.out.println("   3 : Добавить покупателя");
            System.out.println("   4 : Список покупателей");
            System.out.println("   5 : Покупка покупателем обуви");
            System.out.println("   6 : Список продаж");
            System.out.println("   7 : Список доходов по датам продаж");
            System.out.println("   8 : Доход за все время продаж");
            System.out.println("   9 : Добавление денег покупателю ");
            System.out.println("  10 : Редактировать товар" );
            System.out.println("  11 : Редактировать покупателя ");
            System.out.println("  12 : Список распроданной обуви");
            System.out.println("  13 : Доход магазина за указанный месяц");
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
                case 10:
                    editProduct();
                    break;
                case 11:
                    editBuyer();
                    break;
                case 12:
                    printListAllSold();
                    break;
               /* case 13:
                    incomeMonth();
                    break;*/
                default:
                    System.out.println("*** Выберите цифру из списка! ***");
            }
        }while("r".equals(repeat));
    }
    
    private void addModel(){
         if(isQuit()){
            return;
        }
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
         if(isQuit()){
            return;
        }
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
            System.out.println("*** Выберите номер обуви для покупки: ***");
            History history = new History();
            Set<Integer> setNumbersModels = printListModels();
            if(setNumbersModels.isEmpty()){
                System.out.println("*** В списке нет обуви ***");
                return;
            }
            int numberModel = insertNumber(setNumbersModels);
            
            Set<Integer> setNumbersBuyers = printListBuyersForSale();
            if(setNumbersBuyers.isEmpty()){
                System.out.println("*** В списке нет покупателей ***");
                return;
            }
            System.out.print("*** Выберите номер покупателя: ***   ");
            int numberBuyer = insertNumber(setNumbersBuyers);
            
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
            System.out.println("*** ПРОДАНО:   " + history.getModel().getManufacturer()
                + " цена: " + history.getModel().getPrice() + "eur"
                + " / цвет: " + history.getModel().getColor()
                + " / размер: " + history.getModel().getSize()
                + " / покупатель: "+history.getBuyer().getName()
                + " / тел: " + history.getBuyer().getPhone()
            );
        
            }
        
        private Set<Integer> printListModels() {
            System.out.println("---------------");
            System.out.println("*** Список обуви в продаже ***");
            Set<Integer> setNumbersModels = new HashSet<>();
                for (int i = 0; i < models.size(); i++) {
                    if(models.get(i) != null && models.get(i).getCount() > 0){
                        System.out.printf("%3d   %-15s %-10s size: %-5d %5d eur    Всего: %3d tk    В наличии: %3d tk %n"
                        ,i+1
                        ,models.get(i).getManufacturer()
                        ,models.get(i).getColor()
                        ,models.get(i).getSize()
                        ,models.get(i).getPrice()
                        ,models.get(i).getQuantity()
                        ,models.get(i).getCount()
                    );
                         setNumbersModels.add(i+1);
                    }
                } 
           return setNumbersModels;
        } 
        private Set<Integer> printListAllModels() {
            System.out.println("---------------");
            System.out.println("*** Список обуви ***");
            Set<Integer> setNumbersModels = new HashSet<>();
            for (int i = 0; i < models.size(); i++) {
                if(models.get(i) != null && models.get(i).getCount() >= 0){
                    System.out.printf("%3d   %-12s %-10s size: %-4d %5d eur     Всего: %3d tk     В наличии: %3d tk %n"
                        ,i+1
                        ,models.get(i).getManufacturer()
                        ,models.get(i).getColor()
                        ,models.get(i).getSize()
                        ,models.get(i).getPrice()
                        ,models.get(i).getQuantity()
                        ,models.get(i).getCount()
                );
                setNumbersModels.add(i+1);
            }
        }
        System.out.println("-------------------");
        return setNumbersModels;
    }
        
        private Set<Integer> printListBuyers() {
            
            System.out.println("---------------");
            System.out.println("*** Список покупателей ***");
            Set<Integer> setNumbersBuyers = new HashSet<>();
                for (int i = 0; i < buyers.size(); i++) {
                    if(buyers.get(i) != null){
                         System.out.printf("%3d   %-22s  тел: %-12s %5d eur%n"
                        ,i+1
                        ,buyers.get(i).getName()
                        ,buyers.get(i).getPhone()
                        ,buyers.get(i).getMoney()
                        );
                        setNumbersBuyers.add(i+1);
                    }
                }
                return setNumbersBuyers;
    } 
        
         private Set<Integer> printListBuyersForSale() {
             System.out.println("---------------");
            System.out.println("*** Список покупателей ***");
            Set<Integer> setNumbersBuyers = new HashSet<>();
                for (int i = 0; i < buyers.size(); i++) {
                    if(buyers.get(i) != null && buyers.get(i).getMoney() > (models.get(i).getPrice()-1)){
                         System.out.printf("%3d   %-22s  тел: %-12s %5d eur%n"
                        ,i+1
                        ,buyers.get(i).getName()
                        ,buyers.get(i).getPhone()
                        ,buyers.get(i).getMoney()
                        );
                        setNumbersBuyers.add(i+1);
                    }
                }
                return setNumbersBuyers;
    }
        
        
        
        private Set<Integer> printListSales(){
            System.out.println("-------------------");
            System.out.println("*** Список продаж ***");
            Set<Integer> setNumbersModels = new HashSet<>();
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
                    setNumbersModels.add(i+1);
                }
            } 
            if(setNumbersModels.isEmpty()){
            System.out.println("Продаж не было!");
            System.out.println("-------------------");
        }
        return setNumbersModels;
}
    
        private void printListIncomeByDateOfSale() {
            System.out.println("-------------------");
            System.out.println("*** Список доходов по датам продаж ***");                      
            int n = 0;
            for (int i = 0; i < histories.size(); i++) {
                if(histories.get(i).getModel() != null && histories.get(i).getModel().getCount()> 0) {
                    System.out.println( "*** сумма: " + histories.get(i).getModel().getPrice() + "eur *** " 
                            + histories.get(i).getDateOfSale()
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
           System.out.println( " *** " + buyers.get(numBuyer-1).getName()
                        + " / Добавлены денеги в сумме: " + addMoney + " eur"
                        + " / Всего у покупателя: " +  buyers.get(numBuyer-1).getMoney() + " eur"); 
            //System.out.println("Вот!");
            } 

    private int getNumber() {
        int number;
        do{
            String strNumber = scanner.nextLine();
            try {
                number = Integer.parseInt(strNumber);
                return number;
            } catch (NumberFormatException e) {
                System.out.println("Попробуй еще раз: ");
            }
        }while(true);
    }

    private int insertNumber(Set<Integer> setNumbers) {
        int number;
        do{
            number = getNumber();
            if(setNumbers.contains(number)){
                return number;
            }
            System.out.println("Попробуй еще: ");
        }while(true);
    }

    private void editProduct() {
        System.out.println("---------------");
        System.out.println("*** Редактировать товар ***");
        if(isQuit()){
            return;
        }
        /*
        *Выводим список обуви
        *выбираем номер обуви
        *показать сожержимое полей обуви
        *запрос на изменение поля
        *ввести новое значение поля
        ***повторить для всех полей 
        *сохранить данные
        */
         Set<Integer> changeNumbers = new HashSet<>();
        changeNumbers.add(1);
        changeNumbers.add(2);
        Set<Integer> setNumbersModels = printListAllModels();
        if(setNumbersModels.isEmpty()){
            System.out.println("*** Обуви в продаже нет! ***");
            return;
        }
        System.out.println("*** Введите номер обуви:");
        int numberModel = insertNumber(setNumbersModels);
        //первое поле
        System.out.println("Производитель: " + models.get(numberModel - 1).getManufacturer());
        System.out.println("*** Если нужно изменить, нажми 1, если пропустить, нажми 2");
        int change = insertNumber(changeNumbers);
        if(1 == change){
            System.out.println("*** Введите нового производителя: ");
            models.get(numberModel - 1).setManufacturer(scanner.nextLine());
        }
        //второе поле
        System.out.println("Цвет: " + models.get(numberModel - 1).getColor());
        System.out.println("*** Если нужно изменить цвет, нажми 1, если пропустить, нажми 2");
        change = insertNumber(changeNumbers);
        if(1 == change){
            System.out.println("*** Введите новый цвет: ");
            models.get(numberModel - 1).setColor(scanner.nextLine());
        }
        //третье поле
        System.out.println("Размер: " + models.get(numberModel - 1).getSize());
        System.out.println("*** Если нужно изменить размер, нажми 1, если пропустить, нажми 2");
        change = insertNumber(changeNumbers);
        if(1 == change){
            System.out.println("*** Введите новый размер: ");
            models.get(numberModel - 1).setSize(getNumber());
        }
        //четвертое поле
        System.out.println("Цена: " + models.get(numberModel - 1).getPrice());
        System.out.println("*** Если нужно изменить цену, нажми 1, если пропустить, нажми 2");
        change = insertNumber(changeNumbers);
        if(1 == change){
            System.out.println("*** Введите новую цену: ");
            models.get(numberModel - 1).setPrice(scanner.nextInt());
        }
        //пятое поле
        System.out.println("*** Количество: " + models.get(numberModel - 1).getQuantity());
        System.out.printf("*** Если нужно изменить общее количество обуви,%n нажми 1, если пропустить, нажми 2");
        change = insertNumber(changeNumbers);
        if(1 == change){
            System.out.println("*** Введите новое количество обуви: ");
            int oldQuantity = models.get(numberModel - 1).getQuantity();
            int oldCount = models.get(numberModel - 1).getCount();
            int newQuantity;
            do{
                newQuantity = getNumber();
                //if(newQuantity >= oldQuantity - oldCount){
                if(newQuantity >= oldQuantity - oldCount){
                    break;
                }
                System.out.println("*** Обуви должно быть больше. ***");
            }while(true);
            int newCount = oldCount + (newQuantity - oldQuantity);
            
            models.get(numberModel - 1).setQuantity(newQuantity);
            models.get(numberModel - 1).setCount(newCount);
        } 
     keeping.saveModels(models);   
    }

    /*private void editBuyer() {
        System.out.println("---------------");
        System.out.println("*** Редактировать покупателя ***");
        if(isQuit()){
            return;
        }
        /*
        *Выводим список покупателей
        *выбираем номер покупателя
        *показать сожержимое полей покупателя
        *запрос на изменение поля
        *ввести новое значение поля
        ***повторить для всех полей 
        *сохранить данные
        */
    
     /*   Set<Integer> changeNumbers = new HashSet<>();
        changeNumbers.add(1);
        changeNumbers.add(2);
        Set<Integer> setNumbersBuyers = printListBuyers();
        if(setNumbersBuyers.isEmpty()){
            System.out.println("*** Покупателей нет! ***");
            return;
        }
        System.out.println("*** Введите номер покупателя:");
        int numberBuyer = insertNumber(setNumbersBuyers);
        //первое поле
        System.out.println("Покупатель: " + buyers.get(numberBuyer - 1).getName());
        System.out.println("*** Если нужно изменить, нажми 1, если пропустить, нажми 2");
        int change = insertNumber(changeNumbers);
        if(1 == change){
            System.out.println("*** Введите имя нового покупателя: ");
            buyers.get(numberBuyer - 1).setName(scanner.nextLine());
        }
        //второе поле
       System.out.println("Телефон: " + buyers.get(numberBuyer - 1).getPhone());
        System.out.println("*** Если нужно изменить, нажми 1, если пропустить, нажми 2");
        change = insertNumber(changeNumbers);
        if(1 == change){
            System.out.println("*** Введите новый номер: ");
            buyers.get(numberBuyer - 1).setPhone(scanner.nextLine()); 
        }
        //третье поле
        System.out.println("Сумма денег: " + buyers.get(numberBuyer - 1).getMoney());
        System.out.println("*** Если нужно изменить сумму денег, нажми 1, если пропустить, нажми 2");
        change = insertNumber(changeNumbers);
        if(1 == change){
            System.out.println("*** Введите другую сумму: ");
            buyers.get(numberBuyer - 1).setMoney(getNumber());
        }
     keeping.saveBuyers(buyers);   
    }
*/
    
    private void editBuyer() {
        System.out.println("*** Выберите покупателя, которого хотитие изменить: ***");
        int n=0;
        for (int i = 0; i < buyers.size(); i++) {
            if (buyers.get(i)!=null) {
                System.out.printf("%d. %s. тел.: %s%n.  eur: %s%n."
                ,i+1
                ,buyers.get(i).getName()
                ,buyers.get(i).getPhone()
                ,buyers.get(i).getMoney()
                );
            }
            n++;
        }
        if (n<1) {
            System.out.println("*** Нет зарегистрированных покупателей");
            return;
        }
        System.out.print("*** Выберите номер покупателя: ***");
        int numberBuyer= getNumber();
        String repeat="yes";
        do{
            System.out.println(" 0: Выход");
            System.out.println(" 1: Изменить имя покупателя");
            System.out.println(" 2: Изменить номер покупателя");
            System.out.println("*** Выберите номер покупателя, данные которого хотите изменить: ");
            int num=getNumber();
            switch(num){
                case 0:
                    repeat="no";
                    break;
                case 1:
                    System.out.print("Введите новое имя покупателя: ");
                    
                    buyers.get(numberBuyer - 1).setName(scanner.nextLine()); 
                    keeping.saveBuyers(buyers);
                    break;
                case 2:
                    System.out.print("Введите новый номер покупателя: ");
                    String newPhone=scanner.nextLine();
                    buyers.get(numberBuyer - 1).setPhone(newPhone);
                    keeping.saveBuyers(buyers);
                    break;
                case 3:
                    System.out.print("Введите другое количество денег: ");
                    int newMoney=scanner.nextInt();
                    buyers.get(numberBuyer - 1).setMoney(newMoney);
                    keeping.saveBuyers(buyers);
                    break;
            }
         }while("yes".equals(repeat));
   
    
    }

    private boolean isQuit(){
        System.out.println("*** Чтобы закончить задачу нажми \"q\" ***");
        System.out.println("*** Чтобы продолжить, нажми любую другую клавишу. ***");
        String q = scanner.nextLine();
        if("q".equals(q)){
            return true;
        }else{
            return false;
        }
    }

       private Set<Integer> printListAllSold() {
            System.out.println("*** Список распроданной обуви ***");
            Set<Integer> setNumbersModels = new HashSet<>();
                for (int i = 0; i < models.size(); i++) {
                    if(models.get(i) != null && models.get(i).getCount() == 0){
                        System.out.printf("%3d   %-12s %-10s size: %-5d %5d eur    Всего: %3d tk    В наличии: %3d tk %n"
                        ,i+1
                        ,models.get(i).getManufacturer()
                        ,models.get(i).getColor()
                        ,models.get(i).getSize()
                        ,models.get(i).getPrice()
                        ,models.get(i).getQuantity()
                        ,models.get(i).getCount()
                    );
                         setNumbersModels.add(i+1);
                    }
                } 
           return setNumbersModels;
        } 

  
    /*private void incomeMonth() {
       /* java.util.Date date {
        java.uti= new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
*/

        
        /*System.out.println("*** Доход за месяц ***");
        double income = 0;
        System.out.println("*** Введите год, за который надо вывести доход: ***");
        int year = getNumber();
        System.out.println("*** Введите месяц (1-12), за который надо вывести доход: ***");
        int chosenMonths = getNumber()-1;
        for (int i = 0; i < histories.size(); i++) {
            Date date = histories.get(i).getDateOfSale();
            boolean toSum = summator(date, chosenMonths, year);
            if ( histories.get(i)!=null && toSum) {
                income += histories.get(i).getModel().getPrice();
            }
        }
        System.out.println("*** Доход магазина: ***");
        System.out.println("*** Выручка магазина составляет: ***" + income);
        }

    private boolean summator(Date date, int chosenMonths, int year) {
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        return month != chosenMonths;
        */
    
       /*
       int sum = 0 ;
        Calendar c = new GregorianCalendar();
        System.out.println("*** Доход за месяц ***");
        System.out.println("*** Введите месяц (1-12), за который надо вывести доход: ***");
        //Set<Integer> setNumbersMonths = new HashSet<>();
        int month = insert Number()-1 ;
        System.out.println("*** Введите год, за который надо вывести доход: ***");
        //Set<Integer> setNumbersYears = new HashSet<>();
        int year = get Number(yearsNumber);
        
        int newMoney=scanner.nextInt();
        buyers.get(numberBuyer - 1).setMoney(newMoney);
        
        buyers.get(numberBuyer - 1).setMoney(scanner.nextLine());
                    
        for (int i = 0; i < histories.size(); i++) {
        Date(date) = histories.get(i).getDateOfSale();
       //Calendar cal = Calendar.getInstance();
        c.setTime(date);
        int saleMonth = c.get(Calendar.MONTH);
        int saleYear = c.get(Calendar.YEAR);
        if ( month == saleMonth && year == saleYear) {
            sum += histories.get(i).getModel().getPrice();
        }
       
    }*/

}