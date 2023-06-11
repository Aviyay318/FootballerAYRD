import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {



    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //new Window();
        LeagueManager leagueManager = new LeagueManager();
        Random random = new Random();
        boolean gameOver = leagueManager.playGame(random, scanner);
        System.out.println(gameOver);


    }

}