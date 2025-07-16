package Controller;

import model.*;
import Utils.*;
import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class Controller {
    private List<Workout> workouts;
    private DefaultListModel<TrainingSession> sessionListModel;
    private DefaultListModel<Workout> workoutListModel;
    private DefaultListModel<Exercise> exerciseListModel;
    private Data dataManager;

    public Controller(List<Workout> workouts, DefaultListModel<TrainingSession> sessionListModel,
                      DefaultListModel<Workout> workoutListModel, DefaultListModel<Exercise> exerciseListModel,
                      Data dataManager) {
        this.workouts = workouts;
        this.sessionListModel = sessionListModel;
        this.workoutListModel = workoutListModel;
        this.exerciseListModel = exerciseListModel;
        this.dataManager = dataManager;
    }

    public List<Workout> getWorkouts() {
        return workouts;
    }

    public DefaultListModel<TrainingSession> getSessionListModel() {
        return sessionListModel;
    }

    public DefaultListModel<Workout> getWorkoutListModel() {
        return workoutListModel;
    }

    public DefaultListModel<Exercise> getExerciseListModel() {
        return exerciseListModel;
    }

    public boolean addSession(Workout workout, String dateText) throws DateTimeParseException {
        LocalDate date = LocalDate.parse(dateText);
        TrainingSession session = new TrainingSession(date);
        workout.addTrainingSession(session);
        sessionListModel.addElement(session);
        saveData();
        return true;
    }

    public boolean removeSession(Workout workout, TrainingSession session) {
        if (workout != null && session != null) {
            workout.removeTrainingSession(session);
            sessionListModel.removeElement(session);
            saveData();
            return true;
        }
        return false;
    }

    public boolean addWorkout(String workoutName) {
        if (workoutName != null && !workoutName.trim().isEmpty()) {
            Workout workout = new Workout(workoutName);
            workoutListModel.addElement(workout);
            workouts.add(workout);
            saveData();
            return true;
        }
        return false;
    }

    public boolean removeWorkout(Workout workout) {
        if (workout != null) {
            workoutListModel.removeElement(workout);
            workouts.remove(workout);
            saveData();
            return true;
        }
        return false;
    }

    public boolean removeExercise(TrainingSession session, Exercise exercise) {
        if (session != null && exercise != null) {
            session.removeExercise(exercise);
            exerciseListModel.removeElement(exercise);
            saveData();
            return true;
        }
        return false;
    }

    public boolean addExercise(TrainingSession session, String name, int reps, int sets, int weight) {
        if (session != null && name != null && !name.trim().isEmpty()) {
            Exercise exercise = new Exercise(name, reps, sets, weight);
            session.addExercise(exercise);
            exerciseListModel.addElement(exercise);
            saveData();
            return true;
        }
        return false;
    }

    public boolean setGoalForExercise(Exercise exercise, String goal, String deadlineText) throws DateTimeParseException {
        LocalDate deadline = LocalDate.parse(deadlineText);
        exercise.setGoal(goal, deadline);
        saveData();
        return true;
    }

    public void saveData() {
        dataManager.saveData(workouts);
    }
}

