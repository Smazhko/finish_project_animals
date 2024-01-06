import java.util.ArrayList;

public abstract class PackAnimal extends Animal{


    public PackAnimal(String name, String gender, String birthday) {
        super(name, gender, birthday);
        setType("вьючные животные");
    }

    public PackAnimal(String name, String gender, String birthday, ArrayList<String> commandList) {
        super(name, gender, birthday, commandList);
        setType("вьючные животные");
    }
}
