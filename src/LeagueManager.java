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
        this.leagueTable = new ArrayList<>();
        //startGame();

    }
   private void createLeagueTable(){
       this.leagueTable=findTopScoringTeams(this.teams.size());
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


    public  boolean playGame (Random random, Scanner scanner) {
        if (this.matches.isEmpty()) {
            return true; // Game over condition
        }

        List<Match> newMatches = IntStream.range(0, 5)
                .mapToObj(i -> random.nextInt(this.matches.size()))
                .distinct()
                .map(this.matches::get)
                .collect(Collectors.toList());

        this.matches = this.matches.stream()
                .filter(match -> !newMatches.contains(match))
                .collect(Collectors.toList());

        startGame(newMatches);
        createLeagueTable();
        System.out.println(Constants.MENU);;
        System.out.println("Choose an option: ");
        int choice = getInputInRange(scanner, 1, 6);
        outcome(choice);
        return playGame(random, scanner);
    }

    private void outcome(int choice) {
        Scanner scanner = new Scanner(System.in);
        int userChoice;
        boolean endMenu =false;
       while (!endMenu){
           switch (getInputInRange(scanner,1,6)){
               case 1->{
                   System.out.println("enter an id of team you want");
                   userChoice = scanner.nextInt();
                   System.out.println(findMatchesByTeam(userChoice));
               }
               case 2->{
                   System.out.println("enter how much team you want: ");
                   userChoice = scanner.nextInt();
                   System.out.println(findTopScoringTeams(userChoice));
               }
               case 3->{
                   System.out.println("enter how much goals you want: ");
                   userChoice = scanner.nextInt();
                   System.out.println(findPlayersWithAtLeastNGoals(userChoice));
               }
               case 4->{
                   System.out.println("enter position of team you want: ");
                   userChoice = scanner.nextInt();
                   System.out.println(getTeamByPosition(userChoice));
               }
               case 5->{
                   System.out.println("enter position of scorers you want: ");
                   userChoice = scanner.nextInt();
                   System.out.println(getTopScorers(userChoice));
               }
               case 6->{
                   endMenu=true;
               }
           }
       }
    }


    public void startGame(List<Match> matches){
        Random random = new Random();
        int counter = 5;
        for (Match match : matches) {
            System.out.println("Round number: "+counter);
            counter--;
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






    public int getInputInRange(Scanner scanner, int min, int max) {
        return IntStream.generate(() -> {
                    System.out.println("Enter a number 1-6:");
                    return scanner.nextInt();
                })
                .filter(choice -> choice >= min && choice <= max)
                .findFirst()
                .orElseGet(() -> {
                    System.out.println("Invalid number");
                    return getInputInRange(scanner, min, max);
                });
    }

}
