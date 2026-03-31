public class WorkoutPlan {
    private final String planName;
    private final int durationWeeks;
    private final Exercise[] exercises;
    private int exerciseCount = 0;
    private int maxExercise; // not from UML. needed to create an array of "Exercise" of this size (see line 12)

    public WorkoutPlan(String name, int durationWeeks, int maxExercise) {
        this.planName = name;
        this.durationWeeks = durationWeeks;
        exercises = new Exercise[maxExercise];
    }

    public boolean addExercise(Exercise e) {
        if (exerciseCount == exercises.length) {
            return false;
        }
        exercises[exerciseCount] = e;
        exerciseCount++;
        return true;
    }

    public boolean removeExercise(String name) {
        for (int i = 0; i < exerciseCount; i++) {
            if (exercises[i].getName().equals(name)) {
                for (int j = i; j < exerciseCount - 1; j++) {
                    exercises[j] = exercises[j + 1];
                }
                exerciseCount--;
                exercises[exerciseCount] = null;
                return true;
            }
        }
        return false;
    }

    public Exercise searchExercise(String name) {
        for (int i = 0; i < exerciseCount; i++) {
            if (exercises[i].getName().equals(name)) {
                return exercises[i];
            }
        }
        return null;
    }

    public Exercise searchExerciseRecursive(String name, int index) {
        if (index == exerciseCount) {
            return null;
        } else if (exercises[index].getName().equals(name)) {
            return exercises[index];
        }

        return searchExerciseRecursive(name, index + 1);
    }

    public String toString() {
        String temp = "";

        for (int i = 0; i < exerciseCount; i++) {
            temp += "\n" + (i + 1) + ") " + exercises[i];
        }

        return "Plan name: " + planName + ", duration in weeks: " + durationWeeks + ", exercise count: " + exerciseCount + "\nExercises Info: " + temp;
    }
}