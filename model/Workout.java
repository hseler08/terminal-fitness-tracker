package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Workout implements Serializable {
    private String name;
    private List<TrainingSession> trainingSessions;
    private List<Exercise> exercises;
    public Workout(String name) {
        this.name = name;
        this.trainingSessions = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TrainingSession> getTrainingSessions() {
        return trainingSessions;
    }

    public void addTrainingSession(TrainingSession trainingSession) {
        trainingSessions.add(trainingSession);
    }

    public void removeTrainingSession(TrainingSession trainingSession) {
        trainingSessions.remove(trainingSession);
    }


    public String toString() {
        return name;
    }
}
