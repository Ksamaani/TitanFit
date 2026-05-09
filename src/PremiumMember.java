// A premium member with a personal trainer and spa access option

import java.io.Serializable;

public class PremiumMember extends Member implements Serializable {

    private boolean personalTrainer;
    private boolean spaAccess;
    private WorkoutPlan customPlan;

    // Constructor for initializing The values.
    public PremiumMember(int id, String name, int age, int duration, boolean personalTrainer, boolean spaAccess) {
        super(id, name, age, duration);
        this.personalTrainer = personalTrainer;
        this.spaAccess = spaAccess;
    }

    // Overrides the standard Member calculateFee method.
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

    // Links a newly created WorkoutPlan to this specific user.
    public void setWorkoutPlan(WorkoutPlan plan) {
        this.customPlan = plan;
    }

    // Retrieves the user's specific plan.
    public WorkoutPlan getWorkoutPlan() {
        return customPlan;
    }


    @Override
    public String toString() {
        return super.toString() + "This is a Premium Member, Has A Personal Trainer: " + this.personalTrainer + ", Has A Spa Access: " + this.spaAccess;
    }

}
