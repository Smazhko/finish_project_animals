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
     * Сохранение базы
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
     * <u>Добавление нового животного.</u>
     */
    public void addNewAnimal(Animal newAnimal) {
        allAnimalsList.add(newAnimal);
        saveBase();
    }

    /**
     * Добавление новой команды животному
     */
    public void addNewCommand(Animal currentAnimal, String newCommand){
        if (currentAnimal.getCommandList() == null) {
            currentAnimal.setCommandList(new ArrayList<>());
        }
        currentAnimal.getCommandList().add("'" + newCommand + "'");
        saveBase();
    }


    /**
     * Удаление ТЕКУЩЕГО животного с сохранением базы.
     */
    public void removeAnimal(Animal currentAnimal){
        allAnimalsList.remove(currentAnimal);
        //System.out.println(currentAnimal.toString());
        currentAnimal = null;
        saveBase();
    }


    public Animal findAnimal(String curID){
        currentAnimal = allAnimalsList
                .stream()
                .filter(test -> (test.getId() == Integer.parseInt(curID)))
                .findFirst()
                .orElse(null);
        return currentAnimal;
    }

    /**
     * Вывод на экран инфо ВСЕХ животных
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

