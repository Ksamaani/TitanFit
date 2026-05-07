// The class that manages all members and trainers
public class Gym {
    private String gymName;
    private List<Member> membersList;
    private List<Trainer> trainersList;
    private int maxMembers;
    private int maxTrainers;
    private int currentTrainerCount;
    private int currentMembersCount;

    
    public Gym(String name, int maxTrainers, int maxMembers) {

        this.gymName = name;
        this.maxMembers = maxMembers;
        this.maxTrainers = maxTrainers;
        this.currentMembersCount = 0;
        this.currentTrainerCount = 0;

        this.membersList = new List<Member>("Members");
        this.trainersList = new List<Trainer>("Trainers");

    }

    public boolean addMember(Member m) {

        if (currentMembersCount >= maxMembers) {
            return false;
        }
        membersList.insertAtBack(m);
        currentMembersCount++; // Increase the count
        return true;

    }

    //Removes a member using pointer manipulation.
    public boolean removeMember(int id) {
        Node<Member> current = membersList.getFirstNode();
        Node<Member> previous = null;

        while (current != null) {
            if (current.data.getId() == id) {
                if (previous == null) {
                    membersList.removeFromFront();
                } else if (current.nextNode == null) {
                    membersList.removeFromBack();
                } else {
                    previous.nextNode = current.nextNode;
                }
                currentMembersCount--;
                return true;
            }
            previous = current;
            current = current.nextNode;
        }
        return false;
    }

    
    // Recursive search in members List by ID
    public Member searchMemberRecursive(int id) {
        return searchMemberHelper(membersList.getFirstNode(), id);
    }

    //Private recursive helper method.
    private Member searchMemberHelper(Node<Member> current, int id) {
        if (current == null) {
            return null;
        }
        if (current.data.getId() == id) {
            return current.data;
        }
        return searchMemberHelper(current.nextNode, id);
    }

    public boolean addTrainer(Trainer t) {
        if (currentTrainerCount >= maxTrainers) {
            return false;
        }
        trainersList.insertAtBack(t);
        currentTrainerCount++;
        return true;
    }

    public boolean removeTrainer(int id) {

        Node<Trainer> current = trainersList.getFirstNode();
        Node<Trainer> previous = null;

        while (current != null) {
            if (current.data.getId() == id) {
                if (previous == null) {
                    trainersList.removeFromFront();
                } else if (current.nextNode == null) {
                    trainersList.removeFromBack();
                } else {
                    previous.nextNode = current.nextNode;
                }
                currentTrainerCount--;
                return true;
            }
            previous = current;
            current = current.nextNode;
        }
        return false;
    }

    public Trainer searchTrainerRecursive(int id) {
        return searchTrainerHelper(trainersList.getFirstNode(), id);
    }
    private Trainer searchTrainerHelper(Node<Trainer> current, int id) {
        if (current == null) {
            return null;
        }
        if (current.data.getId() == id) {
            return current.data;
        }
        return searchTrainerHelper(current.nextNode, id);
    }

    public void displayMembers() {
        if (membersList.isEmpty()) {
            System.out.println("No members currently enrolled.");
        } else {
            membersList.print();
        }
    }

    public void displayTrainers() {

        if (trainersList.isEmpty()) {
            System.out.println("No trainers currently employed.");
        } else {
            trainersList.print();
        }

    }

    public int getCurrentTrainerCount() {
        return currentTrainerCount;
    }

    public int getCurrentMembersCount() {
        return currentMembersCount;
    }
}
