import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Team  {
    private int id;
    private static int counter=0;

    public String getName() {
        return name;
    }

    private String name;
    private List<Player> players;
    private BufferedImage teamIcon;

    public Team(String name) {
        setTeamId();
        this.name = name;
        this.players = Utils.createPlayers();
        drawImage();
    }

    private void drawImage() {
       try {
           this.teamIcon = ImageIO.read(new File(Constants.TEAMS_ICONS[this.id-1]));
       }catch (IOException e){
           e.printStackTrace();
       }
    }
    public boolean isSameTeam(Team team){
        boolean isSameTeam = false;
        if (this.id==team.id){
            isSameTeam=true;
        }
        return isSameTeam;
    }


    private void setTeamId() {
        counter++;
        this.id=counter;

    }
    public boolean checkId(int id){
        boolean isIdentical = false;
        if (this.id==id){
            isIdentical = true;
        }
        return isIdentical;
    }
    public boolean isIdBigger(Team team){
        boolean isIdBigger = false;
        if (this.id>team.id){
            isIdBigger = true;
        }
        return isIdBigger;
    }

    public List<Player> getPlayers() {
        return players;
    }

    @Override
    public String toString() {
       return  "Id: "+this.id+" "+"Team Name: " + name +"\n";
    }


}
