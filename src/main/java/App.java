public class App {
    public static void main(String[] args) {
        TableMaker tableMaker = new TableMaker();
        System.out.println("Hello");
        tableMaker.createTableCars();

        while (!tableMaker.isClose()) {
            tableMaker.dropTable();
        }
    }
}
