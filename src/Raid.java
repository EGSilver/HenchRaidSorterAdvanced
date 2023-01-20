import java.io.IOException;
import java.util.ArrayList;

public class Raid {
    private String name;
    private int maxAmountOfPlayers;
    private int maxAmountOfMelee;
    private int maxAmountofRanged;
    private int maxAmountofHealers;
    private int maxAmountofTanks;
    private int tanks;
    private int healers;
    private int melee;
    private int ranged;
    private DatabaseManager databaseManager = new DatabaseManager();

    private ArrayList<Player> rosterPlayers = new ArrayList<>();

    public Raid(String name, int maxAmountOfPlayers, int maxAmountOfMelee, int maxAmountofRanged, int maxAmountofHealers, int maxAmountofTanks, int tanks, int healers, int melee, int ranged) {
        this.name = name;
        this.maxAmountOfPlayers = maxAmountOfPlayers;
        this.maxAmountOfMelee = maxAmountOfMelee;
        this.maxAmountofRanged = maxAmountofRanged;
        this.maxAmountofHealers = maxAmountofHealers;
        this.maxAmountofTanks = maxAmountofTanks;
        this.tanks = tanks;
        this.healers = healers;
        this.melee = melee;
        this.ranged = ranged;
    }

    public Raid(String name, int maxAmountOfPlayers, int maxAmountOfMelee, int maxAmountofRanged, int maxAmountofHealers, int maxAmountofTanks) {
        this.name = name;
        this.maxAmountOfPlayers = maxAmountOfPlayers;
        this.maxAmountOfMelee = maxAmountOfMelee;
        this.maxAmountofRanged = maxAmountofRanged;
        this.maxAmountofHealers = maxAmountofHealers;
        this.maxAmountofTanks = maxAmountofTanks;
    }

    public Raid() {
    }

    public ArrayList<Player> createRoster(Raid raid, ArrayList<Player> player, String raidName) throws IOException {
        if (rosterPlayers.size() < databaseManager.readRaidMaxPlayers(raidName) || rosterPlayers.size() == 0) {
            for (Player p : player) {
                if (p.getRole().equals("Tank") && tanks < raid.getMaxAmountofTanks() && !rosterPlayers.contains(p)) {
                    rosterPlayers.add(p);
                    tanks++;
                } else if (p.getRole().equals("Healer") && healers < raid.getMaxAmountofHealers() && !rosterPlayers.contains(p)) {
                    rosterPlayers.add(p);
                    healers++;
                } else if (p.getRole().equals("Ranged") && ranged < raid.getMaxAmountofRanged() && !rosterPlayers.contains(p)) {
                    rosterPlayers.add(p);
                    ranged++;
                } else if (p.getRole().equals("Melee") && melee < raid.getMaxAmountOfMelee() && !rosterPlayers.contains(p)) {
                    rosterPlayers.add(p);
                    melee++;
                }
            }
        }
        return rosterPlayers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxAmountOfPlayers() {
        return maxAmountOfPlayers;
    }

    public void setMaxAmountOfPlayers(int maxAmountOfPlayers) {
        this.maxAmountOfPlayers = maxAmountOfPlayers;
    }

    public int getMaxAmountOfMelee() {
        return maxAmountOfMelee;
    }

    public void setMaxAmountOfMelee(int maxAmountOfMelee) {
        this.maxAmountOfMelee = maxAmountOfMelee;
    }

    public int getMaxAmountofRanged() {
        return maxAmountofRanged;
    }

    public void setMaxAmountofRanged(int maxAmountofRanged) {
        this.maxAmountofRanged = maxAmountofRanged;
    }

    public int getMaxAmountofHealers() {
        return maxAmountofHealers;
    }

    public void setMaxAmountofHealers(int maxAmountofHealers) {
        this.maxAmountofHealers = maxAmountofHealers;
    }

    public int getMaxAmountofTanks() {
        return maxAmountofTanks;
    }

    public void setMaxAmountofTanks(int maxAmountofTanks) {
        this.maxAmountofTanks = maxAmountofTanks;
    }

    public ArrayList<Player> getRosterPlayers() {
        return rosterPlayers;
    }

    public int getTanks() {
        return tanks;
    }

    public void setTanks(int tanks) {
        this.tanks = tanks;
    }

    public int getHealers() {
        return healers;
    }

    public void setHealers(int healers) {
        this.healers = healers;
    }

    public int getMelee() {
        return melee;
    }

    public void setMelee(int melee) {
        this.melee = melee;
    }

    public int getRanged() {
        return ranged;
    }

    public void setRanged(int ranged) {
        this.ranged = ranged;
    }

    public void setRosterPlayers(ArrayList<Player> rosterPlayers) {
        this.rosterPlayers = rosterPlayers;
    }

    @Override
    public String toString() {
        return name;
    }
}
