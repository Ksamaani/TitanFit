public class PremiumMember extends Member {
    private boolean personalTrainer;
    private boolean spaAccess;
    private WorkoutPlan customPlan;

    public PremiumMember(int id, String name, int age, int duration, boolean personalTrainer, boolean spaAccess) {
        super(id, name, age, duration);
        this.personalTrainer = personalTrainer;
        this.spaAccess = spaAccess;
    }

    @Override
    public double calculateFee() {
        double fee = super.calculateFee();
        if (personalTrainer) {
            fee += 1000;
        }
        if (spaAccess) {
            fee += 500;
        }
        return fee;
    }

    public void setWorkoutPlan(WorkoutPlan plan) {
        this.customPlan = plan;
    }

    public WorkoutPlan getWorkoutPlan() {
        return customPlan;
    }


    @Override
    public String toString() {
        return super.toString() + "This is a Premium Member, Has A Personal Trainer: " + this.personalTrainer + ", Has A Spa Access: " + this.spaAccess;
    }

}
