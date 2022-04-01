import dao.DAO;
import dto.CarDTO;
import propertiesLoader.PropertiesLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringJoiner;

public class UserInterface {
    private String dataSource;
    public static String LINE_BREAK = "\n";

    public UserInterface(String filePath) {
        this.dataSource = filePath;
    }

    public String getAction() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nТоварищ, что хотите сделать с данными?");
        String action = sc.nextLine();
        return action;
    }

    public CarDTO getDataForInsert() {

        CarDTO usersObject = new CarDTO(0,null,0,0);
        Scanner sc = new Scanner(System.in);
        System.out.print("Напишите название машины: ");
        usersObject.setBrand(sc.nextLine());
        System.out.print("Укажите год выпуска: ");
        usersObject.setYear(sc.nextInt());
        System.out.print("Укажите стоимость: ");
        usersObject.setCost(sc.nextInt());
        return usersObject;
    }

    public String formatActionMenu() {

        PropertiesLoader loader = new PropertiesLoader(dataSource);
        File file = new File(loader.getMenu());
        StringJoiner joiner = new StringJoiner(LINE_BREAK);
        Scanner sc = null;
        try {
            sc = new Scanner(file);
            while (sc.hasNextLine()) {
                joiner.add(sc.nextLine());
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return joiner.toString();
    }

    public CarDTO getDataForUpdate(DAO dao) {

        Scanner sc = new Scanner(System.in);
        int id = getIdFromUser();
        CarDTO usersObject = dao.getById(id);
        usersObject.setId(id);
        System.out.println("Вы выбрали строчку: " + dao.getById(id));
        System.out.print("Вы хотите изменить название? ");
        boolean isYes = sc.nextLine().equalsIgnoreCase("ДА");
        if (isYes) {
            System.out.print("На какое новое название изменить? ");
            usersObject.setBrand(sc.nextLine());
        }
        System.out.print("Вы хотите изменить год сборки? ");
        isYes = sc.nextLine().equalsIgnoreCase("ДА");
        if (isYes) {
            System.out.print("На какой год сборки изменить? ");
            usersObject.setYear(sc.nextInt());
            sc.nextLine();
        }
        System.out.print("Вы хотите изменить стоимость? ");
        isYes = sc.nextLine().equalsIgnoreCase("ДА");
        if (isYes) {
            System.out.print("Сколько будет стоить теперь? ");
            usersObject.setCost(sc.nextInt());
        }
        return usersObject;
    }

    public int getIdFromUser() {

        Scanner sc = new Scanner(System.in);
        System.out.print("Какой id вас интересует? ");
        return sc.nextInt();
    }
}
