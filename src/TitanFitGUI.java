import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TitanFitGUI extends JFrame {

    private Gym  gym;
    private int  maxMembers, maxTrainers;

    private DefaultListModel<NodeWrapper> listModel;
    private JList<NodeWrapper>            itemList;
    private JLabel                        statusLabel;
    private ResultFrame                   resultFrame;

    // ── Node wrapper: clean label separate from domain object ──────────────
    static class NodeWrapper {
        final String label;
        final Object data;
        NodeWrapper(String label, Object data) { this.label = label; this.data = data; }
        @Override public String toString() { return label; }
    }

    // ── Second JFrame: displays all results ────────────────────────────────
    static class ResultFrame extends JFrame {
        private final JTextArea textArea;

        ResultFrame() {
            super("TitanFit — Results");
            setSize(520, 460);
            setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            setLocationRelativeTo(null);

            textArea = new JTextArea();
            textArea.setEditable(false);
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
            textArea.setMargin(new Insets(12, 14, 12, 14));
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            add(new JScrollPane(textArea), BorderLayout.CENTER);

            JButton btnClose = new JButton("Close");
            btnClose.addActionListener(e -> setVisible(false));
            JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            bottom.add(btnClose);
            add(bottom, BorderLayout.SOUTH);
        }

        void display(String title, String content) {
            setTitle("TitanFit — " + title);
            textArea.setText(content);
            textArea.setCaretPosition(0);
            setVisible(true);
            toFront();
        }
    }

    // ── Entry point ────────────────────────────────────────────────────────
    public static void main(String[] args) {
        new TitanFitGUI();
    }

    // ── Constructor: ask load or create, then build window ─────────────────
    public TitanFitGUI() {
        super("TitanFit Management System");

        // --- CHANGE: ask user to load or create new gym ---
        int loadChoice = JOptionPane.showConfirmDialog(null,
                "Load previous gym data?", "TitanFit — Startup",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (loadChoice == JOptionPane.YES_OPTION) {
            Gym loaded = GymFileManager.loadGym();
            if (loaded != null) {
                gym         = loaded;
                maxMembers  = gym.getMaxMembers();
                maxTrainers = gym.getMaxTrainers();
                buildUI(gym.getGymName());
                setVisible(true);
                return;
            } else {
                JOptionPane.showMessageDialog(null,
                        "No saved data found. Please create a new gym.",
                        "TitanFit", JOptionPane.WARNING_MESSAGE);
            }
        }

        // Create new gym
        JTextField tfName   = new JTextField("TitanFit Gym", 15);
        JSpinner spTrainers = new JSpinner(new SpinnerNumberModel(5,  1, 100,  1));
        JSpinner spMembers  = new JSpinner(new SpinnerNumberModel(50, 1, 1000, 1));

        JPanel setup = new JPanel(new GridLayout(3, 2, 8, 8));
        setup.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        setup.add(new JLabel("Gym Name:"));     setup.add(tfName);
        setup.add(new JLabel("Max Trainers:")); setup.add(spTrainers);
        setup.add(new JLabel("Max Members:"));  setup.add(spMembers);

        if (JOptionPane.showConfirmDialog(null, setup, "TitanFit — Gym Setup",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) != JOptionPane.OK_OPTION) {
            System.exit(0);
        }

        String gymName = tfName.getText().trim().isEmpty() ? "TitanFit Gym" : tfName.getText().trim();
        maxTrainers = (int) spTrainers.getValue();
        maxMembers  = (int) spMembers.getValue();
        gym = new Gym(gymName, maxTrainers, maxMembers);

        buildUI(gymName);
        setVisible(true);
    }

    // ── Build the main window ──────────────────────────────────────────────
    private void buildUI(String gymName) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 560);
        setLocationRelativeTo(null);

        resultFrame = new ResultFrame();

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(25, 25, 38));
        header.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
        JLabel title = new JLabel("TitanFit  —  " + gymName);
        title.setForeground(new Color(215, 215, 230));
        title.setFont(new Font("SansSerif", Font.BOLD, 17));
        header.add(title, BorderLayout.WEST);
        add(header, BorderLayout.NORTH);

        // Center: JList of members & trainers
        listModel = new DefaultListModel<>();
        itemList  = new JList<>(listModel);
        itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemList.setFont(new Font("SansSerif", Font.PLAIN, 13));
        itemList.addListSelectionListener(e -> { if (!e.getValueIsAdjusting()) onSelect(); });

        JScrollPane listScroll = new JScrollPane(itemList);
        listScroll.setBorder(BorderFactory.createTitledBorder("Members & Trainers"));
        add(listScroll, BorderLayout.CENTER);

        // Button grid (2 rows x 5 columns)
        JPanel btnPanel = new JPanel(new GridLayout(2, 5, 6, 6));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
        btnPanel.add(makeBtn("1. Add Member",     e -> doAddMember()));
        btnPanel.add(makeBtn("2. Add Trainer",    e -> doAddTrainer()));
        btnPanel.add(makeBtn("3. Remove",         e -> doRemove()));
        btnPanel.add(makeBtn("4. Search Member",  e -> doSearchMember()));
        btnPanel.add(makeBtn("5. Search Trainer", e -> doSearchTrainer()));
        btnPanel.add(makeBtn("6. Display All",    e -> doDisplayAll()));
        btnPanel.add(makeBtn("7. Workout Plan",   e -> doWorkoutPlan()));
        btnPanel.add(makeBtn("8. Payment",        e -> doPayment()));
        btnPanel.add(makeBtn("9. Save Data",      e -> doSave())); // --- CHANGE: Save button ---
        btnPanel.add(makeBtn("Exit",              e -> System.exit(0)));

        // Status bar
        statusLabel = new JLabel(" Ready");
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        statusLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(3, 8, 3, 8)));

        JPanel south = new JPanel(new BorderLayout());
        south.add(btnPanel,    BorderLayout.CENTER);
        south.add(statusLabel, BorderLayout.SOUTH);
        add(south, BorderLayout.SOUTH);

        // Rebuild list if gym was loaded from file
        rebuildList();
    }

    // ── Helpers ────────────────────────────────────────────────────────────

    private JButton makeBtn(String text, ActionListener al) {
        JButton b = new JButton(text);
        b.addActionListener(al);
        return b;
    }

    private void rebuildList() {
        listModel.clear();
        Node<Member> mn = gym.getMembersList().getFirstNode();
        while (mn != null) {
            Member m = mn.data;
            String prefix = (m instanceof PremiumMember) ? "[Premium]  " : "[Member]   ";
            listModel.addElement(new NodeWrapper(prefix + "[" + m.getId() + "] " + m.getName(), m));
            mn = mn.nextNode;
        }
        Node<Trainer> tn = gym.getTrainersList().getFirstNode();
        while (tn != null) {
            Trainer t = tn.data;
            listModel.addElement(new NodeWrapper("[Trainer]  [" + t.getId() + "] " + t.getName(), t));
            tn = tn.nextNode;
        }
    }

    private void onSelect() {
        NodeWrapper nw = itemList.getSelectedValue();
        if (nw == null) return;
        if (nw.data instanceof PremiumMember) {
            PremiumMember pm = (PremiumMember) nw.data;
            StringBuilder sb = new StringBuilder(pm.toString());
            sb.append("\n\nMonthly Fee: ").append(pm.calculateFee()).append(" SAR");
            WorkoutPlan plan = pm.getWorkoutPlan();
            if (plan != null) sb.append("\n\n").append(plan.toString());
            resultFrame.display("Member Details", sb.toString());
        } else if (nw.data instanceof Member) {
            Member m = (Member) nw.data;
            resultFrame.display("Member Details",
                    m.toString() + "\n\nMonthly Fee: " + m.calculateFee() + " SAR");
        } else if (nw.data instanceof Trainer) {
            Trainer t = (Trainer) nw.data;
            resultFrame.display("Trainer Details",
                    t.toString() + "\n\nMonthly Salary: " + t.calculateFee() + " SAR");
        }
    }

    private void setStatus(String msg) { statusLabel.setText(" " + msg); }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // ── Action: Add Member ─────────────────────────────────────────────────
    private void doAddMember() {
        if (gym.getCurrentMembersCount() >= maxMembers) {
            showError("Maximum member capacity reached (" + maxMembers + ")."); return;
        }

        JTextField tfId      = new JTextField(10);
        JTextField tfName    = new JTextField(15);
        JSpinner   spAge     = new JSpinner(new SpinnerNumberModel(25, 1, 120, 1));
        JSpinner   spDur     = new JSpinner(new SpinnerNumberModel(1,  1, 120, 1));
        JCheckBox  cbPremium = new JCheckBox();
        JCheckBox  cbTrainer = new JCheckBox(); cbTrainer.setEnabled(false);
        JCheckBox  cbSpa     = new JCheckBox(); cbSpa.setEnabled(false);
        JTextField tfPlan    = new JTextField("My Plan", 15); tfPlan.setEnabled(false);
        JSpinner   spWeeks   = new JSpinner(new SpinnerNumberModel(8,  1, 52, 1)); spWeeks.setEnabled(false);
        JSpinner   spMaxEx   = new JSpinner(new SpinnerNumberModel(10, 1, 50, 1)); spMaxEx.setEnabled(false);

        cbPremium.addActionListener(e -> {
            boolean p = cbPremium.isSelected();
            cbTrainer.setEnabled(p); cbSpa.setEnabled(p);
            tfPlan.setEnabled(p); spWeeks.setEnabled(p); spMaxEx.setEnabled(p);
        });

        JPanel form = new JPanel(new GridLayout(0, 2, 6, 6));
        form.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        form.add(new JLabel("Member ID:"));                form.add(tfId);
        form.add(new JLabel("Name:"));                     form.add(tfName);
        form.add(new JLabel("Age:"));                      form.add(spAge);
        form.add(new JLabel("Membership Duration (mo):")); form.add(spDur);
        form.add(new JLabel("Premium Member:"));           form.add(cbPremium);
        form.add(new JLabel("  + Personal Trainer:"));    form.add(cbTrainer);
        form.add(new JLabel("  + Spa Access:"));           form.add(cbSpa);
        form.add(new JSeparator());                         form.add(new JSeparator());
        form.add(new JLabel("Plan Name (Premium only):")); form.add(tfPlan);
        form.add(new JLabel("  Plan Duration (weeks):")); form.add(spWeeks);
        form.add(new JLabel("  Max Exercises:"));          form.add(spMaxEx);

        if (JOptionPane.showConfirmDialog(this, form, "Add Member",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) != JOptionPane.OK_OPTION) return;

        int id;
        try { id = Integer.parseInt(tfId.getText().trim()); }
        catch (NumberFormatException ex) { showError("Invalid ID — must be an integer."); return; }

        String name = tfName.getText().trim();
        if (name.isEmpty()) { showError("Name cannot be empty."); return; }
        if (gym.searchMemberRecursive(id) != null) { showError("ID " + id + " is already in use."); return; }

        // --- CHANGE: wrapped in try/catch for InvalidAgeException and IllegalArgumentException ---
        // InvalidAgeException is thrown in Person.setAge() and propagates here
        // IllegalArgumentException is thrown in Member constructor when duration <= 0
        try {
            Member m;
            if (cbPremium.isSelected()) {
                PremiumMember pm = new PremiumMember(id, name, (int) spAge.getValue(),
                        (int) spDur.getValue(), cbTrainer.isSelected(), cbSpa.isSelected());
                String planName = tfPlan.getText().trim().isEmpty() ? "Plan" : tfPlan.getText().trim();
                pm.setWorkoutPlan(new WorkoutPlan(planName, (int) spWeeks.getValue(), (int) spMaxEx.getValue()));
                m = pm;
            } else {
                m = new Member(id, name, (int) spAge.getValue(), (int) spDur.getValue());
            }

            if (gym.addMember(m)) {
                rebuildList();
                setStatus("Member '" + name + "' added successfully.");
            } else {
                showError("Failed to add member.");
            }

        } catch (InvalidInputException | IllegalArgumentException ex) {
            showError("Error adding member: " + ex.getMessage());
        }
    }

    // ── Action: Add Trainer ────────────────────────────────────────────────
    private void doAddTrainer() {
        if (gym.getCurrentTrainerCount() >= maxTrainers) {
            showError("Maximum trainer capacity reached (" + maxTrainers + ")."); return;
        }

        JTextField tfId     = new JTextField(10);
        JTextField tfName   = new JTextField(15);
        JSpinner   spAge    = new JSpinner(new SpinnerNumberModel(30, 18, 70, 1));
        JTextField tfSpec   = new JTextField(15);
        JSpinner   spSalary = new JSpinner(new SpinnerNumberModel(3000, 500, 50000, 500));

        JPanel form = new JPanel(new GridLayout(0, 2, 6, 6));
        form.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        form.add(new JLabel("Trainer ID:"));            form.add(tfId);
        form.add(new JLabel("Name:"));                  form.add(tfName);
        form.add(new JLabel("Age:"));                   form.add(spAge);
        form.add(new JLabel("Specialization:"));        form.add(tfSpec);
        form.add(new JLabel("Monthly Salary (SAR):")); form.add(spSalary);

        if (JOptionPane.showConfirmDialog(this, form, "Add Trainer",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) != JOptionPane.OK_OPTION) return;

        int id;
        try { id = Integer.parseInt(tfId.getText().trim()); }
        catch (NumberFormatException ex) { showError("Invalid ID — must be an integer."); return; }

        String name = tfName.getText().trim();
        if (name.isEmpty()) { showError("Name cannot be empty."); return; }
        if (gym.searchTrainerRecursive(id) != null) { showError("ID " + id + " is already in use."); return; }

        // --- CHANGE: wrapped in try/catch for InvalidAgeException ---
        // InvalidAgeException thrown in Person.setAge() propagates here
        try {
            Trainer t = new Trainer(id, name, (int) spAge.getValue(),
                    tfSpec.getText().trim(), (double)(int) spSalary.getValue());
            if (gym.addTrainer(t)) {
                rebuildList();
                setStatus("Trainer '" + name + "' added successfully.");
            } else {
                showError("Failed to add trainer.");
            }
        } catch (InvalidInputException ex) {
            showError("Error adding trainer: " + ex.getMessage());
        }
    }

    // ── Action: Remove ─────────────────────────────────────────────────────
    private void doRemove() {
        String[] opts = {"Member", "Trainer", "Cancel"};
        int type = JOptionPane.showOptionDialog(this, "Remove a Member or Trainer?", "Remove",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opts, opts[0]);
        if (type < 0 || type == 2) return;

        String input = JOptionPane.showInputDialog(this, "Enter ID to remove:");
        if (input == null || input.trim().isEmpty()) return;

        int id;
        try { id = Integer.parseInt(input.trim()); }
        catch (NumberFormatException ex) { showError("Invalid ID."); return; }

        boolean ok = (type == 0) ? gym.removeMember(id) : gym.removeTrainer(id);
        if (ok) {
            rebuildList();
            setStatus((type == 0 ? "Member" : "Trainer") + " ID " + id + " removed.");
        } else {
            showError((type == 0 ? "Member" : "Trainer") + " ID " + id + " not found.");
        }
    }

    // ── Action: Search Member ──────────────────────────────────────────────
    private void doSearchMember() {
        String input = JOptionPane.showInputDialog(this, "Enter Member ID to search:");
        if (input == null || input.trim().isEmpty()) return;
        int id;
        try { id = Integer.parseInt(input.trim()); }
        catch (NumberFormatException ex) { showError("Invalid ID."); return; }

        Member m = gym.searchMemberRecursive(id);
        if (m != null) {
            StringBuilder sb = new StringBuilder("--- Search Result ---\n\n").append(m.toString());
            sb.append("\n\nMonthly Fee: ").append(m.calculateFee()).append(" SAR");
            if (m instanceof PremiumMember) {
                WorkoutPlan plan = ((PremiumMember) m).getWorkoutPlan();
                if (plan != null) sb.append("\n\n").append(plan.toString());
            }
            resultFrame.display("Search Result", sb.toString());
            setStatus("Found member: " + m.getName());
        } else {
            showError("No member found with ID " + id + ".");
        }
    }

    // ── Action: Search Trainer ─────────────────────────────────────────────
    private void doSearchTrainer() {
        String input = JOptionPane.showInputDialog(this, "Enter Trainer ID to search:");
        if (input == null || input.trim().isEmpty()) return;
        int id;
        try { id = Integer.parseInt(input.trim()); }
        catch (NumberFormatException ex) { showError("Invalid ID."); return; }

        Trainer t = gym.searchTrainerRecursive(id);
        if (t != null) {
            resultFrame.display("Search Result",
                    "--- Search Result ---\n\n" + t.toString()
                            + "\n\nMonthly Salary: " + t.calculateFee() + " SAR");
            setStatus("Found trainer: " + t.getName());
        } else {
            showError("No trainer found with ID " + id + ".");
        }
    }

    // ── Action: Display All ────────────────────────────────────────────────
    private void doDisplayAll() {
        StringBuilder sb = new StringBuilder("=== All Members ===\n\n");
        Node<Member> mn = gym.getMembersList().getFirstNode();
        if (mn == null) {
            sb.append("No members enrolled.\n");
        } else {
            int i = 1;
            while (mn != null) {
                sb.append(i++).append(")  ").append(mn.data.toString()).append("\n");
                mn = mn.nextNode;
            }
        }
        sb.append("\n=== All Trainers ===\n\n");
        Node<Trainer> tn = gym.getTrainersList().getFirstNode();
        if (tn == null) {
            sb.append("No trainers employed.\n");
        } else {
            int i = 1;
            while (tn != null) {
                sb.append(i++).append(")  ").append(tn.data.toString()).append("\n");
                tn = tn.nextNode;
            }
        }
        resultFrame.display("All Members & Trainers", sb.toString());
        setStatus("Members: " + gym.getCurrentMembersCount() + "/" + maxMembers
                + "   Trainers: " + gym.getCurrentTrainerCount() + "/" + maxTrainers);
    }

    // ── Action: Workout Plan ───────────────────────────────────────────────
    private void doWorkoutPlan() {
        String input = JOptionPane.showInputDialog(this, "Enter Premium Member ID:");
        if (input == null || input.trim().isEmpty()) return;
        int id;
        try { id = Integer.parseInt(input.trim()); }
        catch (NumberFormatException ex) { showError("Invalid ID."); return; }

        Member m = gym.searchMemberRecursive(id);
        if (!(m instanceof PremiumMember)) {
            showError("No premium member found with ID " + id + "."); return;
        }
        PremiumMember pm   = (PremiumMember) m;
        WorkoutPlan   plan = pm.getWorkoutPlan();
        if (plan == null) { showError("This member has no workout plan."); return; }

        String[] opts = {"Add Exercise", "Remove Exercise", "Search Exercise", "Cancel"};
        int choice = JOptionPane.showOptionDialog(this,
                "Plan: " + plan.getPlanName() + "   Member: " + pm.getName(),
                "Workout Plan Management",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opts, opts[0]);

        if (choice == 0) {
            JTextField tfEx  = new JTextField(15);
            JSpinner   spSet = new JSpinner(new SpinnerNumberModel(3,  1, 20,  1));
            JSpinner   spRep = new JSpinner(new SpinnerNumberModel(10, 1, 100, 1));
            JPanel form = new JPanel(new GridLayout(3, 2, 6, 6));
            form.add(new JLabel("Exercise Name:")); form.add(tfEx);
            form.add(new JLabel("Sets:"));          form.add(spSet);
            form.add(new JLabel("Repetitions:"));   form.add(spRep);

            if (JOptionPane.showConfirmDialog(this, form, "Add Exercise",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) != JOptionPane.OK_OPTION) return;

            String exName = tfEx.getText().trim();
            if (exName.isEmpty()) { showError("Exercise name cannot be empty."); return; }

            if (plan.addExercise(new Exercise(exName, (int) spSet.getValue(), (int) spRep.getValue()))) {
                resultFrame.display("Workout Plan", pm.toString() + "\n\n" + plan.toString());
                setStatus("Exercise '" + exName + "' added to plan.");
            } else {
                showError("Plan is full — maximum exercise limit reached.");
            }

        } else if (choice == 1) {
            String exName = JOptionPane.showInputDialog(this, "Enter exercise name to remove:");
            if (exName == null || exName.trim().isEmpty()) return;
            if (plan.removeExercise(exName.trim())) {
                resultFrame.display("Workout Plan", pm.toString() + "\n\n" + plan.toString());
                setStatus("Exercise '" + exName.trim() + "' removed.");
            } else {
                showError("Exercise '" + exName.trim() + "' not found.");
            }

        } else if (choice == 2) {
            String exName = JOptionPane.showInputDialog(this, "Enter exercise name to search:");
            if (exName == null || exName.trim().isEmpty()) return;
            Exercise found = plan.searchExerciseRecursive(exName.trim(), 0);
            if (found != null) {
                resultFrame.display("Exercise Found", "--- Exercise Found ---\n\n" + found.toString());
                setStatus("Found: " + found.getName());
            } else {
                showError("Exercise '" + exName.trim() + "' not found.");
            }
        }
    }

    // ── Action: Payment ────────────────────────────────────────────────────
    private void doPayment() {
        String input = JOptionPane.showInputDialog(this, "Enter ID to process payment:");
        if (input == null || input.trim().isEmpty()) return;
        int id;
        try { id = Integer.parseInt(input.trim()); }
        catch (NumberFormatException ex) { showError("Invalid ID."); return; }

        Person p = gym.searchMemberRecursive(id);
        if (p == null) p = gym.searchTrainerRecursive(id);

        if (p instanceof Payable) {
            double amount = ((Payable) p).makePayment();
            JOptionPane.showMessageDialog(this,
                    "Payment processed for: " + p.getName() + "\nAmount: " + amount + " SAR",
                    "Payment Successful", JOptionPane.INFORMATION_MESSAGE);
            setStatus("Payment of " + amount + " SAR processed for " + p.getName() + ".");
        } else {
            showError("No person found with ID " + id + ".");
        }
    }

    // ── Action: Save ───────────────────────────────────────────────────────
    // --- CHANGE: new save method calling GymFileManager ---
    private void doSave() {
        GymFileManager.saveGym(gym);
        setStatus("Gym data saved successfully.");
        JOptionPane.showMessageDialog(this,
                "Gym data saved successfully.",
                "Save", JOptionPane.INFORMATION_MESSAGE);
    }
}