public class Exercise {
    private String name;
    private int sets;
    private int repetitions;

    public Exercise(String name, int sets, int repititions) {
        this.name = name;
        this.sets = sets;
        this.repetitions = repititions;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return "Exercise name: " + getName() + ", Sets: " + sets + ", Repetitions: " + repetitions;
    }
}
