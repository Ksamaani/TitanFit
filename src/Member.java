public class Member extends Person{

    private int membershipDuration;
    private double baseFee;

    public Member (int id,String name,int age,int duration){
        super(id,name,age);
        this.membershipDuration = duration;
    }

    @Override
    public double calculateFee(){
        return membershipDuration*baseFee;
    }

    public double makePayment(){
        return 0.0;
    }

    @Override
    public String toString(){
        return "Name: "+this.getName()+"id: "+this.getId()+"age: "+ this.getAge()+"Membership Duration: "+this.membershipDuration+"months ";
    }
}
//• Person
//• Member
//• PremiumMember
//• Payable interface