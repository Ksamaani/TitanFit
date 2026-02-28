public class Member extends Person implements Payable{

    private int membershipDuration;
    private double baseFee;

    public Member (int id,String name,int age,int duration){
        super(id,name,age);
        this.membershipDuration = duration;
        this.baseFee = 250;
    }

    @Override
    public double calculateFee(){
        return membershipDuration*baseFee;
    }


    @Override
    public double makePayment(){
        return calculateFee();
    }

    @Override
    public String toString(){
        return "Name: "+this.getName()+"id: "+this.getId()+"age: "+ this.getAge()+"Membership Duration: "+this.membershipDuration+"months ";
    }
}
