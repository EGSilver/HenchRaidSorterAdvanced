public class Player {
    private String name;
    private String playerClass;
    private String role;

    public Player(String name, String playerClass, String role) {
        this.name = name;
        this.playerClass = playerClass;
        this.role = role;
    }

    public Player(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlayerClass() {
        return playerClass;
    }

    public void setPlayerClass(String playerClass) {
        this.playerClass = playerClass;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return  name +
                ", "  + playerClass +
                ", " + role;
    }
}
