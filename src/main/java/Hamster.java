import java.util.ArrayList;

public class Hamster extends Pet{

    public Hamster(String name, String gender, String birthday) {
        super(name, gender, birthday);
        this.setAnimalClass("хомячок");
    }

    public Hamster(String name, String gender, String birthday, ArrayList<String> commandList) {
        super(name, gender, birthday, commandList);
        this.setAnimalClass("хомячок");
    }
}
