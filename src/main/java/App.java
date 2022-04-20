
import db_layer.connection.ConnectionFactory;
import db_layer.dao.TableCreator;
import db_layer.dto.YearDTO;
import db_layer.propertiesLoader.PropertiesLoader;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        System.out.println("Ссылка на свойства:");
        Scanner sc = new Scanner(System.in);
        final String dataSource = sc.nextLine();
        final PropertiesLoader loader = new PropertiesLoader(dataSource);

        final ConnectionFactory factory = new ConnectionFactory(loader);
        final DAOFacade daoFacade = new DAOFacade(factory, loader);
        final FormatterFacade formatterFacade = new FormatterFacade();
        final TableCreator tableCreator = new TableCreator(factory, loader);
        tableCreator.prepareAllTables();

        boolean isExit = false;
        final UserInterface ui = new UserInterface(loader);
        System.out.println(ui.formatActionMenu());

        while (!isExit) {
            switch (ui.getAction().toUpperCase()) {
                case "ВЫВОД":
                    outputAllTables(daoFacade, formatterFacade);
                    break;
                case "МАШИНА":
                    System.out.println(daoFacade.getCarsDAO().getById(ui.getIdFromUser()).toString());
                    break;
                case "ВЛАДЕЛЕЦ":
                    System.out.println(daoFacade.getOwnersDAO().getById(ui.getIdFromUser()).toString());
                    break;
                case "МАГАЗИН":
                    System.out.println(daoFacade.getShopsDAO().getById(ui.getIdFromUser()).toString());
                    break;
                case "ГОДЫ":
                    final YearDTO yearDTO = ui.getYearsFromUser();
                    System.out.println(formatterFacade
                        .getCarFormatter()
                        .getFromList(daoFacade.getCarService()
                        .getCarsBetweenYears(yearDTO.getYearFrom(), yearDTO.getYearTo())));
                    break;
                case "НИЖЕ ЦЕНЫ":
                    System.out.println(formatterFacade
                        .getCarFormatter()
                        .getFromList(daoFacade.getCarService().getCarsCostLessThan(ui.getCostFromUser())));
                    break;
                case "ГДЕ КУПИТЬ":
                    final int carId = ui.getIdFromUser();
                    System.out.println(formatterFacade
                        .getCarShopFormatter()
                        .carShopOnlyFromList(daoFacade.getCarsDAO().carInParticularShop(carId)));
                    break;
                case "ЧТО В МАГАЗИНЕ":
                    final int shopId = ui.getIdFromUser();
                    System.out.println(formatterFacade
                        .getShopFormatter()
                        .allCarIntoShopFromList(daoFacade.getShopsDAO().allCarInOneShop(shopId)));
                    break;
                case "УДАЛИТЬ ИЗ МАГАЗИНА":
                    final int idCar = ui.getCarIdFromUser();
                    final int idShop = ui.getShopIdFromUser();
                    daoFacade.getCarsDAO().deleteCarFromShop(idCar,idShop);
                    break;
                case "МЕНЮ":
                    System.out.println(ui.formatActionMenu());
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

    private static void outputAllTables(DAOFacade daoFacade, FormatterFacade formatterFacade) {
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
            .getFromList(daoFacade.getShopsDAO().findAll()));

        System.out.println();

        System.out.println(formatterFacade
            .getCarShopFormatter()
            .getFromList(daoFacade.getCarIntoShopsDAO().findAll()));
    }
}
