import java.util.Random;

public class Player{
    private int id;
    private String firstName;
    private String lastName;
    private static int counter=0;

    public Player() {
        setPlayerId();
        setPlayerName();
    }

    private void setPlayerId() {
        counter++;
        this.id=counter;
    }
   public String getName(){
        return this.firstName+" "+this.lastName;
   }
    private void setPlayerName() {
        Random random = new Random();
        this.firstName = Constants.FIRST_NAMES[random.nextInt(0,32)];
        this.lastName = Constants.LAST_NAMES[random.nextInt(0,32)];
    }

    public int getPlayerId(){
        return this.id;
    }
    @Override
    public String toString() {
               return "Id: "+this.id+" "+"First Name: " + firstName + "\n"+ "Last Name: " + lastName;
    }
}
