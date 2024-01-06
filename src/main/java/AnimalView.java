import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
/*
 НАЗНАЧЕНИЕ КЛАССА (В МОДЕЛИ MVP):
 отображение данных для пользователя,
 обработка пользовательского ввода,
 не содержит бизнес-логики,
 только отображает данные, полученные от презентера,
 передает пользовательский ввод обратно презентеру.
*/

public class AnimalView extends ColoredConsole{

    private String[] mainMenuLines = new String[]{
            "Посмотреть список животных",
            "ДОБАВИТЬ новое животное",
            "Добавить животному КОМАНДУ",
            "УДАЛИТЬ животное из реестра",
            //"Редактировать общий СПИСОК КОМАНД",
            "Выйти из программы"
    };

    private String[] addAnimalMenuLines = new String[]{
            "кошка",
            "собака",
            "хомячок",
            "верблюд",
            "лошадь",
            "осёл"
    };


    private static final String REGEX_NAME = "[a-zA-Zа-яёА-ЯЁ0-9\\-\\_]+";
    private static final String REGEX_GENDER = "^[МЖмж]$";  // ^ начало слова, [] - интервал для одной буквы, $ конец слова
    private static final String REGEX_COMMAND = "[a-zA-Zа-яёА-ЯЁ0-9 \\-!\\?]+";


    private Integer menuSelection(String[] menu, String title) {
        printTitle(title);
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy (EEE), HH:mm");

        System.out.println(ANSI_LIGHT + ANSI_WHITE + dateFormat.format(new Date()) + ANSI_DEFAULT);

        for (int i = 0; i < menu.length; i++) {
            System.out.printf("[ %d ]  %s %n", i + 1, menu[i]);
        }

        return Integer.parseInt(controlledInput("Выберите пункт", "[1-" + menu.length + "]"));
    }


    public String[] getNewAnimal(Integer choise) {
        printTitle("ДОБАВЛЕНИЕ НОВОГО ЖИВОТНОГО (" + addAnimalMenuLines[choise - 1] + ")");
        String name     = controlledInput("Имя (АЯAZ09-_) ", REGEX_NAME);
        String gender   = controlledInput("Пол (М / Ж)    ", REGEX_GENDER);
        String birthday = verifyDateInput("Дата рожд-я (ДД.ММ.ГГГГ)");

        return new String[]{name, gender, birthday};
    }


    public void allAnimalsTitle(){
        printTitle("СПИСОК ВСЕХ ЖИВОТНЫХ ПИТОМНИКА");
        System.out.printf("%-3s %-10s  %-30s  %-3s  %-10s  %-60s%n",
                "id", "Имя", "Тип и класс животного", "Пол", "Дата рожд.", "Список команд");
    }


    public String getNewCommand() {
        printTitle("ДОБАВЛЕНИЕ НОВОЙ КОМАНДЫ");
        return controlledInput("Обучите выбранное животное новой команде", REGEX_COMMAND);
    }

    public boolean getDeletingAgree() {
        printTitle("!!!   УДАЛЕНИЕ ЖИВОТНОГО   !!!");
        printError("Вы уверены в том, что хотите удалить запись о животном из реестра? (0 - нет, 1 - да)");

        return controlledInput("Выберите действие", "^[01]$").equals("1");
        // вернёт true если ==1 и false в других случаях
    }

    public Integer getMainMenuChoise(){
        return menuSelection(mainMenuLines, "ГЛАВНОЕ МЕНЮ");
    }

    public Integer getAddMenuChoise(){
        return menuSelection(addAnimalMenuLines, "ДОБАВЛЕНИЕ НОВОГО ЖИВОТНОГО");
    }



    private String controlledInput(String message, String regEx){
        Scanner inputStr = new Scanner(System.in);
        boolean continueFlag = true;
        String userInput = null;

        while (continueFlag) {
            System.out.print(ANSI_YELLOW + ANSI_ITALIC + message + " > " + ANSI_DEFAULT);
            userInput = inputStr.nextLine();
            if (userInput.matches(regEx)) {
                continueFlag = false;
            } else {
                printError("Некорректный ввод. Попробуйте ещё раз.");
            }
        }
        return userInput;
    }

    private String verifyDateInput(String message){
        Scanner inputStr = new Scanner(System.in);
        boolean continueFlag = true;
        String userInput = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        sdf.setLenient(false);

        while (continueFlag) {
            System.out.print(ANSI_YELLOW + ANSI_ITALIC + message + " > " + ANSI_DEFAULT);
            userInput = inputStr.nextLine();
            Date newdate = null;

            try {
                newdate = sdf.parse(userInput);

                if (newdate!=null && newdate.after(new java.util.Date())) {
                    printError("Некорректный ввод (дата из будущего). Попробуйте ещё раз.");
                    continueFlag = true;
                } else {
                    continueFlag = false;
                }
            } catch (ParseException e) {
                printError("Некорректный ввод. Попробуйте ещё раз.");
            }
        }
        return userInput;
    }

    private void printTitle(String title) {
        String borderColor = ANSI_BRIGHT_BLUE + ANSI_BOLD;
        String textColor = ANSI_BRIGHT_GREEN + ANSI_BOLD;
        System.out.println();
        System.out.println(borderColor + "╓" + "─".repeat(title.length() + 2) + "╖");
        System.out.println("║ " + textColor + title + borderColor + " ║");
        System.out.println("╙" + "─".repeat(title.length() + 2) + "╜" + ANSI_DEFAULT);
    }

    private void printMessage(String message) {
        System.out.println(ANSI_BRIGHT_BLUE + message + ANSI_DEFAULT);
    }

    private void printError(String message) {
        System.out.println(ANSI_BRIGHT_RED + "<< ! >> " + message + ANSI_DEFAULT);
    }


    public String getCurrentAnimal() {
        System.out.print(ANSI_YELLOW + ANSI_ITALIC + "Введите ID искомого животного" + " > " + ANSI_DEFAULT);
        Scanner inputStr = new Scanner(System.in);
        String userInput = inputStr.nextLine();
        return userInput;
    }

    public void showCurrentAnimal() {
        printMessage("Вы выбрали следующее животное: ");
        System.out.printf("%-3s %-10s  %-30s  %-3s  %-10s  %-60s%n",
                "id", "Имя", "Тип и класс животного", "Пол", "Дата рожд.", "Список команд");
    }

    public void sayAnimalDeletingSuccessful() {
        printError("Запись о животном успешно удалена из реестра!");
    }

    public void sayListIsEmpty() {
        printError("База данных животных пуста. Выводить на печать нечего!");
    }

    public void sayAnimalIsAbsent(){
        printError("Запись о животном с указанным ID отсутствует.");
    }

    public void sayBye() {
        printTitle("ДО ВСТРЕЧИ !");
    }
}
