import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class LeagueManager {
    private List<Team> teams;
    private List<Match> matches;
    private List<Team> leagueTable;

    public LeagueManager() {
        this.teams = Utils.createTeam();
        this.matches = matchTeams();
        startGame();

    }

    //1
    public List<Match> findMatchesByTeam(int teamId) {
        return this.matches.stream().filter(match -> match.isSameTeamById(teamId)).toList();
    }
    //2
    public List<Team> findTopScoringTeams(int n) {
        return teams.stream()
                .collect(Collectors.toMap(team -> team, team -> matches.stream()
                        .flatMap(match -> match.getGoals().stream())
                        .map(Goal::getScorer)
                        .filter(team.getPlayers()::contains)
                        .count()))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(n)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
    //3
    public List<Player> findPlayersWithAtLeastNGoals(int n) {
        Map<Player, Long> playerGoals = this.matches.stream().map(Match::getGoals).flatMap(List::stream).map(Goal::getScorer)
                .collect(groupingBy(player -> player, counting()));
        return playerGoals.entrySet().stream().filter(playerLongEntry -> playerLongEntry.getValue() >= n).map(Map.Entry::getKey).toList();
    }
    //4
    private Team getTeamByPosition(int position) {
        return this.leagueTable.get(position);
    }
    //5
    public Map<Integer, Integer> getTopScorers(int n) {
            Map<Integer, Long> playerGoals = matches.stream()
                    .flatMap(match -> match.getGoals().stream())
                    .map(Goal::getScorer)
                    .collect(Collectors.groupingBy(Player::getPlayerId, Collectors.counting()));

            return playerGoals.entrySet().stream()
                    .sorted(Map.Entry.<Integer, Long>comparingByValue().reversed())
                    .limit(n)
                    .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().intValue()));

    }

//    כל משחק יימשך 10 שניות. בתחילה יודפס על המסך מיהן הקבוצות המתמודדות, ולאחר מכן ספירה לאחור של 10 שניות, שבסיומה תוצאת המשחק תקבע באופן אקראי.
//    התוצאות יאוחסנו באובייקט מהמחלקה Match.
//    כל שער שיובקע יתועד יחד עם הדקה שבה הובקע השער והשחקן שכבש את השער. מיד לאחר הגרלת התוצאה, התוצאה תוצג על המסך.



    public void startGame(){
        Random random = new Random();

        for (Match match : this.matches) {
            System.out.println("Match between " + match.getHomeTeam().getName() + " and " + match.getAwayTeam().getName());
            IntStream.iterate(10, i -> i - 1)
                    .limit(10)
                    .forEach(num -> {
                        System.out.println(num);
            Utils.sleep(1000);
                    });
            int homeTeamScore = random.nextInt(4);
            int awayTeamScore = random.nextInt(4);
            match.setGoals(homeTeamScore, awayTeamScore);

            System.out.println("Match result: " + match.getHomeTeam().getName() + " " + homeTeamScore + " - " + awayTeamScore + " " + match.getAwayTeam().getName());
            System.out.println("Goals:");

            for (Goal goal : match.getGoals()) {
                Player scorer = goal.getScorer();
                int minute = goal.getMinute();
                System.out.println(scorer.getName() + " scored in minute " + minute);
            }

            System.out.println();
        }



    }

    public  List<Match> matchTeams() {
        return this.teams.stream()
                .flatMap(homeTeam ->
                        this.teams.stream()
                                .filter(awayTeam -> awayTeam.isIdBigger(homeTeam))
                                .map(awayTeam -> new Match(homeTeam, awayTeam)))
                .collect(Collectors.toList());
    }



    private List<Goal> createGoals(Team homeTeam, Team awayTeam) {
        Random random = new Random();
        List<Player> players = new ArrayList<>(homeTeam.getPlayers());
        players.addAll(awayTeam.getPlayers());
        return Stream.generate(() -> new Goal(players.get(random.nextInt(players.size())))).limit(5).toList();
    }


    public Match play() {
        return null;
    }

}
