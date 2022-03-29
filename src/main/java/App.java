public class App {
    public static void main(String[] args) {
        DataAccessObject dao = new DataAccessObject();
        DialogWithUser dialog = new DialogWithUser();
        dao.createTable();
        boolean isExit = false;

        System.out.println(dialog.formatActionMenu());
        while (!isExit) {
            switch (dialog.getAction().toUpperCase()) {
                case "ВЫВОД":
                    System.out.println(dao.formatToStringFullTable());
                    break;
                case "ДОБАВИТЬ":
                    dao.addString(dialog.getDataForInsert());
                    break;
                case "УДАЛИТЬ":
                    dao.deleteString(dialog.getIdByUser());
                    break;
                case "ИЗМЕНИТЬ":
                    dao.updateString(dialog.getDataForUpdate());
                    break;
                case "СТРОЧКУ":
                    System.out.println(dao.formatForOutputOneString(dialog.getIdByUser()).toString());
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
