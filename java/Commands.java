import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Commands {
    private File dataFile = new File("commands.txt");
    private ArrayList<String> commandsList;

    public void addCommand(String command){
        commandsList.add(command);
    }

    public List<String> loadCommands() {

        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

        if(dataFile.canRead()) {
            try {
                Scanner fileScanner = new Scanner(dataFile);
                while (fileScanner.hasNextLine()) {
                    addCommand(fileScanner.nextLine());
                }
                fileScanner.close();
            } catch (FileNotFoundException e) {
                System.out.println("READ ERROR");
                throw new RuntimeException(e);
            }
        }
        return commandsList;
    }


    public void saveCommands(){
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

        if(dataFile.canWrite()) {
            try (FileWriter fwriter = new FileWriter(dataFile, false)) {
                for (var record: commandsList) {
                    fwriter.write(record + "\n");
                }
                fwriter.flush();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
