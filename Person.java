// Abstract class for all the people in the gym.
// Blueprint for Member and Trainer.

import java.io.Serializable;

public abstract class Person implements Serializable {

    private int id;
    private String name;
    private int age;

    // Constructor for initializing The values.
    public Person(int id, String name, int age) {
        this.setName(name);
        this.id = id;
        this.setAge(age);
    }

    // Abstract method making subclasses calculate their fee.
    public abstract double calculateFee();

    public String toString() {
        return "Name: " + name + ", Id: " + id + ", Age: " + age + " ,";
    }

    // Getters and Setters.
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
        if (age <= 0 || age >= 120)
            throw new InvalidAgeException("Invalid age: " + age);
        this.age = age;
    }
}
