import db_layer.dto.YearDTO;
import db_layer.logger.LoggerManager;
import db_layer.propertiesLoader.PropertiesLoader;

import java.io.*;
import java.util.Scanner;
import java.util.StringJoiner;

public class UserInterface {
    private final PropertiesLoader loader;

    public static final String LINE_BREAK = "\n";
    private static final String LOG_MESSAGE = "Error in path to file with menu statement: %s";

    public UserInterface(PropertiesLoader loader) {
        this.loader = loader;
    }

    public String getAction() {
        System.out.println("\nТоварищ, что хотите сделать с данными?");
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    public String formatActionMenu() {
        String pathToMenu = loader.getMenu();
        File file = new File(pathToMenu);
        StringJoiner joiner = new StringJoiner(LINE_BREAK);
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                joiner.add(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            LoggerManager.getLogger().info(String.format(LOG_MESSAGE, pathToMenu));
        }
        return joiner.toString();
    }

    public int getIdFromUser() {
        System.out.print("Какой id вас интересует? ");
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
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
        final YearDTO yearDTO = new YearDTO();
        final Scanner sc = new Scanner(System.in);
        System.out.println("Укажите год с которого вы хотели бы найти авто:");
        final int yearFirst = sc.nextInt();
        sc.nextLine();
        System.out.println("Укажите год до которого вы хотели бы найти авто:");
        final int yearSecond = sc.nextInt();
        if (yearFirst < yearSecond) {
            yearDTO.setYearFrom(yearFirst);
            yearDTO.setYearTo(yearSecond);
        } else {
            yearDTO.setYearFrom(yearSecond);
            yearDTO.setYearTo(yearFirst);
        }
        return yearDTO;
    }
}
