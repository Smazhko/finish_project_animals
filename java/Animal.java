import java.util.ArrayList;
import java.util.Random;

public abstract class Animal {
    private Integer id;         // динамическое поле, которое будет самостоятельным у каждого наследника класса
    private String name;        // имя животного
    private String gender;      // пол животного
    private String birthday;    // дата рождения животного
    private String type;        // наименование типа (домашние/вьючные) на русском - для вывода на печать
    private String animalClass; // класс животного - кошка, собака и пр.
    private static int newID;   // статическое поле, которое не будет принадлежать никому из наследников класса
    private ArrayList<String> commandList; // список команд - будет содержать только уникальные команды на случай,
                                         // если пользователь захочет продублировать команду

    static { // инициализация статического поля
        newID = new Random().nextInt(100,500);
    }

    /**
     * Первый конструктор класса - без списка команд
     * @param name имя животного
     * @param gender пол
     * @param birthday дата рождения
     */
    public Animal(String name, String gender, String birthday) {
        // изменение ID через посредника, имеющего статическое свойство, для того,
        // чтобы поле ID было у каждого нового объекта своё и имело уникальное значение
        id = newID++;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        commandList = new ArrayList<>();
    }

    /**
     * Второй конструктор класса - со списком команд
     * @param name имя животного
     * @param gender пол
     * @param birthday дата рождения
     * @param commandList список команд ArrayList
     */
    public Animal(String name, String gender, String birthday, ArrayList<String> commandList) {
        // изменение ID через посредника, имеющего статическое свойство, для того,
        // чтобы поле ID было у каждого нового объекта своё и имело уникальное значение
        id = newID++;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.commandList = commandList;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGender() {
        return gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public ArrayList<String> getCommandList() {
        return commandList;
    }

    public void setCommandList(ArrayList<String> commandList) {
        this.commandList = commandList;
    }

    public String getAnimalClass() {
        return animalClass;
    }

    public void setAnimalClass(String animalClass) {
        this.animalClass = animalClass;
    }

    /**
     * Форматированный вывод всех данных о животном<br>
     * Список команд commandList склеивается в одну строку при помощи String.join
     * @return
     */
    @Override
    public String toString() {
        String cmnds;
        if (commandList == null) {
           cmnds = "";
        } else {
            cmnds = String.join(",", commandList);
        }
        return String.format("%-3s %-10s  %-30s  %-3s  %-10s  %-60s", id, name,
                "(" + animalClass + " - " + type + ")",
                gender, birthday, cmnds);
    }

    public void addCommand (String command){
        commandList.add(command);
    }

}
