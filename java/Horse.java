import java.util.ArrayList;

public class Horse extends PackAnimal{


    public Horse(String name, String gender, String birthday) {
        super(name, gender, birthday);
        this.setAnimalClass("лошадь");
    }

    public Horse(String name, String gender, String birthday, ArrayList<String> commandList) {
        super(name, gender, birthday, commandList);
        this.setAnimalClass("лошадь");
    }
}
