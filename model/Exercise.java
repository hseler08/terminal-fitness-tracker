package model;

import java.io.Serializable;
import java.time.LocalDate;


    public class Exercise implements Serializable {
        private String name;
        private int repetitions;
        private int sets;
        private int weight;

        private String goalDescription;
        private LocalDate goalDeadline;

        public Exercise(String name, int repetitions, int sets, int weight ) {
            this.name = name;
            this.repetitions = repetitions;
            this.sets = sets;
            this.weight = weight;
        }

        public String getName() { return name; }
        public int getRepetitions() { return repetitions; }
        public int getSets() { return sets; }
        public int getWeight() { return weight; }

        public String getGoalDescription() { return goalDescription; }
        public LocalDate getGoalDeadline() { return goalDeadline; }

        public void setGoal(String goalDescription, LocalDate goalDeadline) {
            this.goalDescription = goalDescription;
            this.goalDeadline = goalDeadline;
        }

        public void setDetails(int repetitions, int sets, int weight) {
            this.repetitions = repetitions;
            this.sets = sets;
            this.weight = weight;
        }

        public String toString() {
            return name + " - " + repetitions + " reps, " + sets + " sets, " + weight + " kg";
        }
    }

