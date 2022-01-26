
package myclasses;

import entity.Buyer;
import entity.History;
import entity.Model;
import facade.BuyerFacade;
import facade.HistoryFacade;
import facade.ModelFacade;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.Date;
import tools.Singleton;

public class App {
    private Scanner scanner = new Scanner(System.in);
    private BuyerFacade buyerFacade;
    private ModelFacade modelFacade;
    private HistoryFacade historyFacade;
    private Singleton singleton;
    
    public App() {
        singleton = Singleton.getInstance();
        init();   
    }
    private void init() {
        buyerFacade = new BuyerFacade(Buyer.class);
        modelFacade = new ModelFacade(Model.class);
        historyFacade = new HistoryFacade(History.class);
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
                    Sales();
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
                    editModel();
                    break;
                case 11:
                    editBuyer();
                    break;
                case 12:
                    printListAllSold();
                    break;
                case 13:
                    incomeMonth();
                    break;
                    
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
        modelFacade.create(model);
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
        buyerFacade.create(buyer);
    }
    private void addHistory(){
        if (isQuit()) {
            return;
        }
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
        
        Model model = modelFacade.find((long)numberModel);
        history.setModel(model);
        
        
        Buyer buyer = buyerFacade.find((long)numberBuyer);
        history.setBuyer(buyer);
        
        Calendar c = new GregorianCalendar();
        history.setDateOfSale(c.getTime());
    
        
        model.setCount(model.getCount() - 1);
        buyer.setMoney(buyer.getMoney() - model.getPrice());
        
        modelFacade.edit(model);
        historyFacade.create(history);
        
            
        System.out.println("-------------------");
        System.out.println("*** ПРОДАНО:   " + model.getManufacturer()
            + " цена: " + model.getPrice() + "eur"
            + " / цвет: " + model.getColor()
            + " / размер: " + model.getSize()
            + " / покупатель: "+ buyer.getName()
            + " / тел: " + buyer.getPhone()
        );
    }
        
    private Set<Integer> printListModels() {
        System.out.println("---------------");
        System.out.println("*** Список обуви в продаже ***");
        
        Set<Integer> setNumbersModels = new HashSet<>();
        List<Model> models = modelFacade.findAll();
            for (int i = 0; i < models.size(); i++) {
                if(models.get(i) != null && models.get(i).getCount() > 0){
                    System.out.printf("%3d   %-15s %-10s size: %-5d %5d eur    Всего: %3d tk    В наличии: %3d tk %n"
                    ,models.get(i).getId()
                    ,models.get(i).getManufacturer()
                    ,models.get(i).getColor()
                    ,models.get(i).getSize()
                    ,models.get(i).getPrice()
                    ,models.get(i).getQuantity()
                    ,models.get(i).getCount()
                );
                     setNumbersModels.add(models.get(i).getId().intValue());
                }
            } 
       return setNumbersModels;
    } 
    private Set<Integer> printListAllModels() {
        System.out.println("---------------");
        System.out.println("*** Список обуви ***");
            
        Set<Integer> setNumbersModels = new HashSet<>();
        List<Model> models = modelFacade.findAll();
        for (int i = 0; i < models.size(); i++) {
            if(models.get(i) != null && models.get(i).getCount() >= 0){
                System.out.printf("%3d   %-12s %-10s size: %-4d %5d eur     Всего: %3d tk     В наличии: %3d tk %n"
                    ,models.get(i).getId()
                    ,models.get(i).getManufacturer()
                    ,models.get(i).getColor()
                    ,models.get(i).getSize()
                    ,models.get(i).getPrice()
                    ,models.get(i).getQuantity()
                    ,models.get(i).getCount()
                );
                setNumbersModels.add(models.get(i).getId().intValue());
            }
        }
    System.out.println("-------------------");
    return setNumbersModels;
    }
        
    private Set<Integer> printListBuyers() {
        
        System.out.println("---------------");
        System.out.println("*** Список покупателей ***");
        
        Set<Integer> setNumbersBuyers = new HashSet<>();
        List<Buyer> buyers = buyerFacade.findAll();
            for (int i = 0; i < buyers.size(); i++) {
                if(buyers.get(i) != null){
                     System.out.printf("%3d   %-22s  тел: %-12s %5d eur%n"
                    ,buyers.get(i).getId()
                    ,buyers.get(i).getName()
                    ,buyers.get(i).getPhone()
                    ,buyers.get(i).getMoney()
                    );
                    setNumbersBuyers.add(buyers.get(i).getId().intValue());
                }
            }
            return setNumbersBuyers;
    } 
        
    private Set<Integer> printListBuyersForSale() {
         System.out.println("---------------");
        System.out.println("*** Список покупателей ***");
        
        Set<Integer> setNumbersBuyers = new HashSet<>();
        List<Buyer> buyers = buyerFacade.findAll();
        List<Model> models = modelFacade.findAll();
            for (int i = 0; i < buyers.size(); i++) {
                if(buyers.get(i) != null && buyers.get(i).getMoney() > (models.get(i).getPrice()-1)){
                     System.out.printf("%3d   %-22s  тел: %-12s %5d eur%n"
                    ,buyers.get(i).getId()
                    ,buyers.get(i).getName()
                    ,buyers.get(i).getPhone()
                    ,buyers.get(i).getMoney()
                    );
                    setNumbersBuyers.add(buyers.get(i).getId().intValue());
                }
            }
            return setNumbersBuyers;
    }
        
        
        
