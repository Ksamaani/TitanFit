import java.io.Serializable;

// A standard gym member
public class Member extends Person implements Payable, Serializable {

    // unique attributes to a standard member.
    private int membershipDuration;
    private double baseFee;

    // Constructor for initializing The values.
    public Member(int id, String name, int age, int duration) {
        super(id, name, age);
        if (duration <= 0)
            throw new IllegalArgumentException("Invalid duration: " + duration + ". Duration must be greater than 0");
        this.membershipDuration = duration;
        this.baseFee = 250;
    }

    @Override
    public double calculateFee() {
        return membershipDuration * baseFee;
    }


    @Override
    public double makePayment() {
        return calculateFee();
    }

    @Override
    public String toString() {
        return super.toString() + "Membership Duration: " + this.membershipDuration + " months";
    }
}
