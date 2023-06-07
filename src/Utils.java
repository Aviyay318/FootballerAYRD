import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class Utils {
    public static List<String> readFile() {
        String line;
        List<String> teams = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader((Constants.PATH_TO_DATA_FILE)));
            while ((line = bufferedReader.readLine()) !=null) {
                teams.add(line);
            }
        }catch (IOException e){
            System.out.println("can't read from the file");
        }
        return teams;
    }
    public static List<Team> createTeam(){
        List<String> teamsNames= readFile();
        return teamsNames.stream().map(Team::new).toList();
    }

    public static List<Player> createPlayers() {
       return Stream.generate((Player::new)).limit(15).toList();
    }


    public void sleep(int sleep){
        try {
            Thread.sleep(sleep);
        }catch (Exception e){
            e.getStackTrace();
        }
    }

}
