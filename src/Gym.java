// The class that manages all members and trainers
public class Gym {
    private String gymName;
    private Member[] members;
    private Trainer[] trainers;
    private int TrainerCount = 0;
    private int membersCount = 0;

    
    public Gym(String name, int maxTrainers, int maxMembers) {
        this.gymName = name;
        members = new Member[maxMembers];
        trainers = new Trainer[maxTrainers];

    }

    public boolean addMember(Member m) {

        if (membersCount == members.length)
            return false;
        else {
            members[membersCount] = m;
            membersCount++;
            return true;
        }

    }

    public boolean removeMember(int id) {
        for (int i = 0; i < membersCount; i++) {
            if (members[i].getId() == id) {
                for (int j = i; j < membersCount - 1; j++) {
                    members[j] = members[j + 1];
                }
                membersCount--;
                members[membersCount] = null;
                return true;
            }
        }
        return false;
    }

    
    // Recursive search in members array by ID
    public Member searchMemberRecursive(int Id, int index) {
        if (index == membersCount) {
            return null;
        } else if (members[index].getId() == Id) {
            return members[index];
        }

        return searchMemberRecursive(Id, index + 1);
    }

    public boolean addTrainer(Trainer t) {
        if (TrainerCount == trainers.length)
            return false;
        else {
            trainers[TrainerCount] = t;
            TrainerCount++;
            return true;
        }
    }

    public boolean removeTrainer(int id) {
        for (int i = 0; i < TrainerCount; i++) {
            if (trainers[i].getId() == id) {
                for (int j = i; j < TrainerCount - 1; j++) {
                    trainers[j] = trainers[j + 1];
                }
                TrainerCount--;
                trainers[TrainerCount] = null;
                return true;
            }
        }
        return false;
    }

    public Trainer searchTrainerRecursive(int id, int index) {
        if (index == TrainerCount) {
            return null;
        } else if (trainers[index].getId() == id) {
            return trainers[index];
        }
        return searchTrainerRecursive(id, index + 1);
    }

    public void displayMembers() {
        if (membersCount == 0)
            System.out.println("No members currently enrolled.");
        else {
            for (int i = 0; i < membersCount; i++) {
                System.out.println(members[i].toString());
            }
        }
    }

    public void displayTrainers() {
        if (TrainerCount == 0)
            System.out.println("No trainers currently employed.");
        else {
            for (int i = 0; i < TrainerCount; i++) {
                System.out.println(trainers[i].toString());
            }
        }

    }

    public int getTrainerCount() {
        return TrainerCount;
    }

    public int getMembersCount() {
        return membersCount;
    }
}
