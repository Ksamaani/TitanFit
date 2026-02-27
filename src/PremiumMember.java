public class PremiumMember extends Member{
    private boolean personalTrainer;
    private boolean spaAccess;

    public PremiumMember (int id,String name,int age,int duration){
        super(id, name, age, duration);
    }

    @Override
    public double calculateFee(){
        double fee = super.calculateFee();
        if (personalTrainer){
            fee += 1000;
        }
        if (spaAccess){
            fee += 500;
        }
        return fee;
    }
    @Override
    public String toString(){
        return super.toString()+"This is a Premium Member, Has A Personal Trainer: "+ this.personalTrainer+", Has A Spa Access: "+this.spaAccess;
    }

}
