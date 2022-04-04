
import db_layer.connection.ConnectionFactory;
import db_layer.dao.CarsDAO;
import db_layer.dao.DAO;
import formatter.Formatter;
import formatter.CarFormatter;
import service_layer.CarService;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        System.out.println("Ссылка на свойства:");
        Scanner sc = new Scanner(System.in);
        String dataSource = sc.nextLine();
        ConnectionFactory factory = new ConnectionFactory(dataSource);
        CarsDAO dao = new CarsDAO(factory, dataSource);
        Formatter formatter = new CarFormatter();
        UserInterface dialog = new UserInterface(dataSource);
        CarService carService = new CarService(dao);

        dao.createTable();
        boolean isExit = false;
        System.out.println(dialog.formatActionMenu());

        while (!isExit) {
            switch (dialog.getAction().toUpperCase()) {
                case "ВЫВОД":
                    System.out.println(formatter.formatFromList(dao.findAll()));
                    break;
                case "ДОБАВИТЬ":
                    dao.save(dialog.getDataForInsert());
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
                case "ГОДЫ":
                    System.out.println(formatter.formatFromList(carService.getCarsBetweenYears(1910,2000)));
                    break;
                case "ЦЕНЫ":
                    System.out.println(formatter.formatFromList(carService.getCarsLessCost(1000000)));
                    break;
                default:
                    System.out.println("Товарищ, такого мы сделать не можем");
                    break;
            }
        }
        dao.dropTable();
    }
}
