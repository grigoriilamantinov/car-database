import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringJoiner;

public class DialogWithUser {

    public String getAction() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nТоварищ, что хотите сделать с данными?");
        String action = sc.nextLine();
        return action;
    }

    public TransportObjectData getDataForInsert() {
        TransportObjectData usersObject = new TransportObjectData(0,null,0,0);
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
        File file = new File("C:\\Users\\grigorii\\IdeaProjects\\car_database\\src\\main\\resources\\action_menu");
        StringJoiner joiner = new StringJoiner("\n");
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

    public TransportObjectData getDataForUpdate() {
        DataAccessObject objectById = new DataAccessObject();
        Scanner sc = new Scanner(System.in);
        int id = getIdByUser();
        TransportObjectData usersObject = objectById.formatForOutputOneString(id);
        usersObject.setId(id);
        System.out.println("Вы выбрали строчку: " + objectById.formatForOutputOneString(id));
        System.out.print("Вы хотите изменить название? ");
        String isYes = sc.nextLine();
        if (isYes.equalsIgnoreCase("ДА")) {
            System.out.print("На какое новое название изменить? ");
            usersObject.setBrand(sc.nextLine());
        }
        System.out.print("Вы хотите изменить год сборки? ");
        isYes = sc.nextLine();
        if (isYes.equalsIgnoreCase("ДА")) {
            System.out.print("На какой год сборки изменить? ");
            usersObject.setYear(sc.nextInt());
            sc.nextLine();
        }
        System.out.print("Вы хотите изменить стоимость? ");
        isYes = sc.nextLine();
        if (isYes.equalsIgnoreCase("ДА")) {
            System.out.print("Сколько будет стоить теперь? ");
            usersObject.setCost(sc.nextInt());
        }
        return usersObject;
    }

    public int getIdByUser() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Какой id вас интересует? ");
        return sc.nextInt();
    }
}
