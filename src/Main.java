import java.util.Scanner;
public class Main { 
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("==================================================");
        System.out.println("          Welcome to TitanFit Management          ");
        System.out.println("==================================================");
        
        System.out.print("Enter Gym Name: ");
        String gymName = scanner.nextLine();
        System.out.print("Enter Maximum Number of Trainers: ");
        int maxTrainers = scanner.nextInt();
        System.out.print("Enter Maximum Number of Members: ");
        int maxMembers = scanner.nextInt();
        
        Gym titanFit = new Gym(gymName, maxTrainers, maxMembers); 

        int choice;

        do {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Add a Member (Standard / Premium)");
            System.out.println("2. Add a Trainer");
            System.out.println("3. Remove a Member / Trainer");
            System.out.println("4. Search for a Member ");
            System.out.println("5. Search for a Trainer ");
            System.out.println("6. Display All Members & Trainers");
            System.out.println("7. Workout Plan Management (Premium Members Only)");
            System.out.println("8. Process Payments");
            System.out.println("0. Exit");
            System.out.print("Please enter your choice: ");
            
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Member ID: ");
                    int mId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Name: ");
                    String mName = scanner.nextLine();
                    System.out.print("Enter Age: ");
                    int mAge = scanner.nextInt();
                    System.out.print("Enter Duration (months): ");
                    int mDuration = scanner.nextInt();
                    
                    System.out.print("Is this a Premium Member? (1 for Yes, 0 for No): ");
                    int isPremium = scanner.nextInt();
                    scanner.nextLine();
                    
                    boolean mAdded;
                    if (isPremium == 1) {
                        System.out.print("Include Personal Trainer? (1 for Yes, 0 for No): ");
                        boolean hasTrainer = (scanner.nextInt() == 1);
                        System.out.print("Include Spa Access? (1 for Yes, 0 for No): ");
                        boolean hasSpa = (scanner.nextInt() == 1);
                        scanner.nextLine(); 
                        PremiumMember newPremium = new PremiumMember(mId, mName, mAge, mDuration, hasTrainer, hasSpa);
                        
                        System.out.println("\n--- Create Premium Workout Plan ---");
                        System.out.print("Enter Plan Name: ");
                        String pName = scanner.nextLine();
                        System.out.print("Enter Plan Duration (weeks): ");
                        int pDuration = scanner.nextInt();
                        System.out.print("Enter Maximum Exercises for this plan: ");
                        int pMaxEx = scanner.nextInt();
                        
                        WorkoutPlan personalPlan = new WorkoutPlan(pName, pDuration, pMaxEx);
                        newPremium.setWorkoutPlan(personalPlan);
                        
                        mAdded = titanFit.addMember(newPremium);
                    } else {
                        Member newMember = new Member(mId, mName, mAge, mDuration);
                        mAdded = titanFit.addMember(newMember);
                    }
                    
                    if(mAdded) 
                        System.out.println("Member added successfully!");
                    else 
                        System.out.println("Failed to add member.");
                    break;

                case 2:
                    System.out.print("Enter Trainer ID: ");
                    int tId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Name: ");
                    String tName = scanner.nextLine();
                    System.out.print("Enter Age: ");
                    int tAge = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Specialization: ");
                    String tSpec = scanner.nextLine();
                    System.out.print("Enter Monthly Salary: ");
                    double tSalary = scanner.nextDouble();
                    
                    Trainer newTrainer = new Trainer(tId, tName, tAge, tSpec, tSalary);
                    if (titanFit.addTrainer(newTrainer)) {
                        System.out.println("Trainer added successfully!");
                    } else {
                        System.out.println("Failed to add trainer.");
                    }
                    break;

                case 3:
                    System.out.print("Remove (1) Member or (2) Trainer? ");
                    int rmChoice = scanner.nextInt();
                    System.out.print("Enter ID to remove: ");
                    int rmId = scanner.nextInt();
                    
                    if (rmChoice == 1) {
                        if (titanFit.removeMember(rmId))
                         System.out.println("Member removed.");
                        else 
                         System.out.println("Member not found.");
                    } else if (rmChoice == 2) {
                        if (titanFit.removeTrainer(rmId))
                         System.out.println("Trainer removed.");
                        else
                         System.out.println("Trainer not found.");
                    }
                    break;

                case 4:
                    System.out.print("Enter Member ID to search: ");
                    int searchMId = scanner.nextInt();

                    Member foundM = titanFit.searchMemberRecursive(searchMId, 0);

                    if (foundM != null) {
                        System.out.println(foundM.toString());
                    } else {
                            System.out.println("Member not found.");
                            }
                    break;
                case 5:
                    System.out.print("Enter Trainer ID to search: ");
                    int searchTId = scanner.nextInt();
                    
                    Trainer foundT = titanFit.searchTrainerRecursivea(searchTId, 0);
                    if (foundT != null) {
                        System.out.println(foundT.toString());
                    } else {
                        System.out.println("Trainer not found.");
                    }
                    break;

                case 6:
                    titanFit.displayMembers();
                    titanFit.displayTrainers();
                    break;

                case 7:
                    System.out.print("Enter your Member ID to access Workout Plans: ");
                    int accessId = scanner.nextInt();
                    scanner.nextLine();
                    
                    Member planMember = titanFit.searchMemberRecursive(accessId, 0);
                    
                    if (planMember != null && planMember instanceof PremiumMember) {
                        PremiumMember pm = (PremiumMember) planMember;
                        WorkoutPlan currentPlan = pm.getWorkoutPlan();
                        
                        if (currentPlan != null) {
                            System.out.println("\n--- Managing Plan: " + currentPlan.toString() + " ---");
                            System.out.println("1. Add Exercise | 2. Remove Exercise | 3. Search Exercise");
                            System.out.print("Choice: ");
                            int exChoice = scanner.nextInt();
                            scanner.nextLine();
                            
                            if (exChoice == 1) {
                                System.out.print("Exercise Name: ");
                                String exName = scanner.nextLine();
                                System.out.print("Sets: ");
                                int sets = scanner.nextInt();
                                System.out.print("Repetitions: ");
                                int reps = scanner.nextInt();
                                if (currentPlan.addExercise(new Exercise(exName, sets, reps))) {
                                    System.out.println("Exercise added to your personal plan!");
                                } else {
                                    System.out.println("Cannot add. Plan is full.");
                                }
                            } else if (exChoice == 2) {
                                System.out.print("Enter Exercise Name to remove: ");
                                String rmEx = scanner.nextLine();
                                if (currentPlan.removeExercise(rmEx)) 
                                    System.out.println("Exercise removed.");
                                else 
                                    System.out.println("Not found.");
                            } else if (exChoice == 3) {
                                System.out.print("Enter Exercise Name to search: ");
                                String searchEx = scanner.nextLine();
                                Exercise foundEx = currentPlan.searchExerciseRecursive(searchEx, 0);
                                System.out.println(foundEx != null ? foundEx.toString() : "Not found.");
                            }
                        } else {
                            System.out.println("No workout plan found for this member.");
                        }
                    } else if (planMember != null) {
                        System.out.println("Access Denied. Workout Plans are available for Premium Members only.");
                    } else {
                        System.out.println("Member not found.");
                    }
                    break;

                case 8:
                    System.out.print("Enter ID of Person to process payment: ");
                    int payId = scanner.nextInt();
                    
                    Person p = titanFit.searchMemberRecursive(payId, 0);
                    if (p == null) {
                        p = titanFit.searchTrainerRecursivea(payId, 0);
                    }
                    
                    if (p != null) {
                        if (p instanceof Payable) {
                            double amount = ((Payable) p).makePayment();
                            System.out.println("Processed payment for " + p.getName() + ": " + amount + " SAR.");
                        }
                    } else {
                        System.out.println("ID not found.");
                    }
                    break;

                case 0:
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 0);
        
        scanner.close();
    }
}
