import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        TableCars tableMaker = new TableCars();
        Dialog dialog = new Dialog();
        tableMaker.createTableCars();
        Scanner sc = new Scanner(System.in);
        boolean isExit = false;

        System.out.println(dialog.actionMenu());
        while (!isExit) {
            System.out.println("\nТоварищ, что хотите сделать с данными?");
            String action = sc.nextLine();
            action = action.toUpperCase();

            switch (action) {
                case "ВЫВОД":
                    System.out.println(tableMaker.outputData());
                    break;
                case "ДОБАВИТЬ":
                    tableMaker.insertString(dialog.getData());
                    break;
                case "УДАЛИТЬ":
                    tableMaker.deleteString(dialog.idForDelete());
                    break;

                case "МЕНЮ":
                    System.out.println(dialog.actionMenu());
                    break;
                case "ВЫХОД":
                    isExit = true;
                    break;
                default:
                    System.out.println("Товарищ, такого мы сделать не можем");
                    break;
            }
        }

        tableMaker.dropTable();
    }
}
