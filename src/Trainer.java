public class Trainer extends Person implements Payable {
private String specialization;
private double monthelySalary;

Trainer(int id,String name,int age,String spec,double month){
    super(id, name, age);
    this.setSpecialization(spec);
    this.setmonthelySalary(month);
}
    @Override
public double calculateFee(){
    return monthelySalary;
}

    @Override
    public double makePayment() {
        return calculateFee();
    }

    public String toString(){
        return super.toString()+"Specialization in: "+getSpecialization();
    }





 public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String spec) {
        this.specialization = spec;
    }
    
    public double getmonthelySalary() {
        return monthelySalary;
    }

    public void setmonthelySalary(double month) {
        this.monthelySalary = monthelySalary;
    }

}
