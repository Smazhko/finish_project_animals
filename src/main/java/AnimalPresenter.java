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

public class AnimalPresenter {
    AnimalModel model;
    AnimalView view;

    public AnimalPresenter(AnimalView view, AnimalModel model) {
        this.model = model;
        this.view = view;
        model.loadBase();
    }

    public void startMenu(){
        boolean continueFlag = true;
        while (continueFlag){
            switch (view.getMainMenuChoise()){
                case 1:
                    // Посмотреть список животных
                    view.allAnimalsTitle();
                    if (model.getAllAnimalsInfo() == false) {
                        view.sayListIsEmpty();
                    }
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
     * Создание нового животного
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

    public void removeAnimal(){
        view.allAnimalsTitle();
        if (!model.getAllAnimalsInfo()) {
            view.sayListIsEmpty();
        }
        Animal currentAnimal = model.findAnimal(view.getCurrentAnimal());

        if (currentAnimal != null) {
            view.showCurrentAnimal();
            System.out.println(currentAnimal.toString());
            if(view.getDeletingAgree()){
                model.removeAnimal(currentAnimal);
                view.sayAnimalDeletingSuccessful();
            }
        } else {
            view.sayAnimalIsAbsent();
        }
    }

    public void addNewComand(){
        view.allAnimalsTitle();
        if (!model.getAllAnimalsInfo()) {
            view.sayListIsEmpty();
        }
        Animal currentAnimal = model.findAnimal(view.getCurrentAnimal());
        if (currentAnimal != null) {
            view.showCurrentAnimal();
            System.out.println(currentAnimal.toString());
            String newCmd = view.getNewCommand();
            if (newCmd != null) {
                model.addNewCommand(currentAnimal, newCmd);
            }
        } else {
            view.sayAnimalIsAbsent();
        }
    }
}
