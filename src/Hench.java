import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class Hench extends DefaultListCellRenderer {
    private JFrame frame;
    private JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JList rosterJlist;
    private JButton addPlayerButton;
    private JList allPlayersJlist;
    private JButton removePlayerButton;
    private JButton trustButton;
    private JComboBox dropDownRaidSelect;
    private JButton clearListButton;
    private JTextField textfieldMaxMelee;
    private JTextField textfieldMaxTanks;
    private JTextField textfieldMaxRanged;
    private JTextField healerAmountTextField;
    private JLabel tankLabel;
    private JLabel healerLabel;
    private JLabel rangedLabel;
    private JLabel meleeLabel;
    private int tankCounter;
    private int healerCounter;
    private int meleeCounter;
    private int rangedCounter;
    private JList jListPlayerManager;
    private JButton addPlayerButton1;
    private JTextField textFieldNewPlayerRole;
    private JTextField textFieldNewPlayerName;
    private JTextField textFieldNewPlayerClass;
    private JButton rmPlayerButton;
    private JComboBox comboBoxClassDropDown;
    private JComboBox comboBoxRoleDropDown;
    private JTextArea textAreaCounter;
    private JButton addRaidButton;
    private JButton deleteRaidButton;
    private JTextField textFieldAddRaidNewName;
    private JTextField textFieldAddRaidMaxPlayers;
    private JList jListAllRaids;
    private JComboBox comboBox1;
    private JButton sortButton;
    private JLabel textPaneTanksCount;
    private JLabel textPaneHealsCount;
    private JLabel textPaneMeleeCount;
    private JLabel textPaneRangedCount;
    private JButton addAllButton;

    private ImageIcon tankIcon = new ImageIcon(getClass().getResource("/img/tank-removebg.png"));
    private ImageIcon healerIcon = new ImageIcon(getClass().getResource("/img/healerxx-removebg.png"));
    private ImageIcon meleeIcon = new ImageIcon(getClass().getResource("/img/melee-removebg.png"));
    private ImageIcon rangedIcon = new ImageIcon(getClass().getResource("/img/ranged-removebg.png"));

    private ArrayList<Player> allPlayers = new ArrayList<>();
    private DefaultListModel modelAllPlayers = new DefaultListModel<>();
    private ArrayList<Player> selectedRosterPlayers = new ArrayList<>();
    private DefaultListModel modelSelectedRosterPlayers = new DefaultListModel<>();
    private DefaultListModel emptyModel = new DefaultListModel<>();
    private DefaultComboBoxModel<Raid> allRaidsCombobox = new DefaultComboBoxModel<>();
    private Raid raid = new Raid();
    private Raid newRaid;
    private DatabaseManager databaseManager = new DatabaseManager();
    private GeneratedRoster generatedRoster = new GeneratedRoster();
    private int counter = 0;


    public Hench() throws IOException {
        rosterJlist.setCellRenderer(new HenchListCellRenderer());
        allPlayersJlist.setCellRenderer(new HenchListCellRenderer());
        jListPlayerManager.setCellRenderer(new HenchListCellRenderer());
        textAreaCounter.setEditable(false);
        textPaneRangedCount.setText("0");
        textPaneMeleeCount.setText("0");
        textPaneHealsCount.setText("0");
        textPaneTanksCount.setText("0");
        textAreaCounter.setText("0");
        trustButton.setEnabled(false);
        tankLabel.setIcon(tankIcon);
        healerLabel.setIcon(healerIcon);
        meleeLabel.setIcon(meleeIcon);
        rangedLabel.setIcon(rangedIcon);
        addPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (allPlayersJlist.getSelectedValue() != null) {
                    roleCount();
                    if (!selectedRosterPlayers.contains(allPlayersJlist.getSelectedValue())) {
                        selectedRosterPlayers.add((Player) allPlayersJlist.getSelectedValue());
                    }
                    try {
                        modelAllPlayers.remove(allPlayersJlist.getSelectedIndex());
                    } catch (ArrayIndexOutOfBoundsException q) {
                        JOptionPane.showMessageDialog(mainPanel, "Select a player first");
                        counter--;
                    }
                    modelSelectedRosterPlayers.clear();
                    modelSelectedRosterPlayers.addAll(selectedRosterPlayers);
                    rosterJlist.setModel(modelSelectedRosterPlayers);
                    allPlayersJlist.setModel(modelAllPlayers);

                    counter++;
                    textAreaCounter.setText(String.valueOf(counter));

                    Object raidSelectedInComboBox = allRaidsCombobox.getSelectedItem();
                    if (textAreaCounter.getText().equals("25") && raidSelectedInComboBox.toString().contains("25")) {
                        textAreaCounter.setForeground(Color.RED);
                    } else if (textAreaCounter.getText().equals("10") && raidSelectedInComboBox.toString().contains("10")) {
                        textAreaCounter.setForeground(Color.RED);
                    }
                }
            }
        });
        removePlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) throws ArrayIndexOutOfBoundsException {
                if (rosterJlist.getSelectedValue() != null) {
                    roleDecrease();
                    Iterator it = selectedRosterPlayers.iterator();
                    if (!modelAllPlayers.contains(rosterJlist.getSelectedValue())) {
                        modelAllPlayers.addElement(rosterJlist.getSelectedValue());
                    }
                    Object player = rosterJlist.getSelectedValue();
                    try {
                        if (player != null && counter > 0) {
                            counter--;
                        }

                    } catch (ArrayIndexOutOfBoundsException q) {

                    }
                    try {
                        while (it.hasNext()) {
                            if (it.next().equals(player)) {
                                it.remove();
                            }
                        }
                        modelSelectedRosterPlayers.remove(rosterJlist.getSelectedIndex());
                    } catch (IndexOutOfBoundsException i) {
                        System.out.println("OutOfBounds No player selected");
                    } catch (NullPointerException np) {
                        System.out.println("Nullpointer No player selected");
                    }
                    rosterJlist.setModel(modelSelectedRosterPlayers);
                    allPlayersJlist.setModel(modelAllPlayers);
                    textAreaCounter.setText(String.valueOf(counter));
                    if (modelSelectedRosterPlayers.isEmpty()) {
                        selectedRosterPlayers.clear();
                    }
                    try {
                        Object raidSelectedInComboBox = allRaidsCombobox.getSelectedItem();
                        if (textAreaCounter.getText().equals("24") && raidSelectedInComboBox.toString().contains("25")) {
                            textAreaCounter.setForeground(Color.BLACK);
                        } else if (textAreaCounter.getText().equals("9") && raidSelectedInComboBox.toString().contains("10")) {
                            textAreaCounter.setForeground(Color.BLACK);
                        }
                    } catch (NullPointerException np) {
                        System.out.println("No raid selected");
                    }
                    sortAllPlayerList();
                }
            }
        });
        trustButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    Raid generatedRaid = new Raid("", 0, 0, 0, 0, 0, 0, 0, 0, 0);
                    try {
                        int maxMelee = Integer.parseInt(textfieldMaxMelee.getText());
                        int maxRanged = Integer.parseInt(textfieldMaxRanged.getText());
                        int maxHealers = Integer.parseInt(healerAmountTextField.getText());
                        int maxTanks = Integer.parseInt(textfieldMaxTanks.getText());
                        int totalRoles = maxMelee + maxRanged + maxHealers + maxTanks;
                        int maxAmountOfPlayers = databaseManager.readRaidMaxPlayers(allRaidsCombobox.getSelectedItem().toString());
                        if ((maxAmountOfPlayers == 25 && (totalRoles != 25))
                                || (maxAmountOfPlayers == 10 && (totalRoles != 10))) {
                            JOptionPane.showMessageDialog(mainPanel, "The quantity of maximum raiders entered does not align with the maximum number of raiders for this raid");
                        } else if (allRaidsCombobox.getSelectedItem() != null) {

                            generatedRaid = new Raid(allRaidsCombobox.getSelectedItem().toString(), maxAmountOfPlayers, maxMelee, maxRanged, maxHealers, maxTanks, 0, 0, 0, 0);
                            generatedRoster.run(raid.createRoster(generatedRaid, selectedRosterPlayers, allRaidsCombobox.getSelectedItem().toString()));;

                        }
                    } catch (NumberFormatException n) {
                        JOptionPane.showMessageDialog(mainPanel, "Please input the required number of tanks, healers, melee and ranged in the designated fields for proper raid formation");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (UnsupportedLookAndFeelException ex) {
                        throw new RuntimeException(ex);
                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    } catch (InstantiationException ex) {
                        throw new RuntimeException(ex);
                    } catch (IllegalAccessException ex) {
                        throw new RuntimeException(ex);
                    }
            }
        });
        clearListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedRosterPlayers.clear();
                modelSelectedRosterPlayers.clear();
                rosterJlist.setModel(modelSelectedRosterPlayers);
                modelAllPlayers.clear();
                modelAllPlayers.addAll(allPlayers);
                allPlayersJlist.setModel(modelAllPlayers);
                counter = 0;
                roleCounterReset();
                textAreaCounter.setText(String.valueOf(counter));
                if (textAreaCounter.getForeground().equals(Color.RED)) {
                    textAreaCounter.setForeground(Color.BLACK);
                }
            }
        });
        textfieldMaxTanks.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                textfieldMaxTanks.setText("");
            }
        });
        healerAmountTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                healerAmountTextField.setText("");
            }
        });
        textfieldMaxRanged.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                textfieldMaxRanged.setText("");
            }
        });
        textfieldMaxMelee.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                textfieldMaxMelee.setText("");
            }
        });
        allPlayersJlist.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    roleCount();
                    JList list = (JList) e.getSource();
                    int index = list.locationToIndex(e.getPoint());
                    Object item = list.getModel().getElementAt(index);
                    if (!selectedRosterPlayers.contains(allPlayersJlist.getSelectedValue())) {
                        selectedRosterPlayers.add((Player) allPlayersJlist.getSelectedValue());
                    }
                    modelAllPlayers.remove(allPlayersJlist.getSelectedIndex());
                    modelSelectedRosterPlayers.clear();
                    modelSelectedRosterPlayers.addAll(selectedRosterPlayers);
                    rosterJlist.setModel(modelSelectedRosterPlayers);
                    allPlayersJlist.setModel(modelAllPlayers);
                    counter++;
                    textAreaCounter.setText(String.valueOf(counter));
                    try {
                        Object raidSelectedInComboBox = allRaidsCombobox.getSelectedItem();
                        if (textAreaCounter.getText().equals("25") && raidSelectedInComboBox.toString().contains("25")) {
                            textAreaCounter.setForeground(Color.RED);
                        } else if (textAreaCounter.getText().equals("10") && raidSelectedInComboBox.toString().contains("10")) {
                            textAreaCounter.setForeground(Color.RED);
                        }
                    } catch (NullPointerException np) {
                        System.out.println("No raid selected");
                    }
                }
            }
        });
        rmPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Player player = new Player("", "", "");
                if (allPlayers.contains(jListPlayerManager.getSelectedValue())) {
                    for (Player p : allPlayers) {
                        player = p;
                    }
                    try {
                        databaseManager.removePlayer(player);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    allPlayers.remove(jListPlayerManager.getSelectedValue());
                    modelAllPlayers.clear();
                    modelAllPlayers.addAll(allPlayers);
                    jListPlayerManager.setModel(modelAllPlayers);
                    try {
                        if (textAreaCounter.getText().equals("24") && comboBoxClassDropDown.getSelectedItem().toString().equals("25")) {
                            textAreaCounter.setForeground(Color.BLACK);
                        } else if (textAreaCounter.getText().equals("24") && comboBoxClassDropDown.getSelectedItem().toString().equals("25")) {

                        }
                    } catch (NullPointerException np) {
                        System.out.println("No raid selected");
                    }
                }
            }
        });
        addPlayerButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboBoxClassDropDown.getSelectedItem().equals("Select Class") || comboBoxRoleDropDown.getSelectedItem().equals("Select Role")) {
                    JOptionPane.showMessageDialog(mainPanel, "Please select class and or role");
                } else if (textFieldNewPlayerName.getText().equals("")) {
                    JOptionPane.showMessageDialog(mainPanel, "Please enter a name");
                } else {
                    String playerName = textFieldNewPlayerName.getText();
                    String playerClass = (String) comboBoxClassDropDown.getSelectedItem();
                    String playerRole = (String) comboBoxRoleDropDown.getSelectedItem();
                    Player newPlayer = new Player(playerName, playerClass, playerRole);
                    try {
                        databaseManager.writePlayer(newPlayer);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    allPlayers.add(newPlayer);
                    modelAllPlayers.clear();
                    modelAllPlayers.addAll(allPlayers);
                    jListPlayerManager.setModel(modelAllPlayers);
                }
            }
        });
        addRaidButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textFieldAddRaidNewName.getText().equals("")) {
                    JOptionPane.showMessageDialog(mainPanel, "Please enter a name for the raid. example: 25:Ulduar");
                } else {
                    String newRaidName = textFieldAddRaidNewName.getText();
                    String newRaidMaxPlayers = (String) comboBox1.getSelectedItem();
                    int playerNumber = Integer.parseInt(newRaidMaxPlayers);
                    Raid newRaid = new Raid(newRaidName, playerNumber, 0, 0, 0, 0, 0, 0, 0, 0);
                    try {
                        databaseManager.writeRaid(newRaid);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    textFieldAddRaidNewName.setText("");
                    allRaidsCombobox.addElement(newRaid);
                }
            }
        });
        deleteRaidButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    databaseManager.deleteRaid((Raid) jListAllRaids.getSelectedValue());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                allRaidsCombobox.removeElement(jListAllRaids.getSelectedValue());
            }
        });
        textFieldAddRaidNewName.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                textFieldAddRaidNewName.setText("");
            }
        });
        rosterJlist.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    if (rosterJlist.getSelectedValue() != null) {
                        roleDecrease();
                        Iterator it = selectedRosterPlayers.iterator();
                        if (!modelAllPlayers.contains(rosterJlist.getSelectedValue())) {
                            modelAllPlayers.addElement(rosterJlist.getSelectedValue());
                        }
                        Object player = rosterJlist.getSelectedValue();
                        try {
                            if (player != null && counter > 0) {
                                counter--;
                            }

                        } catch (ArrayIndexOutOfBoundsException q) {

                        }
                        try {
                            while (it.hasNext()) {
                                if (it.next().equals(player)) {
                                    it.remove();
                                }
                            }
                            modelSelectedRosterPlayers.remove(rosterJlist.getSelectedIndex());
                        } catch (IndexOutOfBoundsException i) {
                            System.out.println("OutOfBounds No player selected");
                        } catch (NullPointerException np) {
                            System.out.println("Nullpointer No player selected");
                        }
                        rosterJlist.setModel(modelSelectedRosterPlayers);
                        allPlayersJlist.setModel(modelAllPlayers);
                        textAreaCounter.setText(String.valueOf(counter));
                        if (modelSelectedRosterPlayers.isEmpty()) {
                            selectedRosterPlayers.clear();
                        }
                        try {
                            Object raidSelectedInComboBox = allRaidsCombobox.getSelectedItem();
                            if (textAreaCounter.getText().equals("24") && raidSelectedInComboBox.toString().contains("25")) {
                                textAreaCounter.setForeground(Color.BLACK);
                            } else if (textAreaCounter.getText().equals("9") && raidSelectedInComboBox.toString().contains("10")) {
                                textAreaCounter.setForeground(Color.BLACK);
                            }
                        } catch (NullPointerException np) {
                            System.out.println("No raid selected");
                        }
                        sortAllPlayerList();
                    }
                }
            }
        });
        dropDownRaidSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (allRaidsCombobox.getSelectedItem() != null) {
                    trustButton.setEnabled(true);
                }
            }
        });
        addAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                allPlayersJlist.getSelectedValuesList();
            }
        });
    }

    public ArrayList<Raid> loadAllRaid() throws IOException {
        return databaseManager.readRaids();
    }

    public void sortAllPlayerList() {
        ArrayList<Player> list = new ArrayList<>();
        for (int i = 0; i < modelAllPlayers.size(); i++) {
            list.add((Player) modelAllPlayers.get(i));
        }
        if (!modelAllPlayers.isEmpty() && !list.isEmpty()) {
            list.sort(Comparator.comparing(Player::getPlayerClass));
            modelAllPlayers.clear();
        }
        for (Player player : list) {
            modelAllPlayers.addElement(player);
        }
    }

    public void roleCount() throws NullPointerException {
        try {
            Player player = (Player) allPlayersJlist.getSelectedValue();
            if (player.getRole().equals("Tank")) {
                tankCounter++;
                textPaneTanksCount.setText(String.valueOf(tankCounter));
            } else if (player.getRole().equals("Healer")) {
                healerCounter++;
                textPaneHealsCount.setText(String.valueOf(healerCounter));
            } else if (player.getRole().equals("Melee")) {
                meleeCounter++;
                textPaneMeleeCount.setText(String.valueOf(meleeCounter));
            } else if (player.getRole().equals("Ranged")) {
                rangedCounter++;
                textPaneRangedCount.setText(String.valueOf(rangedCounter));
            }
        } catch (NullPointerException e) {
        }
    }

    public void roleDecrease() {
        try {
            Player player = (Player) rosterJlist.getSelectedValue();
            if (player.getRole().equals("Tank")) {
                tankCounter--;
                textPaneTanksCount.setText(String.valueOf(tankCounter));
            } else if (player.getRole().equals("Healer")) {
                healerCounter--;
                textPaneHealsCount.setText(String.valueOf(healerCounter));
            } else if (player.getRole().equals("Melee")) {
                meleeCounter--;
                textPaneMeleeCount.setText(String.valueOf(meleeCounter));
            } else if (player.getRole().equals("Ranged")) {
                rangedCounter--;
                textPaneRangedCount.setText(String.valueOf(rangedCounter));
            }
        } catch (NullPointerException e) {

        }
    }

    public void roleCounterReset() {
        tankCounter = 0;
        healerCounter = 0;
        meleeCounter = 0;
        rangedCounter = 0;
        textPaneRangedCount.setText(String.valueOf(rangedCounter));
        textPaneMeleeCount.setText(String.valueOf(meleeCounter));
        textPaneHealsCount.setText(String.valueOf(healerCounter));
        textPaneTanksCount.setText(String.valueOf(tankCounter));
    }

    public void run() throws IOException, ParseException, UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        frame = new JFrame();
        jListPlayerManager.setModel(modelAllPlayers);
        allPlayers.addAll(databaseManager.readPlayers());
        modelAllPlayers.addAll(allPlayers);
        allPlayersJlist.setModel(modelAllPlayers);
        frame.setContentPane(mainPanel);
        frame.setTitle("Hench");
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        allRaidsCombobox.addAll(databaseManager.readRaids());
        jListAllRaids.setModel(allRaidsCombobox);
        dropDownRaidSelect.setModel(allRaidsCombobox);
        frame.setSize(800,600);
        frame.setVisible(true);
    }

}
