import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private int point;

    public void setName(String name){this.name = name;}
    public void setPoint(int point){this.point += point;}

    public String getName(){return this.name;}
    public int getPoint(){return this.point;}

    public Player(String name, int point)
    {
        this.name = name;
        this.point = point;
    }

    /**
     * Creates and returns an arraylist of players in the game
     * 
     * @param playerNames An array containing the players in the game, of type String[]
     * @return A list containing the players in the game, of type List<Player>
     */
    public static List<Player> loadPlayers(String[] playerNames)
    {
        List<Player> playerArray = new ArrayList<>();
        for (int i = 0; i<playerNames.length;i++){playerArray.add(new Player(playerNames[i], 0));}
        return playerArray;
    }
}
