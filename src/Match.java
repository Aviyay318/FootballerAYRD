import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Match {
    private int id;
    private Team homeTeam;

    public List<Goal> getGoals() {
        return goals;
    }

    private Team awayTeam;
    private List<Goal> goals;

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public void setGoals(int homeTeamGoals,int awayTeamGoals) {
       Random random = new Random();
            this.goals.addAll(generateGoals(homeTeamGoals, this.homeTeam.getPlayers(), random));
            this.goals.addAll(generateGoals(awayTeamGoals, this.awayTeam.getPlayers(), random));
        }

        private List<Goal> generateGoals(int numGoals, List<Player> players, Random random) {
            return IntStream.range(0, numGoals)
                    .mapToObj(i -> new Goal(players.get(random.nextInt(players.size()))))
                    .toList();
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public Match(Team homeTeam, Team awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.goals = new ArrayList<>();
    }

    public boolean isSameTeamById(int id){
        boolean isSameTeam = false;
        if (this.homeTeam.checkId(id)||this.awayTeam.checkId(id)){
            isSameTeam=true;
        }
        return isSameTeam;
    }


    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", homeTeam=" + homeTeam +
                ", awayTeam=" + awayTeam +
                ", goals=" + this.goals +
                '}'+"\n";
    }
}
