/*
 НАЗНАЧЕНИЕ КЛАССА (В МОДЕЛИ MVP) - связывает модель и представление.
 > получает пользовательский ввод из представления,
 > обрабатывает его и обновляет модель.
 > Затем получает обновленные данные из модели
 > передает их обратно представлению для отображения.

 Презентер также может:
 > обрабатывать другие события
 > обновлять представление соответствующим образом.
*/


import java.util.Scanner;

/**
 * Контролирующий класс. Манипулирует AnimalView и AnimalModel
 * для обработки данных, введенных пользователем, осуществления команд бизнес логики.
 */

public class AnimalPresenter {
    AnimalModel model;
    AnimalView view;

    /**
     * Конструктор класса - принимает AnimalView и AnimalModel.
     * Происходит загрузка данных о животных из файла.
     * @param view экземпляр класса AnimalView
     * @param model экземпляр класса AnimalModel
     */
    public AnimalPresenter(AnimalView view, AnimalModel model) {
        this.model = model;
        this.view = view;
        model.loadBase();
    }

    /**
     * Главное меню программы <br>
     * Бесконечный цикл, в котором происходит ожидание выбора пользователем пункта меню
     * и вызов новых меню или осуществление соответствующих манипуляций с данными. <br>
     * Прерывание цикла - выбор пользователем пункта меню "Выход".
     */
    public void startMenu(){
        boolean continueFlag = true;
        while (continueFlag){
            switch (view.getMainMenuChoise()){
                case 1:
                    // Посмотреть список животных
                    viewingAnimalsList();
                    break;
                case 2:
                    // ДОБАВИТЬ новое животное
                    addNewAnimal();
                    break;
                case 3:
                    // Добавить животному КОМАНДУ
                    addNewComand();
                    break;
                case 4:
                    //УДАЛИТЬ животное из реестра
                    removeAnimal();
                    break;
                case 5:
                    // "Выйти из программы"
                    view.sayBye();
                    continueFlag = false;
                    System.exit(0);
                    break;
            }
        }
    }

    /**
     * <u>Декорированный вывод списка всех животных.</u><br>
     * Если список пуст - вывод соответствующего сообщения.
     */
    private void viewingAnimalsList() {
        view.allAnimalsTitle();
        if (!model.getAllAnimalsInfo()) {
            view.sayListIsEmpty();
        }
    }

    /**
     * <u>Создание нового животного</u><br>
     * Происходит вызов дополнительного меню с выбором типа нового животного.
     * Собираем данные о новом животном - имя, пол и др.
     * Используется фабрика классов - в зависимости от выбора пользователя.
     * Новое животное создаётся с пустым списком команд.
     * Добавление команд доступно через другой пункт меню.
     */
    public void addNewAnimal(){
        Integer choice = view.getAddMenuChoise();
        String[] userInfo = view.getNewAnimal(choice);
        String animalName     = userInfo[0];
        String animalGender   = userInfo[1];
        String animalBirthday = userInfo[2];
        AnimalFactory anFact = new AnimalFactory();
        Animal newAnimal     = switch (choice) {
            case 1 ->
                    anFact.createCat(animalName, animalGender, animalBirthday, null);
            case 2 ->
                    anFact.createDog(animalName, animalGender, animalBirthday, null);
            case 3 ->
                    anFact.createHamster(animalName, animalGender, animalBirthday, null);
            case 4 ->
                    anFact.createCamel(animalName, animalGender, animalBirthday, null);
            case 5 ->
                    anFact.createHorse(animalName, animalGender, animalBirthday, null);
            case 6 ->
                    anFact.createDonkey(animalName, animalGender, animalBirthday, null);
            default ->
                    throw new IllegalStateException("Unexpected value: " + choice);
        };
        model.addNewAnimal(newAnimal);
    }

    /**
     * <u>Удаление животного из списка</u><br>
     * Вывод списка всех животных пользователю, чтобы он определился с ID того, кого ищем.<br>
     * Поиск животного по ID.<br>
     * Если поиск пуст - вывод соответствующего сообщения.<br>
     * Если поиск успешный - вывод данных о животном на экран и запрос пользователю для подтверждения удаления.
     * При подтверждении - удаление животного из списка.
     */
    public void removeAnimal(){
        viewingAnimalsList();
        Animal currentAnimal = model.findAnimal(view.getCurrentAnimal());

        if (currentAnimal != null) {
            view.showCurrentAnimal(currentAnimal.toString());
            if(view.getDeletingAgree()){
                model.removeAnimal(currentAnimal);
                view.sayAnimalDeletingSuccessful();
            }
        } else {
            view.sayAnimalIsAbsent();
        }
    }

    /**
     * <u>Добавление новой команды животному</u><br>
     * Вывод списка всех животных пользователю, чтобы он определился с ID того, кого ищем.<br>
     * Поиск животного по ID.<br>
     * Если поиск пуст - вывод соответствующего сообщения.<br>
     * Если поиск успешный - вывод данных о животном на экран и вывод диалога -
     * приём от пользователя новой команды. Если команда не пуста - добавление её животному.
     */
    public void addNewComand(){
        viewingAnimalsList();
        Animal currentAnimal = model.findAnimal(view.getCurrentAnimal());
        if (currentAnimal != null) {
            view.showCurrentAnimal(currentAnimal.toString());
            String newCmd = view.getNewCommand();
            if (newCmd != null) {
                model.addNewCommand(currentAnimal, newCmd);
            }
        } else {
            view.sayAnimalIsAbsent();
        }
    }

}
