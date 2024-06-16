import java.util.List;
class DiceGame
{
    public static void main(String[] args)
    {
        String[] data = FileIO.readFile(args[0], false, false);
        final String[] playerNames = data[1].split(","); 
        List<Player> playerArray = Player.loadPlayers(playerNames);
        startGame(data, playerArray,args);
    }

    /**
     * Start to dice game as given rules. At each step the steps are added to the output file.
     * 
     * <ul> <li>The possible scenarios with the corresponding outputs are as follows:
     * <ul> <li>If he threw the dice as 6-6: <name> threw 6-6 and <name>’s score is 77.</li> </ul> 
     * <ul> <li>If he threw dice as 1-5: <name> threw 1-5 and <name>’s score is 65.</li> </ul> 
     * <ul> <li>If he threw dice as 1-1: <name> threw 1-1. Game over <name>!</li> </ul> 
     * <ul> <li>If he skips his turn (0-0): <name> skipped the turn and <name>’s score is 65.</li> </ul> 
     * <ul> <li>If he is the last player standing: <name> is the winner of the game with the score of 65. Congratulations <name>!</li> </ul> </ul> 
     * 
     * @param data        An array of type String[] consisting of lines read from the input file
     * @param playerArray A list containing the players in the game, of type List<Player>
     * @param args        Arguments received from Terminal. It contains input and output files
     */
    public static void startGame(String[] data, List<Player> playerArray,String[] args)
    {
        int playerCounter = 0;
        for (int b = 0; b<(data.length-2);b++)
        {
            String[] diceData = data[b+2].split("-"); // Take dice values
            DoubleDice dice = new DoubleDice(Integer.parseInt(diceData[0]), Integer.parseInt(diceData[1])); // Throw dice
            int pointData = dice.findDicePoint(); // Calculate point of dice
            if(pointData == -1) // Game over statement
            {
                FileIO.writeFile(args[1], (String.format("%s threw 1-1. Game over %s!",
                playerArray.get(playerCounter).getName(),playerArray.get(playerCounter).getName())), 
                true, true); 
                playerArray.remove(playerCounter);
                playerCounter--;
            }
            else if(pointData == -2) // Skip statement
            {
                FileIO.writeFile(args[1],String.format("%s skipped the turn and %s’s score is %d.",
                playerArray.get(playerCounter).getName(),playerArray.get(playerCounter).getName(),
                playerArray.get(playerCounter).getPoint()),true,true);
            }
            else
            {
                playerArray.get(playerCounter).setPoint(pointData);
                FileIO.writeFile(args[1],String.format("%s threw %s and %s’s score is %d.",
                playerArray.get(playerCounter).getName(),data[b+2],playerArray.get(playerCounter).getName(),
                playerArray.get(playerCounter).getPoint()),true,true);
            }
            dice = null; 
            playerCounter = playerCounter >= playerArray.size()-1 ? 0:playerCounter+1;
        }
        
        FileIO.writeFile(args[1],String.format("%s is the winner of the game with the score of %d. Congratulations %s!",
        playerArray.get(playerCounter).getName(),playerArray.get(playerCounter).getPoint(),
        playerArray.get(playerCounter).getName()), true, false);
    }
}
