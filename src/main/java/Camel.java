import java.util.ArrayList;

public class Camel extends PackAnimal{

    public Camel(String name, String gender, String birthday) {
        super(name, gender, birthday);
        this.setAnimalClass("верблюд");
    }

    public Camel(String name, String gender, String birthday, ArrayList<String> commandList) {
        super(name, gender, birthday, commandList);
        this.setAnimalClass("верблюд");
    }
}
