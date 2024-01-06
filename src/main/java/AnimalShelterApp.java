public class AnimalShelterApp {

    public static void main(String[] args) {
        AnimalModel model = new AnimalModel();
        AnimalView view = new AnimalView();

        AnimalPresenter presenter = new AnimalPresenter(view, model);
        presenter.startMenu();
    }

}
