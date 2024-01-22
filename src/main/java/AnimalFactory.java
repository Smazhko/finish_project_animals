import java.util.ArrayList;

public class AnimalFactory {
    public AnimalFactory() {
    }

    public Animal createCat(String name, String gender, String birthday, ArrayList<String> commandList) {
        return new Cat(name, gender, birthday, commandList);
    }

    public Animal createDog(String name, String gender, String birthday, ArrayList<String> commandList) {
        return new Dog(name, gender, birthday, commandList);
    }

    public Animal createHamster(String name, String gender, String birthday, ArrayList<String> commandList) {
        return new Hamster(name, gender, birthday, commandList);
    }

    public Animal createCamel(String name, String gender, String birthday, ArrayList<String> commandList) {
        return new Camel(name, gender, birthday, commandList);
    }

    public Animal createDonkey(String name, String gender, String birthday, ArrayList<String> commandList) {
        return new Donkey(name, gender, birthday, commandList);
    }

    public Animal createHorse(String name, String gender, String birthday, ArrayList<String> commandList) {
        return new Horse(name, gender, birthday, commandList);
    }

}
