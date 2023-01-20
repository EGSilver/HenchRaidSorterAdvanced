import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class HenchTableCellRenderer extends DefaultTableCellRenderer {
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        String role = (String) table.getValueAt(row, 1);
        if (role.equals("Shaman")) {
            setBackground(new Color(0, 112, 221));
        } else if (role.equals("Rogue")) {
            setBackground(new Color(255, 244, 104));
        } else if (role.equals("Warrior")) {
            setBackground(new Color(198, 155, 109));
        } else if (role.equals("Hunter")) {
            setBackground(new Color(170, 211, 114));
        } else if (role.equals("Priest")) {
            setBackground(new Color(255, 255, 255));
        } else if (role.equals("Death Knight")) {
            setBackground(new Color(139, 30, 58));
            setForeground(Color.BLACK);
        } else if (role.equals("Mage")) {
            setBackground(new Color(63, 199, 235));
        } else if (role.equals("Warlock")) {
            setBackground(new Color(135, 136, 238));
        } else if (role.equals("Druid")) {
            setBackground(new Color(255, 124, 10));
        } else if (role.equals("Paladin")) {
            setBackground(new Color(244, 140, 186));
        }
        return this;
    }

}