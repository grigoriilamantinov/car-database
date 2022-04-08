
import db_layer.connection.ConnectionFactory;
import db_layer.dao.CarsDAO;
import db_layer.dao.OwnersDAO;
import db_layer.tableCreator.CarTableCreator;
import db_layer.tableCreator.OwnerTableCreator;
import db_layer.tableCreator.TableCreator;
import formatter.CarFormatter;
import formatter.OwnersFormatter;
import service_layer.CarService;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        System.out.println("Ссылка на свойства:");
        Scanner sc = new Scanner(System.in);
        String dataSource = sc.nextLine();
        ConnectionFactory factory = new ConnectionFactory(dataSource);
        CarsDAO carsDAO = new CarsDAO(factory);
        OwnersDAO ownersDAO = new OwnersDAO(factory);
        CarFormatter carFormatter = new CarFormatter();
        OwnersFormatter ownersFormatter = new OwnersFormatter();
        UserInterface dialog = new UserInterface(dataSource);
        CarService carService = new CarService(carsDAO);
        TableCreator carTable = new CarTableCreator(factory, dataSource);
        TableCreator  ownerTable = new OwnerTableCreator(factory, dataSource);

        carTable.createTable();
        ownerTable.createTable();
        boolean isExit = false;
        System.out.println(dialog.formatActionMenu());

        while (!isExit) {
            switch (dialog.getAction().toUpperCase()) {
                case "ВЫВОД":
                    System.out.println(carFormatter.carFromList(carsDAO.findAll()));
                    System.out.println();
                    System.out.println(ownersFormatter.ownersFromList(ownersDAO.findAll()));
                    break;
                case "ДОБАВИТЬ":
                    carsDAO.save(dialog.getDataForInsert());
                    break;
                case "УДАЛИТЬ":
                    carsDAO.deleteById(dialog.getIdFromUser());
                    break;
                case "ИЗМЕНИТЬ":
                    carsDAO.update(dialog.getDataForUpdate(carsDAO));
                    break;
                case "МАШИНА":
                    System.out.println(carsDAO.getById(dialog.getIdFromUser()).toString());
                    break;
                case "ВЛАДЕЛЕЦ":
                    System.out.println(ownersDAO.getById(dialog.getIdFromUser()).toString());
                    break;
                case "ГОДЫ":
                    System.out.println(carFormatter.carFromList(carService.getCarsBetweenYears(1910,2000)));
                    break;
                case "ЦЕНЫ":
                    System.out.println(carFormatter.carFromList(carService.getCarsCostLessThan(1000000)));
                    break;
                case "ВЛАДЕЛЕЦ МАШИНЫ":
                    System.out.println(carFormatter.ownersCarFromList(
                        ownersDAO.getCarOwners(dialog.getIdFromUser())
                    ));
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
        carTable.dropTable();
        ownerTable.dropTable();
    }
}
