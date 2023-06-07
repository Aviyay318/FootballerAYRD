import java.util.Random;

public class Goal {
    private int id;

    private static int counter=0;
    private int minute;


    private Player scorer;

    public Goal(Player scorer) {
        setGoalId();
        setGoalMinute();
        this.scorer = scorer;
    }
    public int getId() {
        return id;
    }

    public static int getCounter() {
        return counter;
    }

    public int getMinute() {
        return minute;
    }

    public Player getScorer() {
        return scorer;
    }
    private void setGoalMinute() {
        Random random = new Random();
        this.minute = random.nextInt(91);
    }

    private void setGoalId() {
        counter++;
        this.id=counter;
    }



    @Override
    public String toString() {
        return "Goal{" +
                "id=" + id +
                ", minute=" + minute +
                ", scorer=" + scorer +
                '}';
    }
}
