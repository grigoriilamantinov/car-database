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
