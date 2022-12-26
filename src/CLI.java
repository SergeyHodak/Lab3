import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Інтерфейс консольного рядка
public class CLI <T> {
    private final List<UniversalBankCard<T>> clients = new ArrayList<>(); // Список клієнтів
    Scanner scanner = new Scanner(System.in);

    // Отримати інформацію про клієнта за id. "Вважаю що в цьому випадку мається на увазі повернути поле ПІБ клієнта"
    private String getInformationAboutTheClientById(T id) {
        for (UniversalBankCard<T> client : clients) { // Біг по списку клієнтів
            if (client.getId().equals(id)) { // Якщо id цього клієнта відповідає шуканому
                return client.getFullNameOfTheClient(); // Віддати його ПІБ
            }
        }
        return null; // Такого клієнта не знайдено
    }

    // Отримати інформацію про кошти на рахунках клієнта за ідентифікатором
    private  <N> N getInformationAboutTheFundsOnTheClientsAccountsById(T id) {
        UniversalBankCard<T> client = null; // Клієнт
        for (UniversalBankCard<T> unit : clients) { // Біг по списку клієнтів
            if (unit.getId().equals(id)) { // Якщо id цього клієнта відповідає шуканому
                client = unit; // Зареєструвати в змінну
                break; // Завершити цикл
            }
        }
        if (client == null) { // Якщо клієнта не знайдено
            return null; // Такого клієнта не знайдено
        }
        if (client.getCreditAccount() > 0 & client.getPersonalAccount() > 0) { // Якщо обидва рахунки не пусті
            return (N) Double.valueOf(client.getCreditAccount() + client.getPersonalAccount());
        } else if (client.getCreditAccount() > 0) {
            return (N) Integer.valueOf(client.getCreditAccount());
        } else if (client.getPersonalAccount() > 0){
            return (N) Float.valueOf(client.getPersonalAccount());
        }
        return null; // Рахунки пусті
    }

    // Запуск консольного інтерфейс меню
    public <N> void run() {
        while (true) { // Нескінченний цикл
            String infoMainMenu = """
                    Введіть команду для продовження:
                    1 - Зареєструвати нового клієнта;
                    2 - Редагувати інформацію клієнта по id;
                    3 - Отримати інформацію про клієнта по id;
                    4 - Отримати рахунок;
                    5 - Вихід.
                    """;
            System.out.println(infoMainMenu); // Друкувати інформацію про доступні команди
            int command = getInt(); // Отримати команду від користувача
            switch (command) { // Пошук прописаної інструкції до введеної команди
                case 1 -> { // Зареєструвати нового клієнта
                    if (createClient()) { // Створити нового клієнта
                        System.out.println("Реєстрація пройшла успішно!");
                    } else {
                        System.out.println("Реєстрація зазнала невдачі!");
                    }
                }
                case 2 -> { // Редагувати інформацію клієнта по id
                    T id = menuGetId(); // Отримати id
                    if (id != null) {
                        menuUpdateInfoById(id); // Перейти в меню редагування
                    } else {
                        System.out.println("Такого клієнта немає");
                    }
                }
                case 3 -> { // Отримати інформацію про клієнта по id
                    T id = menuGetId(); // Отримати id
                    String info = getInformationAboutTheClientById(id);// Отримати інформацію
                    System.out.println(info); // Друкувати в консоль результат
                }
                case 4 -> { // Отримати рахунок
                    T id = menuGetId(); // Отримати id
                    if (id == null) {
                        System.out.println("Такого клієнта немає");
                        continue;
                    }
                    N info = getInformationAboutTheFundsOnTheClientsAccountsById(id); // Отримати інформацію
                    if (info == null) {
                        System.out.println("Рахунки в цього клієнта пусті");
                        continue;
                    }
                    Class<?> aClass = info.getClass();
                    if (Double.class.equals(aClass)) {
                        System.out.println("Сума на рахунках клієнта = " + info);
                    } else if (Integer.class.equals(aClass)) {
                        System.out.println("На кредитному рахунку клієнта = " + info);
                    } else if (Float.class.equals(aClass)) {
                        System.out.println("На особистому рахунку клієнта = " + info);
                    } else {
                        System.out.println("Тип в невідомому форматі" + info);
                    }
                }
                case 5 -> { // Вихід
                    scanner.close();
                    return;
                }
                default -> System.out.println("Така команда не зареєстрована!");
            }
        }
    }

    // Отримати ціле число з консолі від користувача
    private int getInt() {
        while (true) { // Нескінчений цикл
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (Exception ex) {
                System.out.println("Введіть ціле число, ще раз, але коректно!");
            }
        }
    }

    // Створити нового клієнта
    private boolean createClient() {
        while (true) { // Нескінчений цикл
            T id = menuGetId(); // Отримати ідентифікатор користувача
            if (id == null) {
                return false;
            }
            if (!exist(id)) { // Якщо такий ідентифікатор вже є
                System.out.println("Ідентифікатор вже існує в базі!");
                continue; // повернутись в початок
            }

            // Повідомлення для користувача, який від нього очікує програма ввід даних
            System.out.println("Введіть ПІБ клієнта:");
            String fullNameOfTheClient = getString(); // ПІБ клієнта
            // Повідомлення для користувача, який від нього очікує програма ввід даних
            System.out.println("Введіть значення для пля \"кредитний рахунок\" типу int:");
            int creditAccount = getInt(); // Кредитний рахунок
            // Повідомлення для користувача, який від нього очікує програма ввід даних
            System.out.println("Введіть значення для пля \"особистий рахунок\" типу float:");
            float personalAccount = getFloat(); // Кредитний рахунок
            UniversalBankCard<T> client = new UniversalBankCard<>(); // Екземпляр класу клієнт
            client.setId(id); // Встановити id
            client.setFullNameOfTheClient(fullNameOfTheClient); // Встановити ПІБ клієнта
            client.setCreditAccount(creditAccount); // Встановити кредитний рахунок
            client.setPersonalAccount(personalAccount); // Встановити особистий рахунок
            clients.add(client); // Додати в базу даних
            return true; // Реєстрація виконана успішно
        }
    }

