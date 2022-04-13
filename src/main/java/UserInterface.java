import db_layer.dto.YearDTO;
import db_layer.propertiesLoader.PropertiesLoader;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.StringJoiner;

public class UserInterface {
    private final PropertiesLoader loader;

    public static final String LINE_BREAK = "\n";


    public UserInterface(PropertiesLoader loader) {
        this.loader = loader;
    }

    public String getAction() {
        System.out.println("\nТоварищ, что хотите сделать с данными?");
        return this.readLine();
    }

    public String formatActionMenu() {
        File file = new File(loader.getMenu());
        StringJoiner joiner = new StringJoiner(LINE_BREAK);
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                joiner.add(sc.nextLine());
            }
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

    private String readLine() {
        final InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        final BufferedReader reader = new BufferedReader(inputStreamReader);
        final String line;
        try {
            line = reader.readLine();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        if (line != null) {
            return line;
        } else {
            throw new InputMismatchException("Проблемы с вводом");
        }
    }
}
