package Utils;

import model.TrainingSession;
import model.Workout;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Data {
    private final String fileName;

    public Data(String fileName) {
        this.fileName = fileName;
    }

    public void saveData(List<Workout> wokout) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(wokout);
            System.out.println("Data saved to " + fileName);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    public List<Workout> loadData() {
        File file = new File(fileName);

        if (!file.exists() || file.length() == 0) {
            System.out.println("File does not exist or is empty. Creating a new file: " + fileName);
            try {
                if (file.createNewFile()) {
                    System.out.println("New file created: " + fileName);
                }
            } catch (IOException e) {
                System.out.println("Error creating new file: " + e.getMessage());
            }
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            System.out.println("Data successfully loaded from " + fileName);
            return (List<Workout>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}


