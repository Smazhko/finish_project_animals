import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class AnimalModel {
    private File dataFile = new File("AnimalsBase.txt");
    private ArrayList<Animal> allAnimalsList;
    private Animal currentAnimal = null;


    /**
     * <u>Загрузка базы</u> животных
     * Если файл для загрузки отсутствует, он создаётся вновь.
     * Парсинг файла, распределение данных по переменным.
     * В результате создаётся список allAnimalsList, состоящий из Animal.
     * Класс каждого Animal (кошка, собака) определяется автоматически.
     */
    public void loadBase() {
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException ex) {
                System.out.println("Не могу создать файл AnimalsBase.txt. " + ex.getMessage());
            }
        }
        
        AnimalFactory anFact = new AnimalFactory();
        
        if (dataFile.canRead()) {
            try {
                allAnimalsList = new ArrayList<>();
                Scanner fileScanner = new Scanner(dataFile);
                while (fileScanner.hasNextLine()) {
                    String[] info = fileScanner.nextLine().split(",");
                    String animalClass    = info[1].replaceAll("'", "");
                    String animalName     = info[2].replaceAll("'", "");
                    String animalGender   = info[3].replaceAll("'", "");
                    String animalBirthday = info[4].replaceAll("'", "");
                    ArrayList<String> animalCommands = new ArrayList<>();
                    if (!info[5].equals("NULL")) {
                        Collections.addAll(animalCommands, info[5].split(":"));
                    } else {
                        animalCommands = null;
                    }
                    Animal newAnimal = switch (animalClass) {
                        case "кошка" ->
                            anFact.createCat(animalName, animalGender, animalBirthday, animalCommands);
                        case "собака" ->
                            anFact.createDog(animalName, animalGender, animalBirthday, animalCommands);
                        case "хомячок" ->
                            anFact.createHamster(animalName, animalGender, animalBirthday, animalCommands);
                        case "верблюд" ->
                            anFact.createCamel(animalName, animalGender, animalBirthday, animalCommands);
                        case "лошадь" ->
                            anFact.createHorse(animalName, animalGender, animalBirthday, animalCommands);
                        case "осёл" ->
                            anFact.createDonkey(animalName, animalGender, animalBirthday, animalCommands);
                        default ->
                            throw new IllegalStateException("Unexpected value: " + animalClass);
                    };
                    allAnimalsList.add(newAnimal);
                }
                fileScanner.close();
            } catch (FileNotFoundException e) {
                System.out.println("Не могу прочитать файл базы данных: " + dataFile.getPath() + dataFile.getName());
                e.printStackTrace();
            }
        }
    }


    /**
     * <u>Сохранение базы</u><br>
     * Если файл для сохранения не существует, он создаётся вновь.
     * Запись данных в необходимом формате.
     */
    public void saveBase() {
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

        if (dataFile.canWrite()) {
            try (FileWriter fwriter = new FileWriter(dataFile, false)) {
                for (var curAnimal : allAnimalsList) {
                    String cmnds = "NULL";
                    if (curAnimal.getCommandList() != null){
                        cmnds = String.join(":", curAnimal.getCommandList());
                    }
                    fwriter.write(
                            "'" + curAnimal.getType() + "','" +
                                    curAnimal.getAnimalClass() + "','" +
                                    curAnimal.getName() + "','" +
                                    curAnimal.getGender() + "','" +
                                    curAnimal.getBirthday() + "'," +
                                    cmnds + "\n");
                }
                fwriter.flush();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }


    /**
     * <u>Добавление нового животного.</u><br>
     * После добавления животного в список база сохраняется.
     */
    public void addNewAnimal(Animal newAnimal) {
        allAnimalsList.add(newAnimal);
        saveBase();
    }

    /**
     * Добавление <u>новой команды</u> животному.<br>
     * Если животное не знает ни одной команды (список команд = null),
     * для него вновь создаётся новый список.
     * При попытке добавить уже существующую команду, этого не произойдёт
     * (список команд при добавлении временно преобразуется в HashSet,
     * содержащий только уникальные значения).
     * После попытки добавления база сохраняется.
     */
    public void addNewCommand(Animal currentAnimal, String newCommand){
        if (currentAnimal.getCommandList() == null) {
            currentAnimal.setCommandList(new ArrayList<>());
        }
        currentAnimal.getCommandList().add("'" + newCommand + "'");
        // избавимся от дубликатов в списке команд
        HashSet<String> tempHS = new HashSet<>(currentAnimal.getCommandList());
        ArrayList<String> tempAL = new ArrayList<>(tempHS);
        currentAnimal.setCommandList(tempAL);

        saveBase();
    }


    /**
     * Удаление ТЕКУЩЕГО животного с сохранением базы. <br>
     * Переменной, содержащей ТЕКУЩЕЕ животное, присваивается null.
     * База сохраняется.
     */
    public void removeAnimal(Animal currentAnimal){
        allAnimalsList.remove(currentAnimal);
        //System.out.println(currentAnimal.toString());
        currentAnimal = null;
        saveBase();
    }


    /**
     * Поиск животного<br>
     * Поиск в списке <code>allAnimalsList</code> происходит по ID -
     * используется <b>stream</b> с лямбда-выражением.
     *
     * @param curID ID животного из списка, которое надо найти
     * @return объект Animal, соответствующий искомому ID
     */
    public Animal findAnimal(String curID){
        currentAnimal = allAnimalsList
                .stream()
                .filter(test -> (test.getId() == Integer.parseInt(curID)))
                .findFirst()
                .orElse(null);
        return currentAnimal;
    }


    /**
     * Вывод на экран инфо ВСЕХ животных<br>
     * @return true - при успешном выводе, <br>
     * false - если список пуст и выводить нечего
     */
    public boolean getAllAnimalsInfo() {
        if (!allAnimalsList.isEmpty()) {
            for (Animal curAnimal : allAnimalsList) {
                System.out.println(curAnimal.toString());
            }
        } else {
            return false;
        }
        return true;
    }

}