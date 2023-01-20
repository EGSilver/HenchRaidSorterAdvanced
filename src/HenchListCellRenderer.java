import java.awt.*;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

public class HenchListCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof Player) {
            Player player = (Player) value;
            if (player.getPlayerClass().equals("Shaman")) {
                c.setBackground(new Color(0,112,221));
            } else if (player.getPlayerClass().equals("Rogue")) {
                c.setBackground(new Color(255, 244, 104));
            } else if (player.getPlayerClass().equals("Warrior")) {
                c.setBackground(new Color(198,155,109));
            } else if (player.getPlayerClass().equals("Hunter")) {
                c.setBackground(new Color(170,211,114));
            } else if (player.getPlayerClass().equals("Priest")) {
                c.setBackground(new Color(255,255,255));
            } else  if (player.getPlayerClass().equals("Death Knight")) {
                c.setBackground(new Color(139,30,58));
                c.setForeground(Color.BLACK);
            } else if (player.getPlayerClass().equals("Mage")) {
                c.setBackground(new Color(63, 199, 235));
            } else if (player.getPlayerClass().equals("Warlock")) {
                c.setBackground(new Color(135, 136, 238));
            } else if (player.getPlayerClass().equals("Druid")) {
                c.setBackground(new Color(255, 124, 10));
            } else if (player.getPlayerClass().equals("Paladin")) {
                c.setBackground(new Color(244, 140 ,186));
            }
        }
        return c;
    }
}