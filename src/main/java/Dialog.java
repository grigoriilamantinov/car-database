import java.util.Scanner;

public class Dialog {
    Scanner sc = new Scanner(System.in);
    public static String NEXT_LINE = "\n";
    public static String TILDE = "~";
    StringBuilder sb = new StringBuilder();

    public String chooseAction() {
        System.out.println("Товарищ, что хотите сделать с данными?");
        String action = sc.nextLine();
        return action;
    }

    public String getData() {
        System.out.print("Напишите название машины: ");
        String brand = sc.nextLine();
        sb.append(brand + TILDE);
        System.out.print("Укажите год выпуска: ");
        int year = sc.nextInt();
        sb.append(year + TILDE);
        System.out.print("Укажите стоимость: ");
        int cost = sc.nextInt();
        sb.append(cost);

        return sb.toString();

    }

    public String actionMenu() {
        return NEXT_LINE +
                "Чтобы совершить действие с данными, введите ключевое слово:"
                + NEXT_LINE
                + "1. Вывод - покажет данные таблицы построчно."
                + NEXT_LINE
                + "2. Добавить - добавление новой строчки."
                + NEXT_LINE
                + "3. Удалить - удалить строчку по id."
                + NEXT_LINE
                + "4. Меню - покажет это меню ещё раз."
                + NEXT_LINE
                + "5. Выход - позволит кончить работу с этой таблицой"
                ;

    }

    public int idForDelete() {
        System.out.print("Какой id у записи для удаления? ");
        return sc.nextInt();
    }
}
