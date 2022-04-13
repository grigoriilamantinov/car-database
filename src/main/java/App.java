
import db_layer.connection.ConnectionFactory;
import db_layer.tableCreator.TableCreator;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        System.out.println("Ссылка на свойства:");
        Scanner sc = new Scanner(System.in);
        String dataSource = sc.nextLine();
        ConnectionFactory factory = new ConnectionFactory(dataSource);
        DAOFacade daoFacade = new DAOFacade(factory, dataSource);
        FormatterFacade formatterFacade = new FormatterFacade();

        int carId;
        int shopId;

        TableCreator tableCreator = new TableCreator(factory, dataSource);
        tableCreator.prepareAllTables();

        boolean isExit = false;
        UserInterface dialog = new UserInterface(dataSource);
        System.out.println(dialog.formatActionMenu());

        while (!isExit) {
            switch (dialog.getAction().toUpperCase()) {
                case "ВЫВОД":
                    outputAllTables(factory, dataSource);
                    break;
                case "МАШИНА":
                    System.out.println(daoFacade.getCarsDAO().getById(dialog.getIdFromUser()).toString());
                    break;
                case "ВЛАДЕЛЕЦ":
                    System.out.println(daoFacade.getOwnersDAO().getById(dialog.getIdFromUser()).toString());
                    break;
                case "ГОДЫ":
                    System.out.println(formatterFacade
                        .getCarFormatter()
                        .getFromList(daoFacade.getCarService().getCarsBetweenYears(dialog.getYearsFromUser())));
                    break;
                case "НИЖЕ ЦЕНЫ":
                    System.out.println(formatterFacade
                        .getCarFormatter()
                        .getFromList(daoFacade.getCarService().getCarsCostLessThan(dialog.getCostFromUser())));
                    break;
                case "ГДЕ КУПИТЬ":
                    carId = dialog.getIdFromUser();
                    System.out.println(formatterFacade
                        .getCarShopFormatter()
                        .carShopOnlyFromList(daoFacade.getCarsDAO().carInParticularShop(carId)));
                    break;
                case "ЧТО В МАГАЗИНЕ":
                    shopId = dialog.getIdFromUser();
                    System.out.println(formatterFacade
                        .getShopFormatter()
                        .allCarIntoShopFromList(daoFacade.getShopsDAO().allCarInOneShop(shopId)));
                    break;
                case "ВЛАДЕЛЕЦ МАШИНЫ":
                    System.out.println(formatterFacade.getCarFormatter().ownersCarFromList(
                        daoFacade.getOwnersDAO().getCarOwners(dialog.getIdFromUser())
                    ));
                    break;
                case "УДАЛИТЬ ИЗ МАГАЗИНА":
                    carId = dialog.getCarIdFromUser();
                    shopId = dialog.getShopIdFromUser();
                    daoFacade.getCarsDAO().deleteCarFromShop(carId,shopId);
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

    private static void outputAllTables(ConnectionFactory factory, String dataSource) {
        DAOFacade daoFacade = new DAOFacade(factory, dataSource);
        FormatterFacade formatterFacade = new FormatterFacade();
        System.out.println(formatterFacade
            .getCarFormatter()
            .getFromList(daoFacade.getCarsDAO().findAll()));
        System.out.println();
        System.out.println(formatterFacade
            .getOwnersFormatter()
            .getFromList(daoFacade.getOwnersDAO().findAll()));
        System.out.println();
        System.out.println(formatterFacade
            .getShopFormatter()
            .shopsFromList(daoFacade.getShopsDAO().findAll()));
        System.out.println();
        System.out.println(formatterFacade
            .getCarShopFormatter()
            .getFromList(daoFacade.getCarIntoShopsDAO().findAll()));
    }
}
