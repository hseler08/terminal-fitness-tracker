package View;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import model.*;

public class DiagramView extends JPanel {

    private final List<Workout> workouts;
    private final String selectedExerciseName;

    public DiagramView(List<Workout> workouts, String selectedExerciseName) {
        this.workouts = workouts;
        this.selectedExerciseName = selectedExerciseName;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Set chart margins
        int margin = 50;
        int chartWidth = getWidth() - 2 * margin;
        int chartHeight = getHeight() - 2 * margin;


        // Draw axes
        g2d.drawLine(margin, margin, margin, getHeight() - margin); // Y-axis
        g2d.drawLine(margin, getHeight() - margin, getWidth() - margin, getHeight() - margin); // X-axis

        // Gather data for the selected exercise
        List<String> dates = new ArrayList<>();
        List<Integer> weights = new ArrayList<>();

        for (Workout workout : workouts) {
            for (TrainingSession session : workout.getTrainingSessions()) {
                for (Exercise exercise : session.getExercises()) {
                    if (exercise.getName().equals(selectedExerciseName)) {
                        dates.add(session.getDate().toString());
                        weights.add(exercise.getWeight());
                    }
                }
            }
        }

        if (dates.isEmpty()) {
            g2d.drawString("No data available for the selected exercise.", margin, getHeight() / 2);
            return;
        }

        // Normalize data for drawing
        int maxWeight = weights.stream().max(Integer::compareTo).orElse(1);
        int pointCount = weights.size();
        int xStep = chartWidth / Math.max(pointCount - 1, 1);
        double yScale = (double) chartHeight / maxWeight;

        // Draw data points and lines
        for (int i = 0; i < weights.size(); i++) {
            int x = margin + i * xStep;
            int y = getHeight() - margin - (int) (weights.get(i) * yScale);

            // Draw point
            g2d.fillOval(x - 3, y - 3, 6, 6);

            // Draw label
            g2d.drawString(dates.get(i), x - 15, getHeight() - margin + 15);

            // Connect points with a line
            if (i > 0) {
                int prevX = margin + (i - 1) * xStep;
                int prevY = getHeight() - margin - (int) (weights.get(i - 1) * yScale);
                g2d.drawLine(prevX, prevY, x, y);
            }
        }

        // Draw Y-axis labels
        for (int i = 0; i <= 5; i++) {
            int yLabel = i * maxWeight / 5;
            int y = getHeight() - margin - (int) (yLabel * yScale);
            g2d.drawString(String.valueOf(yLabel), margin - 30, y + 5);
            g2d.drawLine(margin - 5, y, margin + 5, y);
        }
    }

    public static void showChart(List<Workout> workouts, String exerciseName) {
        JFrame frame = new JFrame("Weight Progression for " + exerciseName);
        DiagramView chartPanel = new DiagramView(workouts, exerciseName);
        frame.add(chartPanel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
