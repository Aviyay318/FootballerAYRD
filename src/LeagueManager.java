import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class LeagueManager {
    private List<Team> teams;
    private List<Match> matches;
    private List<Team> leagueTable;

    public LeagueManager() {
        this.teams = Utils.createTeam();
        matchTeams();

    }

    //1
    public List<Match> findMatchesByTeam(int teamId) {
        return this.matches.stream().filter(match -> match.isSameTeamById(teamId)).toList();
    }

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

    public List<Player> findPlayersWithAtLeastNGoals(int n) {
        Map<Player, Long> playerGoals = this.matches.stream().map(Match::getGoals).flatMap(List::stream).map(Goal::getScorer)
                .collect(groupingBy(player -> player, counting()));
        return playerGoals.entrySet().stream().filter(playerLongEntry -> playerLongEntry.getValue() >= n).map(Map.Entry::getKey).toList();
    }

    private Team getTeamByPosition(int position) {
        return this.leagueTable.get(position);
    }

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

    private void matchTeams() {
        this.matches = this.teams.stream().flatMap(homeTeam -> this.teams.stream().
                filter(awayTeam -> awayTeam.isIdBigger(homeTeam)).map(awayTeam -> new Match(homeTeam, awayTeam, createGoals(homeTeam, awayTeam)))).toList();
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
