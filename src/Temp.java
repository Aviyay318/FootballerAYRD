//public class Temp {
//    import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.List;
//import java.util.Random;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//    class Team {
//        private String name;
//
//        public Team(String name) {
//            this.name = name;
//        }
//
//        public String getName() {
//            return name;
//        }
//    }
//
//    class Match {
//        private Team homeTeam;
//        private Team awayTeam;
//
//        public Match(Team homeTeam, Team awayTeam) {
//            this.homeTeam = homeTeam;
//            this.awayTeam = awayTeam;
//        }
//
//        public Team getHomeTeam() {
//            return homeTeam;
//        }
//
//        public Team getAwayTeam() {
//            return awayTeam;
//        }
//    }
//
//    class MatchResult {
//        private Match match;
//        private int homeTeamScore;
//        private int awayTeamScore;
//
//        public MatchResult(Match match, int homeTeamScore, int awayTeamScore) {
//            this.match = match;
//            this.homeTeamScore = homeTeamScore;
//            this.awayTeamScore = awayTeamScore;
//        }
//
//        public Match getMatch() {
//            return match;
//        }
//
//        public int getHomeTeamScore() {
//            return homeTeamScore;
//        }
//
//        public int getAwayTeamScore() {
//            return awayTeamScore;
//        }
//
//        public String getResult() {
//            return match.getHomeTeam().getName() + " " + homeTeamScore + " - " +
//                    awayTeamScore + " " + match.getAwayTeam().getName();
//        }
//    }
//
//    class TeamStanding {
//        private Team team;
//        private int points;
//        private int goalDifference;
//
//        public TeamStanding(Team team, int points, int goalDifference) {
//            this.team = team;
//            this.points = points;
//            this.goalDifference = goalDifference;
//        }
//
//        public Team getTeam() {
//            return team;
//        }
//
//        public int getPoints() {
//            return points;
//        }
//
//        public int getGoalDifference() {
//            return goalDifference;
//        }
//    }
//
//    class LeagueManager {
//        private List<Team> teams;
//        private List<Match> matches;
//        private List<MatchResult> results;
//
//        public LeagueManager(List<Team> teams) {
//            this.teams = teams;
//            this.matches = generateMatches();
//            this.results = new ArrayList<>();
//        }
//
//        public void runLeague() throws InterruptedException {
//            for (int round = 1; round <= 9; round++) {
//                playRound(round);
//                printLeagueStandings();
//                Thread.sleep(1000);
//            }
//        }
//
//        private void playRound(int round) {
//            System.out.println("מחזור " + round + ":");
//
//            for (Match match : matches) {
//                int homeTeamScore = generateRandomScore();
//                int awayTeamScore = generateRandomScore();
//                MatchResult result = new MatchResult(match, homeTeamScore, awayTeamScore);
//                results.add(result);
//
//                System.out.println(result.getResult());
//                Thread.sleep(1000);
//            }
//
//            System.out.println("------------------");
//        }
//
//        private void printLeagueStandings() {
//            List<TeamStanding> standings = teams.stream()
//                    .map(team -> calculateTeamStanding(team, results))
//                    .sorted(Comparator.comparingInt(TeamStanding::getPoints)
//                            .reversed()
//                            .thenComparingInt(TeamStanding::getGoalDifference)
//                            .thenComparing(TeamStanding::getTeam, Comparator.comparing(Team::getName)))
//                    .collect(Collectors.toList());
//
//            int position = 1;
//            for (TeamStanding teamStanding : standings) {
//                System.out.println(position + ". " + teamStanding.getTeam().getName() +
//                        " - Points: " + teamStanding.getPoints() +
//                        ", Goal Difference: " + teamStanding.getGoalDifference());
//                position++;
//            }
//        }
//
//        private List<Match> generateMatches() {
//            return teams.stream()
//                    .flatMap(homeTeam ->
//                            teams.stream()
//                                    .filter(awayTeam -> !awayTeam.equals(homeTeam))
//                                    .map(awayTeam -> new Match(homeTeam, awayTeam)))
//                    .collect(Collectors.toList());
//        }
//
//        private int generateRandomScore() {
//            return new Random().nextInt(6); // Generates a random score between 0 and 5
//        }
//
//        private TeamStanding calculateTeamStanding(Team team, List<MatchResult> results) {
//            int points = results.stream()
//                    .filter(result -> result.getMatch().getHomeTeam().equals(team) || result.getMatch().getAwayTeam().equals(team))
//                    .mapToInt(result -> {
//                        if (result.getHomeTeamScore() == result.getAwayTeamScore()) {
//                            return 1; // Draw
//                        } else if ((result.getHomeTeamScore() > result.getAwayTeamScore() && result.getMatch().getHomeTeam().equals(team)) ||
//                                (result.getAwayTeamScore() > result.getHomeTeamScore() && result.getMatch().getAwayTeam().equals(team))) {
//                            return 3; // Win
//                        } else {
//                            return 0; // Loss
//                        }
//                    })
//                    .sum();
//
//            int goalDifference = results.stream()
//                    .filter(result -> result.getMatch().getHomeTeam().equals(team) || result.getMatch().getAwayTeam().equals(team))
//                    .mapToInt(result -> {
//                        if (result.getMatch().getHomeTeam().equals(team)) {
//                            return result.getHomeTeamScore() - result.getAwayTeamScore();
//                        } else {
//                            return result.getAwayTeamScore() - result.getHomeTeamScore();
//                        }
//                    })
//                    .sum();
//
//            return new TeamStanding(team, points, goalDifference);
//        }
//    }
//
//    public class Main {
//        public static void main(String[] args) throws InterruptedException {
//            List<Team> teams = IntStream.rangeClosed(1, 9)
//                    .mapToObj(i -> new Team("קבוצה " + i))
//                    .collect(Collectors.toList());
//
//            LeagueManager leagueManager = new LeagueManager(teams);
//            leagueManager.runLeague();
//        }
//    }
//
//}
