import connection.ConnectionFactory;
import dao.CarsDAO;
import dao.DAO;

public class App {
    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory("C:\\Users\\grigorii\\IdeaProjects\\LearningSQL\\src\\main\\resources\\connection.properties");
        DAO dao = new CarsDAO(factory);
        UserInterface dialog = new UserInterface();
        dao.createTable();
        boolean isExit = false;

        System.out.println(dialog.formatActionMenu());
        while (!isExit) {
            switch (dialog.getAction().toUpperCase()) {
                case "ВЫВОД":
                    System.out.println(dao.findAll());
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
