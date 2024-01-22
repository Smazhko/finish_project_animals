import java.util.ArrayList;

public class Cat extends Pet{

    public Cat(String name, String gender, String birthday) {
        super(name, gender, birthday);
        setAnimalClass("кошка");
    }

    public Cat(String name, String gender, String birthday, ArrayList<String> commandList) {
        super(name, gender, birthday, commandList);
        setAnimalClass("кошка");
    }
}
