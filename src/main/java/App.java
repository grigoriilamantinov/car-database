
import connection.ConnectionFactory;
import dao.CarsDAO;
import dao.DAO;
import formatter.Formatter;
import formatter.FormatterDTO;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        System.out.println("Ссылка на свойства:");
        Scanner sc = new Scanner(System.in);
        String dataSource = sc.nextLine();
        ConnectionFactory factory = new ConnectionFactory(dataSource);
        DAO dao = new CarsDAO(factory, dataSource);
        Formatter formatter = new FormatterDTO();
        UserInterface dialog = new UserInterface(dataSource);

        dao.createTable();
        boolean isExit = false;
        System.out.println(dialog.formatActionMenu());

        while (!isExit) {
            switch (dialog.getAction().toUpperCase()) {
                case "ВЫВОД":
                    System.out.println(formatter.formatFromList(dao.findAll()));
                    break;
                case "ДОБАВИТЬ":
                    dao.addString(dialog.getDataForInsert());
                    break;
                case "УДАЛИТЬ":
                    dao.deleteById(dialog.getIdFromUser());
                    break;
                case "ИЗМЕНИТЬ":
                    dao.update(dialog.getDataForUpdate(dao));
                    break;
                case "СТРОЧКУ":
                    System.out.println(dao.getById(dialog.getIdFromUser()).toString());
                    break;
                case "МЕНЮ":
                    System.out.println(dialog.formatActionMenu());
                    break;
                case "ВЫХОД":
                    isExit = true;
                    break;
                default:
                    System.out.println("Товарищ, такого мы сделать не можем");
                    break;
            }
        }
        dao.dropTable();
    }
}
