package View;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import Controller.Controller;
import model.*;
import javax.swing.*;
import java.time.format.DateTimeParseException;


public class MainView extends JFrame {
    private JList<Workout> workoutList;
    private DefaultListModel<Workout> workoutListModel;
    private JList<TrainingSession> sessionList;
    private DefaultListModel<TrainingSession> sessionListModel;
    private JList<Exercise> exerciseList;
    private DefaultListModel<Exercise> exerciseListModel;
    private Controller controller;
    private JFrame frame;

    public MainView(Controller controller) {
        this.controller = controller;

        this.sessionListModel = controller.getSessionListModel();
        this.workoutListModel = controller.getWorkoutListModel();
        this.exerciseListModel = controller.getExerciseListModel();

        this.sessionList = new JList<>(sessionListModel);
        this.workoutList = new JList<>(workoutListModel);
        this.exerciseList = new JList<>(exerciseListModel);

        initializeUI();
    }

    private void initializeUI() {

        setTitle("Fitness Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(800, 600);

        JPanel workoutPanel = new JPanel(new BorderLayout());
        JPanel sessionPanel = new JPanel(new BorderLayout());
        JPanel exercisePanel = new JPanel(new BorderLayout());
        JPanel controlPanel = new JPanel(new GridLayout(1, 3));

        workoutPanel.add(new JLabel("Workouts:"), BorderLayout.NORTH);
        workoutPanel.add(new JScrollPane(workoutList), BorderLayout.CENTER);

        JButton addWorkoutButton = new JButton("Add Workout");
        JButton removeWorkoutButton = new JButton("Remove Workout");
        JPanel workoutButtons = new JPanel(new GridLayout(1, 2));
        workoutButtons.add(addWorkoutButton);
        workoutButtons.add(removeWorkoutButton);
        workoutPanel.add(workoutButtons, BorderLayout.SOUTH);

        sessionPanel.add(new JLabel("Training Sessions in Workout:"), BorderLayout.NORTH);
        sessionPanel.add(new JScrollPane(sessionList), BorderLayout.CENTER);

        JButton addSessionButton = new JButton("Add Session");
        JButton deleteSessionButton = new JButton("Delete Session");
        JPanel sessionButtons = new JPanel(new GridLayout(1, 2));
        sessionButtons.add(addSessionButton);
        sessionButtons.add(deleteSessionButton);
        sessionPanel.add(sessionButtons, BorderLayout.SOUTH);

        exercisePanel.add(new JLabel("Exercises in Session:"), BorderLayout.NORTH);
        exercisePanel.add(new JScrollPane(exerciseList), BorderLayout.CENTER);

        JButton addExerciseButton = new JButton("Add Exercise");
        JButton removeExerciseButton = new JButton("Remove Exercise");
        JButton setGoalButton = new JButton("Set Goal");
        JButton showGoalButton = new JButton("Show Goal");
        JButton createChartButton = new JButton("Diagram");
        JPanel exerciseButtonsPanel = new JPanel(new GridLayout(1, 5));

        exerciseButtonsPanel.add(addExerciseButton);
        exerciseButtonsPanel.add(removeExerciseButton);
        exerciseButtonsPanel.add(setGoalButton);
        exerciseButtonsPanel.add(showGoalButton);
        exerciseButtonsPanel.add(createChartButton);

        exercisePanel.add(exerciseButtonsPanel, BorderLayout.SOUTH);

        exerciseList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    Exercise selectedExercise = exerciseList.getSelectedValue();
                    if (selectedExercise != null) {
                        JTextField repsField = new JTextField(String.valueOf(selectedExercise.getRepetitions()));
                        JTextField setsField = new JTextField(String.valueOf(selectedExercise.getSets()));
                        JTextField weightField = new JTextField(String.valueOf(selectedExercise.getWeight()));

                        JPanel panel = new JPanel(new GridLayout(3, 2));
                        panel.add(new JLabel("Repetitions:"));
                        panel.add(repsField);
                        panel.add(new JLabel("Sets:"));
                        panel.add(setsField);
                        panel.add(new JLabel("Weight:"));
                        panel.add(weightField);

                        int result = JOptionPane.showConfirmDialog(MainView.this, panel, "Edit Exercise", JOptionPane.OK_CANCEL_OPTION);
                        if (result == JOptionPane.OK_OPTION) {
                            try {
                                int reps = Integer.parseInt(repsField.getText());
                                int sets = Integer.parseInt(setsField.getText());
                                int weight = Integer.parseInt(weightField.getText());
                                selectedExercise.setDetails(reps, weight, sets);
                                exerciseList.repaint();
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(MainView.this, "Invalid input. Please use numbers.");
                            }
                        }
                    }
                }
            }
        });

        workoutList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    Workout selectedWorkout = workoutList.getSelectedValue();
                    if (selectedWorkout != null) {
                        String newWorkoutName = JOptionPane.showInputDialog(MainView.this, "Edit Workout Name:", selectedWorkout.getName());
                        if (newWorkoutName != null && !newWorkoutName.trim().isEmpty()) {
                            selectedWorkout.setName(newWorkoutName);
                            workoutList.repaint();
                        }
                    }
                }
            }
        });

        addSessionButton.addActionListener(e -> {
            Workout selectedWorkout = workoutList.getSelectedValue();
            if (selectedWorkout != null) {
                String dateText = JOptionPane.showInputDialog(this, "Enter Session Date (YYYY-MM-DD):");
                if (dateText != null && !dateText.trim().isEmpty()) {
                    try {
                        if (controller.addSession(selectedWorkout, dateText)) {
                            JOptionPane.showMessageDialog(this, "Session added successfully!");
                        }
                    } catch (DateTimeParseException ex) {
                        JOptionPane.showMessageDialog(this, "Invalid date format. Use YYYY-MM-DD.");
                    }
                }
            }
        });


        // Przycisk usunięcia Session
        deleteSessionButton.addActionListener(e -> {
            Workout selectedWorkout = workoutList.getSelectedValue();
            TrainingSession selectedSession = sessionList.getSelectedValue();
            if (selectedWorkout != null && selectedSession != null) {
                controller.removeSession(selectedWorkout, selectedSession);
            }
        });

        sessionList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                TrainingSession selectedSession = sessionList.getSelectedValue();
                if (selectedSession != null) {
                    exerciseListModel.clear();
                    for (Exercise exercise : selectedSession.getExercises()) {
                        exerciseListModel.addElement(exercise);
                    }
                }
            }
        });

        addWorkoutButton.addActionListener(e -> {
            String workoutName = JOptionPane.showInputDialog(this, "Enter Workout Name:");
            if (workoutName != null && !workoutName.trim().isEmpty()) {
                controller.addWorkout(workoutName);
            }
        });

        // Przycisk usunięcia Workoutu
        removeWorkoutButton.addActionListener(e -> {
            Workout selectedWorkout = workoutList.getSelectedValue();
            if (selectedWorkout != null) {
                controller.removeWorkout(selectedWorkout);
            }
        });

        workoutList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Workout selectedWorkout = workoutList.getSelectedValue();
                if (selectedWorkout != null) {
                    sessionListModel.clear();
                    for (TrainingSession session : selectedWorkout.getTrainingSessions()) {
                        sessionListModel.addElement(session);
                    }
                    exerciseListModel.clear();
                }
            }
        });

        addExerciseButton.addActionListener(e -> {
            TrainingSession selectedSession = sessionList.getSelectedValue();
            if (selectedSession != null) {
                JTextField nameField = new JTextField();
                JTextField repsField = new JTextField();
                JTextField setsField = new JTextField();
                JTextField weightField = new JTextField();

                JPanel panel = new JPanel(new GridLayout(4, 2));
                panel.add(new JLabel("Name:"));
                panel.add(nameField);
                panel.add(new JLabel("Repetitions:"));
                panel.add(repsField);
                panel.add(new JLabel("Sets:"));
                panel.add(setsField);
                panel.add(new JLabel("Weight:"));
                panel.add(weightField);

                int result = JOptionPane.showConfirmDialog(this, panel, "Enter Exercise Details", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        String name = nameField.getText().trim();
                        int reps = Integer.parseInt(repsField.getText());
                        int sets = Integer.parseInt(setsField.getText());
                        int weight = Integer.parseInt(weightField.getText());

                        if (controller.addExercise(selectedSession, name, reps, sets, weight)) {
                            JOptionPane.showMessageDialog(this, "Exercise added successfully!");
                        } else {
                            JOptionPane.showMessageDialog(this, "Failed to add exercise. Check inputs.");
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Invalid input. Use numbers for reps, sets, and weight.");
                    }
                }
            }
        });

        removeExerciseButton.addActionListener(e -> {
            TrainingSession selectedSession = sessionList.getSelectedValue();
            Exercise selectedExercise = exerciseList.getSelectedValue();
            if (selectedSession != null && selectedExercise != null) {
                controller.removeExercise(selectedSession, selectedExercise);
            }
        });

        setGoalButton.addActionListener(e -> {
            Exercise selectedExercise = exerciseList.getSelectedValue();
            if (selectedExercise != null) {
                JTextField goalField = new JTextField();
                JTextField deadlineField = new JTextField("YYYY-MM-DD");

                JPanel panel = new JPanel(new GridLayout(4, 4));
                panel.add(new JLabel("Goal:"));
                panel.add(goalField);
                panel.add(new JLabel("Deadline:"));
                panel.add(deadlineField);

                int result = JOptionPane.showConfirmDialog(this, panel, "Set Goal", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                   controller.setGoalForExercise(selectedExercise, goalField.getText(), deadlineField.getText());
                }
            }
            controller.saveData();
        });

        showGoalButton.addActionListener(e -> {
            Exercise selectedExercise = exerciseList.getSelectedValue();
            if (selectedExercise != null) {
                JOptionPane.showMessageDialog(
                        frame,
                        "Exercise: " + selectedExercise.getName() + "\n" +
                                "Goal: " + selectedExercise.getGoalDescription() +"\n"+
                                "Deadline:"+ selectedExercise.getGoalDeadline(),
                        "Exercise Goal",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(
                        frame,
                        "No exercise selected. Please select an exercise first.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        createChartButton.addActionListener(e -> {
            Exercise selectedExercise = exerciseList.getSelectedValue();

            if (selectedExercise != null) {
                // Create a JFrame to display the chart
                JFrame chartFrame = new JFrame("Exercise Progress Chart");
                chartFrame.setSize(800, 600);
                chartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                // Create and set up the chart panel
                DiagramView chartPanel = new DiagramView(controller.getWorkouts(), selectedExercise.getName());
                chartFrame.add(chartPanel);

                // Show the chart window
                chartFrame.setVisible(true);
            } else {
                // Show error message if no exercise is selected
                JOptionPane.showMessageDialog(this, "No exercise selected. Please select an exercise first.");
            }
        });


        controlPanel.add(workoutPanel);
        controlPanel.add(sessionPanel);
        controlPanel.add(exercisePanel);
        add(controlPanel, BorderLayout.CENTER);

        setVisible(true);
    }

}
