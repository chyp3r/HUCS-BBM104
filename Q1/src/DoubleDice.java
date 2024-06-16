public class DoubleDice {
    private int number1;
    private int number2;

    public void setNumber1(int number){this.number1 = number;}
    public void setNumber2(int number){this.number2 = number;}

    public int getNumber1(){return this.number1;}
    public int getNumber2(){return this.number2;}

    public DoubleDice(int number1,int number2)
    {
        this.number1 = number1;
        this.number2 = number2;
    }

    /**
     * Checks the dice status and returns the points earned if there is no special condition.
     * 
     * @return -1, -2, 0 or point
     * <ul> <li>-1: Game over status for the relevant player. If thrown 1-1 the player is eliminated</ul> 
     * <ul> <li>-2: Skip status for the relevant player. In case of a 0-0 throw the player skips his round.</li></ul> 
     * <ul> <li>0: 0 standings for relevant player. If a 1-x or x-1 is thrown, the player gets 0 points. </li> </ul> 
     * <ul> <li>point: Points earned if no special situation occurs return</li></ul>  
     */
    public int findDicePoint()
    {
        if(this.number1 == 1 && this.number2 == 1)
            return -1;

        else if(this.number1 == 0 && this.number2 == 0)
            return -2;

        else if(this.number1 == 1 || this.number2 == 1)
            return 0;
            
        else
            return this.number1 + this.number2;
    }
}
