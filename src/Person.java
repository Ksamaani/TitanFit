public abstract class Person {

    private int id;
    private String name;
    private int age;

    public Person(int id,String name,int age){
        this.setName(name);
        this.id = id;
        this.setAge(age);
    }

    public abstract double calculateFee();

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
