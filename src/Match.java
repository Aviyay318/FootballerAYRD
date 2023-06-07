import java.util.List;

public class Match {
    private int id;
    private Team homeTeam;

    public List<Goal> getGoals() {
        return goals;
    }

    private Team awayTeam;
    private List<Goal> goals;


    public Match(Team homeTeam, Team awayTeam, List<Goal> goals) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.goals = goals;
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
                ", goals=" + goals +
                '}'+"\n";
    }
}
