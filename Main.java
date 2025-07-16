import model.*;
import Controller.*;
import View.*;
import java.util.ArrayList;
import java.util.List;
import Utils.Data;
import model.TrainingSession;
import javax.swing.DefaultListModel;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the file name to load data from:");
        String fileName = scanner.nextLine();

        Data dataManager = new Data(fileName);
        List<Workout> workouts = dataManager.loadData();
        if (workouts == null) {
            workouts = new ArrayList<>();
        }

        DefaultListModel<TrainingSession> sessionListModel = new DefaultListModel<>();
        DefaultListModel<Workout> workoutListModel = new DefaultListModel<>();
        DefaultListModel<Exercise> exerciseListModel = new DefaultListModel<>();
        for (Workout workout : workouts) {
            workoutListModel.addElement(workout);
        }

        Controller controller = new Controller(workouts, sessionListModel, workoutListModel, exerciseListModel, dataManager);
        MainView mainView = new MainView(controller);
        mainView.setVisible(true);
    }
}
