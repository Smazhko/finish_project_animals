import java.util.ArrayList;


public class Dog extends Pet{

    public Dog(String name, String gender, String birthday) {
        super(name, gender, birthday);
        this.setAnimalClass("собака");
    }

    public Dog(String name, String gender, String birthday, ArrayList<String> commandList) {
        super(name, gender, birthday, commandList);
        this.setAnimalClass("собака");
    }
}