    // Меню вводу Ідентифікатора
    private T menuGetId() {
        // Повідомлення для користувача, який від нього очікує програма ввід даних
        String message = """
                Оберіть якого типу ви будете вводити ідентифікатор:
                1 - Текстовий рядок,
                2 - Ціле число,
                3 - Скасовувати дію.
                """;
        while (true) { // Нескінчений цикл
            System.out.println(message); // Друкувати інформацію про доступні команди
            int command = getInt(); // Отримати команду
            switch (command) {
                case  1 -> { // Текстовий рядок
                    // Повідомлення для користувача, який від нього очікує програма ввід даних
                    System.out.println("Введіть id типу String:");
                    return (T) getString();
                }
                case  2 -> { // Ціле число
                    // Повідомлення для користувача, який від нього очікує програма ввід даних
                    System.out.println("Введіть id типу Long:");
                    return (T) Long.valueOf(getLong());
                }
                case  3 -> { // Скасовувати дію
                    return null;
                }
                default -> System.out.println("Такої команди немає в даному списку!");
            }
        }
    }


    // Отримати ціле число з консолі від користувача
    private long getLong() {
        while (true) { // Нескінчений цикл
            try {
                return Long.parseLong(scanner.nextLine());
            } catch (Exception ex) {
                System.out.println("Введіть ціле число, ще раз, але коректно!");
            }
        }
    }

    // Отримати текстовий рядок з консолі від користувача
    private String getString() {
        while (true) { // Нескінчений цикл
            try {
                String result = scanner.nextLine();
                if (result.length() > 0) {
                    return result;
                } else {
                    System.out.println("Рядок не має бути пустим!");
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    // Перевірка чи немає, в списку клієнта з таким ідентифікатором
    private boolean exist(T id) {
        return getInformationAboutTheClientById(id) == null;
    }

    // Отримати число з плаваючою точкою, з консолі від користувача
    private float getFloat() {
        while (true) { // Нескінчений цикл
            try {
                return Float.parseFloat(scanner.nextLine());
            } catch (Exception ex) {
                System.out.println("Введіть число з плаваючою точкою, використовуючи крапку як роздільник");
            }
        }
    }

    // Меню редагування інформації по id
    private void menuUpdateInfoById(T id) {
        UniversalBankCard<T> client = null; // Клієнт
        for (UniversalBankCard<T> unit : clients) { // Біг по базі з клієнтами
            System.out.println("unit.getId() = " + unit.getId());
            System.out.println("id = " + id);
            if (unit.getId().equals(id)) {
                client = unit;
                break;
            }
        }
        if (client == null) {
            System.out.println("Клієнта не знайдено в базі");
            return;
        }
        UniversalBankCard<T> result = new UniversalBankCard<>(); // Для збереження змін
        result.setFullNameOfTheClient(null);
        result.setCreditAccount(-1);
        result.setPersonalAccount(-1);

        String message = """
                Оберіть яке поле будете редагувати:
                1 - ПІБ клієнта,
                2 - Кредитний рахунок,
                3 - Особистий рахунок,
                4 - Зберегти та завершити редагування,
                5 - Завершити без зберігання змін.
                """;
        while (true) { // Нескінчений цикл
            System.out.println(message); // Друкувати інформацію про доступні команди
            int command = getInt(); // Отримати команду
            switch (command) {
                case 1 -> { // ПІБ клієнта
                    System.out.println("Поле \"ПІБ клієнта\" має значення = \"" + client.getFullNameOfTheClient() + "\".");
                    System.out.println("Введіть оновлення:");
                    result.setFullNameOfTheClient(getString()); // ПІБ клієнта
                }
                case 2 -> { // Кредитний рахунок
                    System.out.println("Поле \"Кредитний рахунок\" має значення = \"" + client.getCreditAccount() + "\".");
                    System.out.println("Введіть оновлення:");
                    result.setCreditAccount(getInt()); // Кредитний рахунок
                }
                case 3 -> { // Особистий рахунок
                    System.out.println("Поле \"Особистий рахунок\" має значення = \"" + client.getCreditAccount() + "\".");
                    System.out.println("Введіть оновлення:");
                    result.setPersonalAccount(getFloat()); // Особистий рахунок
                }
                case 4 -> { // Зберегти та завершити редагування
                    if (result.getFullNameOfTheClient() != null) {
                        client.setFullNameOfTheClient(result.getFullNameOfTheClient());
                    }
                    if (result.getCreditAccount() != -1) {
                        client.setCreditAccount(result.getCreditAccount());
                    }
                    if (result.getPersonalAccount() != -1) {
                        client.setPersonalAccount(result.getPersonalAccount());
                    }
                    return;
                }
                case 5 -> { // Завершити без зберігання змін
                    return;
                }
                default -> System.out.println("Такої команди немає в даному списку!");
            }
        }
    }
}
