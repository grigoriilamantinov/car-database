
import db_layer.connection.ConnectionFactory;
import db_layer.dao.CarIntoShopsDAO;
import db_layer.dao.CarsDAO;
import db_layer.dao.OwnersDAO;
import db_layer.dao.ShopsDAO;
import db_layer.dto.CarIntoShopsDTO;
import db_layer.tableCreator.TableCreator;
import formatter.CarFormatter;
import formatter.CarIntoShopFormatter;
import formatter.OwnersFormatter;
import formatter.ShopFormatter;
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
        ShopsDAO shopsDAO = new ShopsDAO(factory);
        CarFormatter carFormatter = new CarFormatter();
        ShopFormatter shopFormatter = new ShopFormatter();
        CarIntoShopsDTO carIntoShopsDTO = new CarIntoShopsDTO();
        CarIntoShopFormatter carIntoShopFormatter = new CarIntoShopFormatter();
        CarIntoShopsDAO carIntoShopsDAO = new CarIntoShopsDAO(factory);
        OwnersFormatter ownersFormatter = new OwnersFormatter();
        UserInterface dialog = new UserInterface(dataSource);
        CarService carService = new CarService(carsDAO);
        TableCreator tableCreator = new TableCreator(factory, dataSource);

        tableCreator.createAllTables();
        boolean isExit = false;
        System.out.println(dialog.formatActionMenu());

        while (!isExit) {
            switch (dialog.getAction().toUpperCase()) {
                case "ВЫВОД":
                    System.out.println(carFormatter.carFromList(carsDAO.findAll()));
                    System.out.println();
                    System.out.println(ownersFormatter.ownersFromList(ownersDAO.findAll()));
                    System.out.println();
                    System.out.println(shopFormatter.shopsFromList(shopsDAO.findAll()));
                    System.out.println();
                    System.out.println(carIntoShopFormatter.carIntoShopFromList(carIntoShopsDAO.findAll()));
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
                case "ГДЕ КУПИТЬ":
                    System.out.println(carIntoShopFormatter.carShopOnlyFromList(carsDAO.carInParticularShop(dialog.getIdFromUser())));
                    break;
                case "ЧТО В МАГАЗИНЕ":
                    System.out.println(shopFormatter.allCarIntoShopFromList(shopsDAO.allCarInParticularShop(dialog.getIdFromUser())));
                    break;
                case "ВЛАДЕЛЕЦ МАШИНЫ":
                    System.out.println(carFormatter.ownersCarFromList(
                        ownersDAO.getCarOwners(dialog.getIdFromUser())
                    ));
                case "УДАЛИТЬ ИЗ МАГАЗИНА":
                    System.out.println("Какую машину вы хотите удалить?");
                    int carId = dialog.getIdFromUser();
                    System.out.println("И из какого магазина вы хотите удалить?");
                    int shopId = dialog.getIdFromUser();
                    carsDAO.deleteCarFromShop(carId,shopId);
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
        tableCreator.dropAllTables();
    }
}
