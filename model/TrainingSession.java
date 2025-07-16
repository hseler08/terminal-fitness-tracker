package model;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TrainingSession implements Serializable {
    private LocalDate date;
    private List<Exercise> exercises;

    public TrainingSession(LocalDate date) {
        this.date = date;
        this.exercises = new ArrayList<>();
    }

    public LocalDate getDate() {
        return date;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
    }

    public void removeExercise(Exercise exercise) {
        exercises.remove(exercise);
    }

    public String toString() {
        return date.toString();
    }
}
