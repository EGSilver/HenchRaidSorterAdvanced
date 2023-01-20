import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.text.ParseException;
import javax.swing.JTable;
import java.util.ArrayList;
import java.util.Comparator;

public class GeneratedRoster {

    private JFrame frame;
    private JPanel mainPanel;
    private JList jListGeneratedRoster;
    private JTable table1;
    private DefaultListModel generatedRosterModel = new DefaultListModel<>();
    private DefaultTableModel model = new DefaultTableModel();

    public GeneratedRoster() {
        model.addColumn("Name");
        model.addColumn("Class");
        model.addColumn("Role");
    }

    public void addPlayerToTable(ArrayList<Player> roster) {
        for (Player player : roster) {
            model.addRow(new Object[] {player.getName(), player.getPlayerClass(), player.getRole()});
        }
    }

    public void sortTableOnClass(ArrayList<Player> roster) {
        if (!roster.isEmpty() && !roster.isEmpty()) {
            roster.sort(Comparator.comparing(Player::getRole));
        }
    }

    public void run(ArrayList<Player> roster) throws IOException, ParseException, UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        sortTableOnClass(roster);
        frame = new JFrame();
        addPlayerToTable(roster);
        System.out.println(roster);
        table1.setModel(model);
        generatedRosterModel.addAll(roster);
        table1.setDefaultRenderer(Object.class, new HenchTableCellRenderer());
        frame.setContentPane(mainPanel);
        frame.setTitle("Hench Generated Roster");
        frame.pack();
        frame.setSize(800,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
