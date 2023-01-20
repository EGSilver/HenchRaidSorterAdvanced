import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DatabaseManager {

    private ArrayList<Player> rosterPlayers = new ArrayList<>();
    private ArrayList<Raid> allRaids = new ArrayList<>();

    public ArrayList<Raid> readRaids() throws IOException {
        File playerFile = new File(".\\data\\raids.csv");
        BufferedReader reader = new BufferedReader(new FileReader(playerFile));
        String nextLine;
        while ((nextLine = reader.readLine()) != null) {
            String[] parts = nextLine.split(";");
            String name = parts[0];
            int maxAmountOfPlayers = Integer.parseInt(parts[1]);
            Raid raid = new Raid(name, maxAmountOfPlayers, 0, 0, 0 , 0, 0, 0, 0, 0);
            allRaids.add(raid);
        }
        reader.close();
        return allRaids;
    }

    public int readRaidMaxPlayers(String name) throws IOException {
        int maxRaiders = 0;
        for (Raid raid : allRaids) {
            if (raid.getName().equals(name)) {
                if (raid.getMaxAmountOfPlayers() > maxRaiders) {
                    maxRaiders = raid.getMaxAmountOfPlayers();
                }
            }
        }
        return maxRaiders;
    }

    public String writeRaid(Raid raid) throws IOException {
        String writeInfo = "";
        boolean raidExists = false;
        File playerFile = new File(".\\data\\raids.csv");
        BufferedReader reader = new BufferedReader(new FileReader(playerFile));
        String nextLine = reader.readLine();
        while (nextLine != null) {
            String[] parts = nextLine.split(";");
            String name = parts[0];
            if(name.equals(raid.getName())) {
                raidExists = true;
                break;
            }
            nextLine = reader.readLine();
        }
        reader.close();
        if(!raidExists) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(playerFile, true));
            writer.append(raid.getName() + ";" + raid.getMaxAmountOfPlayers());
            writer.newLine();
            writer.close();
            writeInfo = "Raid added to the database file.";
        } else {
            writeInfo = "Raid already exists in the database file.";
        }
        return writeInfo;
    }

    public void deleteRaid(Raid raid) throws IOException {
        File playerFile = new File(".\\data\\raids.csv");
        File tempFile = new File(".\\data\\raids_temp.csv");
        BufferedReader reader = new BufferedReader(new FileReader(playerFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        String nextLine = reader.readLine();
        boolean raidExists = false;
        while (nextLine != null) {
            String[] parts = nextLine.split(";");
            String name = parts[0];
            if(name.equals(raid.getName())) {
                raidExists = true;
            }
            else {
                writer.write(nextLine);
                writer.newLine();
            }
            nextLine = reader.readLine();
        }
        reader.close();
        writer.close();
        if(raidExists) {
            playerFile.delete();
            tempFile.renameTo(playerFile);
            System.out.println("Raid removed from the database file.");
        } else {
            System.out.println("Raid not found in the database file.");
        }
    }

    public ArrayList<Player> readPlayers() throws IOException {
        File playerFile = new File(".\\data\\players.csv");
        BufferedReader reader = new BufferedReader(new FileReader(playerFile));
        String nextLine;
        while ((nextLine = reader.readLine()) != null) {
            String[] parts = nextLine.split(";");
            String name = parts[0];
            String pClass = parts[1];
            String role = parts[2];
            Player player = new Player(name, pClass, role);
            rosterPlayers.add(player);
        }
        Collections.sort(rosterPlayers, Comparator.comparing(Player::getPlayerClass));
        reader.close();
        return rosterPlayers;
    }

    public String writePlayer(Player player) throws IOException {
        String writeInfo = "";
        boolean playerExists = false;
        File playerFile = new File(".\\data\\players.csv");
        BufferedReader reader = new BufferedReader(new FileReader(playerFile));
        String nextLine = reader.readLine();
        while (nextLine != null) {
            String[] parts = nextLine.split(";");
            String name = parts[0];
            if(name.equals(player.getName())) {
                playerExists = true;
                break;
            }
            nextLine = reader.readLine();
        }
        reader.close();
        if(!playerExists) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(playerFile, true));
            writer.append(player.getName() + ";" + player.getPlayerClass() + ";" + player.getRole());
            writer.newLine();
            writer.close();
            writeInfo = "Player added to the database file.";
        } else {
            writeInfo = "Player already exists in the database file.";
        }
        return writeInfo;
    }

    public void removePlayer(Player player) throws IOException {
        File playerFile = new File(".\\data\\players.csv");
        File tempFile = new File(".\\data\\players_temp.csv");
        BufferedReader reader = new BufferedReader(new FileReader(playerFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        String nextLine = reader.readLine();
        boolean playerExists = false;
        while (nextLine != null) {
            String[] parts = nextLine.split(";");
            String name = parts[0];
            if(name.equals(player.getName())) {
                playerExists = true;
            }
            else {
                writer.write(nextLine);
                writer.newLine();
            }
            nextLine = reader.readLine();
        }
        reader.close();
        writer.close();
        if(playerExists) {
            playerFile.delete();
            tempFile.renameTo(playerFile);
            System.out.println("Player removed from the database file.");
        } else {
            System.out.println("Player not found in the database file.");
        }
    }
}
