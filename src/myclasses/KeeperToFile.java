
package myclasses;

import entity.Buyer;
import entity.History;
import entity.Model;
import interfases.Keeping;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KeeperToFile implements Keeping{

    @Override
    public void saveModels(List<Model> models) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream("models");
            oos = new ObjectOutputStream(fos);
            oos.writeObject(models);
            oos.flush();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(KeeperToFile.class.getName()).log(Level.SEVERE, "Нет такого файла", ex);
        } catch (IOException ex) {
            Logger.getLogger(KeeperToFile.class.getName()).log(Level.SEVERE, "Ошибка ввода", ex);
        }
    }

    @Override
    public List<Model> loadModels() {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        List<Model> models = new ArrayList<>();
        try {
            fis = new FileInputStream("models");
            ois = new ObjectInputStream(fis);
            models = (List<Model>) ois.readObject();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(KeeperToFile.class.getName()).log(Level.SEVERE, "Файл models еще не создан", ex);
        } catch (IOException ex) {
            Logger.getLogger(KeeperToFile.class.getName()).log(Level.SEVERE, "Ошибка чтения models", ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(KeeperToFile.class.getName()).log(Level.SEVERE, "Нет класса", ex);
        }
        return models;
    }

    @Override
    public void saveBuyers(List<Buyer> buyers) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream("buyers");
            oos = new ObjectOutputStream(fos);
            oos.writeObject(buyers);
            oos.flush();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(KeeperToFile.class.getName()).log(Level.SEVERE, "Нет такого файла", ex);
        } catch (IOException ex) {
            Logger.getLogger(KeeperToFile.class.getName()).log(Level.SEVERE, "Ошибка ввода", ex);
        }
    }

    @Override
    public List<Buyer> loadBuyers() {
 FileInputStream fis = null;
        ObjectInputStream ois = null;
        List<Buyer> buyers = new ArrayList<>();
        try {
            fis = new FileInputStream("buyers");
            ois = new ObjectInputStream(fis);
            buyers = (List<Buyer>) ois.readObject();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(KeeperToFile.class.getName()).log(Level.SEVERE, "Файл buyers еще не создан", ex);
        } catch (IOException ex) {
            Logger.getLogger(KeeperToFile.class.getName()).log(Level.SEVERE, "Ошибка чтения buyers", ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(KeeperToFile.class.getName()).log(Level.SEVERE, "Нет класса", ex);
        }
        return buyers;
    }

    @Override
    public void saveHistories(List<History> histories) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream("histories");
            oos = new ObjectOutputStream(fos);
            oos.writeObject(histories);
            oos.flush();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(KeeperToFile.class.getName()).log(Level.SEVERE, "Нет такого файла", ex);
        } catch (IOException ex) {
            Logger.getLogger(KeeperToFile.class.getName()).log(Level.SEVERE, "Ошибка ввода", ex);
        }
    }

    @Override
    public List<History> loadHistories() {
         FileInputStream fis = null;
        ObjectInputStream ois = null;
        List<History> histories = new ArrayList<>();
        try {
            fis = new FileInputStream("histories");
            ois = new ObjectInputStream(fis);
            histories = (List<History>) ois.readObject();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(KeeperToFile.class.getName()).log(Level.SEVERE, "Файл histories еще не создан", ex);
        } catch (IOException ex) {
            Logger.getLogger(KeeperToFile.class.getName()).log(Level.SEVERE, "Ошибка чтения histories", ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(KeeperToFile.class.getName()).log(Level.SEVERE, "Нет класса", ex);
        }
        return histories;
    }
    
}