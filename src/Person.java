// Abstract class for all the people in the gym

public abstract class Person {

    private int id;
    private String name;
    private int age;

    public Person(int id, String name, int age) {
        this.setName(name);
        this.id = id;
        this.setAge(age);
    }

    // Abstract method making subclasses calculate their fee
    public abstract double calculateFee();

    public String toString() {
        return "Name: " + name + ", Id: " + id + ", Age: " + age + " ,";
    }

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
