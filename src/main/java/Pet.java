import java.util.ArrayList;
import java.util.HashSet;

public abstract class Pet extends Animal{


    public Pet(String name, String gender, String birthday) {
        super(name, gender, birthday);
        setType("домашние животные");
    }

    public Pet(String name, String gender, String birthday, ArrayList<String> commandList) {
        super(name, gender, birthday, commandList);
        setType("домашние животные");
    }
}
