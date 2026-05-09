import java.io.Serializable;

// A collection of exercises
public class WorkoutPlan implements Serializable {
    private String planName;
    private int durationWeeks;
    private int maxExercises;
    private int currentExerciseCount;
    private List<Exercise> exercisesList;

    //constructor
    public WorkoutPlan(String name, int durationWeeks, int maxExercise) {
        this.planName = name;
        this.durationWeeks = durationWeeks;
        this.currentExerciseCount =0;
        this.exercisesList = new List<Exercise>("Exercises");
        this.maxExercises=maxExercise;
    }

    public boolean addExercise(Exercise e) {
        if (currentExerciseCount >= maxExercises) {
            return false;
        }
        exercisesList.insertAtBack(e);
        currentExerciseCount++;
        return true;
    }

    public boolean removeExercise(String name) {
        Node<Exercise> current = exercisesList.getFirstNode();
        Node<Exercise> previous = null;

        while (current != null) {

            if (current.data.getName().equals(name)) {
                if (previous == null) {
                    exercisesList.removeFromFront();
                } else if (current.nextNode == null) {
                    exercisesList.removeFromBack();
                } else {
                    previous.nextNode = current.nextNode;
                }
                currentExerciseCount--;
                return true;
            }
            previous = current;
            current = current.nextNode;
        }
        return false;
    }

    public Exercise searchExercise(String name) {
        Node<Exercise> current = exercisesList.getFirstNode();
        while (current != null) {
            if (current.data.getName().equals(name)) {
                return current.data;
            }
            current = current.nextNode;
        }
        return null;
    }

    // Recursive search in List array by name
    public Exercise searchExerciseRecursive(String name, int index) {
        return searchExerciseHelper(exercisesList.getFirstNode(), name);
    }
    private Exercise searchExerciseHelper(Node<Exercise> current, String name) {
        if (current == null) {
            return null;
        }
        if (current.data.getName().equals(name)) {
            return current.data;
        }
        return searchExerciseHelper(current.nextNode, name);
    }

    public String toString() {
        String temp = "";
        Node<Exercise> current = exercisesList.getFirstNode();
        int index = 1;

        while (current != null) {
            temp += "\n" + index + ") " + current.data.toString();
            current = current.nextNode;
            index++;
        }

        return "Plan name: " + planName + ", duration in weeks: " + durationWeeks +
                ", exercise count: " + currentExerciseCount + "\nExercises Info: " + temp;
    }

    public String getPlanName() { return planName; }
}