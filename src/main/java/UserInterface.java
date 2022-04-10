import db_layer.dao.DAO;
import db_layer.dto.CarDTO;
import db_layer.propertiesLoader.PropertiesLoader;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.StringJoiner;

public class UserInterface {
    private final String dataSource;
    public static String LINE_BREAK = "\n";

    public UserInterface(String filePath) {
        this.dataSource = filePath;
    }

    public String getAction() {
        System.out.println("\nТоварищ, что хотите сделать с данными?");
        String action = this.readLine();
        return action;
    }

    private String readLine() {
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String string = null;
        try {
            string = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (string != null) {
            return string;
        } else {
            throw new InputMismatchException("Проблемы с вводом");
        }
    }

    public CarDTO getDataForInsert() {

        CarDTO usersObject = new CarDTO();
        Scanner sc = new Scanner(System.in);
        System.out.print("Напишите название машины: ");
        usersObject.setBrand(sc.nextLine());
        System.out.print("Укажите год выпуска: ");
        usersObject.setYear(sc.nextInt());
        sc.nextLine();
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

    public CarDTO getDataForUpdate(DAO<CarDTO> dao) {
        Scanner sc = new Scanner(System.in);
        int id = getIdFromUser();
        CarDTO car = dao.getById(id);
        car.setId(id);
        System.out.println("Вы выбрали строчку: " + dao.getById(id));
        System.out.print("Вы хотите изменить название? ");
        boolean isYes = sc.nextLine().equalsIgnoreCase("ДА");
        if (isYes) {
            System.out.print("На какое новое название изменить? ");
            car.setBrand(sc.nextLine());
        }
        System.out.print("Вы хотите изменить год сборки? ");
        isYes = sc.nextLine().equalsIgnoreCase("ДА");
        if (isYes) {
            System.out.print("На какой год сборки изменить? ");
            car.setYear(sc.nextInt());
            sc.nextLine();
        }
        System.out.print("Вы хотите изменить стоимость? ");
        isYes = sc.nextLine().equalsIgnoreCase("ДА");
        if (isYes) {
            System.out.print("Сколько будет стоить теперь? ");
            car.setCost(sc.nextInt());
        }
        return car;
    }

    public int getIdFromUser() {
        System.out.print("Какой id вас интересует? ");
        int id = Integer.parseInt(this.readLine());
        return id;
    }
}
