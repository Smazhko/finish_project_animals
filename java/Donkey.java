import java.util.ArrayList;

public class Donkey extends PackAnimal{

    public Donkey(String name, String gender, String birthday) {
        super(name, gender, birthday);
        this.setAnimalClass("осёл");
    }

    public Donkey(String name, String gender, String birthday, ArrayList<String> commandList) {
        super(name, gender, birthday, commandList);
        this.setAnimalClass("осёл");
    }
}
