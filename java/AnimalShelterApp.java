/**
 * Главный класс программы с точкой входа PSVM MAIN.
 * Создаются экземпляры классов AnimalModel, AnimalView и  AnimalPresenter (модель MVP/MVC).
 * Запускается главное меню программы.
 */

public class AnimalShelterApp {

    public static void main(String[] args) {
        AnimalModel model = new AnimalModel();
        AnimalView view = new AnimalView();

        AnimalPresenter presenter = new AnimalPresenter(view, model);
        presenter.startMenu();
    }

}