    private Set<Integer> Sales(){
         System.out.println("-------------------");
            System.out.println("*** Список продаж ***");
            Set<Integer> setNumbersModels = new HashSet<>();
            
            List<History> histories = historyFacade.findAll();
            
            for (int i = 0; i < histories.size(); i++) {
                if(histories.get(i).getModel() != null && histories.get(i).getModel().getCount() > 0) {                          
                    System.out.println( histories.get(i).getId() + ". " 
                        + "производитель: " + histories.get(i).getModel().getManufacturer()
                        + " / цвет: " +  histories.get(i).getModel().getColor()
                        + " / цена: " + histories.get(i).getModel().getPrice() + "eur "
                        + " / размер: " + histories.get(i).getModel().getSize()
                        + " / покупатель: " + histories.get(i).getBuyer().getName()
                        + " / тел: " + histories.get(i).getBuyer().getPhone()
                    );
                    setNumbersModels.add(histories.get(i).getId().intValue());
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
        
        List<History> histories = historyFacade.findAll();
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
            
            List<History> histories = historyFacade.findAll();
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
        List<Buyer> buyers = buyerFacade.findAll();
        
        History history = new History(); 
        System.out.println("*** Список покупателей ***");
        int n = 0;
            for (int i = 0; i < buyers.size(); i++) {
                if(buyers.get(i) != null){
                    System.out.println(buyers.get(i).getId() + ".   " + buyers.get(i).toString());
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
       
        Buyer buyer = buyerFacade.find((long)numBuyer);
       
        history.setBuyer(buyers.get(numBuyer-1));
        buyer.setMoney(buyer.getMoney() + addMoney);
       
        buyerFacade.edit(buyer);
        historyFacade.edit(history);
       
        System.out.println("--------------------");
        System.out.println( " *** " + buyers.get(numBuyer-1).getName()
                    + " / Добавлены денеги в сумме: " + addMoney + " eur"
                    + " / Всего у покупателя: " +  buyers.get(numBuyer-1).getMoney() + " eur"); 
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

    private void editModel() {
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
        List<Model> models = modelFacade.findAll(); 
        
        changeNumbers.add(1);
        changeNumbers.add(2);
        Set<Integer> setNumbersModels = printListAllModels();
        if(setNumbersModels.isEmpty()){
            System.out.println("*** Обуви в продаже нет! ***");
            return;
        }
        System.out.println("*** Введите номер обуви:");
        int numberModel = insertNumber(setNumbersModels);
        
        Model model = modelFacade.find((long)numberModel);
        
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
        
        //keeping.saveModels(models); 
        modelFacade.edit(model);
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
        
        List<Buyer> buyers = buyerFacade.findAll();
        
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
        
        Buyer buyer = buyerFacade.find((long)numberBuyer);
        
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
                    buyerFacade.edit(buyer);
                    break;
                case 2:
                    System.out.print("Введите новый номер покупателя: ");
                    String newPhone=scanner.nextLine();
                    buyers.get(numberBuyer - 1).setPhone(newPhone);
                    buyerFacade.edit(buyer);
                    break;
                case 3:
                    System.out.print("Введите другое количество денег: ");
                    int newMoney=scanner.nextInt();
                    buyers.get(numberBuyer - 1).setMoney(newMoney);
                    buyerFacade.edit(buyer);
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
            
        List<Model> models = modelFacade.findAll();
            
        for (int i = 0; i < models.size(); i++) {
            if(models.get(i) != null && models.get(i).getCount() == 0){
                System.out.printf("%3d   %-12s %-10s size: %-4d %5d eur     Всего: %3d tk     В наличии: %3d tk %n"
                ,models.get(i).getId()
                ,models.get(i).getManufacturer()
                ,models.get(i).getColor()
                ,models.get(i).getSize()
                ,models.get(i).getPrice()
                ,models.get(i).getQuantity()
                ,models.get(i).getCount()
                );
                setNumbersModels.add(models.get(i).getId().intValue());
            }
        } 
        return setNumbersModels;
    } 
       
       
       

  
    
    private void incomeMonth() {
       int sum = 0 ;
        Calendar c = new GregorianCalendar();
        System.out.println("*** Доход за месяц ***");
        System.out.println("*** Введите месяц (1-12), за который надо вывести доход: ***");
        
        Set<Integer> setNumbersMonths = new HashSet<>();
        List<History> histories = historyFacade.findAll();
        
        for(int i = 1;i<13;i++){
            setNumbersMonths.add(i);
        }
        int month = insertNumber(setNumbersMonths)-1;
        System.out.println("*** Введите год, за который надо вывести доход: ***");
        
        int year = getNumber();
        
        
        for (int i = 0; i < histories.size(); i++) {
            Date date = histories.get(i).getDateOfSale();
           //Calendar cal = Calendar.getInstance();
            c.setTime(date);
            int saleMonth = c.get(Calendar.MONTH);
            int saleYear = c.get(Calendar.YEAR);
            if ( month == saleMonth && year == saleYear) {
                sum += histories.get(i).getModel().getPrice();
            }

        }
        System.out.println("Доход за "+ (month+1) +": "+sum);
    }
    

}
