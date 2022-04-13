import db_layer.dto.YearDTO;
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

    public int getIdFromUser() {
        System.out.print("Какой id вас интересует? ");
        int id = Integer.parseInt(this.readLine());
        return id;
    }

    public int getCarIdFromUser() {
        System.out.println("Какую машину вы хотите удалить?");
        return this.getIdFromUser();
    }

    public int getShopIdFromUser() {
        System.out.println("И из какого магазина вы хотите удалить?");
        return this.getIdFromUser();
    }

    public int getCostFromUser() {
        System.out.println("Укажите стоимость ниже которой вы хотели бы найти авто:");
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }

    public YearDTO getYearsFromUser() {
        YearDTO yearDTO = new YearDTO();
        Scanner sc = new Scanner(System.in);
        System.out.println("Укажите год с которого вы хотели бы найти авто:");
        int yearFirst = sc.nextInt();
        sc.nextLine();
        System.out.println("Укажите год до которого вы хотели бы найти авто:");
        int yearSecond = sc.nextInt();
        if (yearFirst < yearSecond) {
            yearDTO.setYearFrom(yearFirst);
            yearDTO.setYearTo(yearSecond);
        } else {
            yearDTO.setYearFrom(yearSecond);
            yearDTO.setYearTo(yearFirst);
        }
        return yearDTO;
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


}
