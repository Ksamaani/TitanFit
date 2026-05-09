import java.io.*;

public class GymFileManager {

    public static final String FILE_NAME = "gym_data.dat";

    // saving the gym
    public static void saveGym(Gym gym){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
            oos.writeObject(gym);
            oos.close();
            System.out.println("Gym data saved successfully");
        }
        catch (IOException e){
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    // reading the gym object
    public static Gym loadGym(){
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME));
            Gym gym = (Gym) ois.readObject();
            ois.close();
            System.out.println(gym.getGymName() + " data loaded successfully!");
            return gym;
        }
        catch (IOException e){
            System.out.println("Error: " + e.getMessage());
        }
        catch (ClassNotFoundException e){
            System.out.println("Error: saved data is corrupted");
        }
        return null;
    }
}
