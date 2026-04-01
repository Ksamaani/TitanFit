// A gym trainer class
public class Trainer extends Person implements Payable {
    private String specialization;
    private double monthelySalary;

    public Trainer(int id, String name, int age, String specialization, double monthelySalary) {
        super(id, name, age);
        this.specialization = specialization;
        this.monthelySalary = monthelySalary;
    }

    @Override
    public double calculateFee() {
        return monthelySalary;
    }

    @Override
    public double makePayment() {
        return calculateFee();
    }

    public String toString() {
        return super.toString() + "Specialization in: " + specialization;
    }
}
