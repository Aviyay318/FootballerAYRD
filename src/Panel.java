import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Panel extends JPanel{
    private JButton startButton;
    private JLabel timer;
    private Font myFont;
    private JLabel homeTeam;
    private JLabel awayTeam;
    private JLabel gameOver;
    private BufferedImage backGround;
    private BufferedImage versusLogo;
    private int counter;
    private BufferedImage barcelona;
    private BufferedImage real;

    public Panel(int x, int y, int width, int height) {
        this.myFont = createTimerFont().deriveFont(Font.BOLD, 36f);
        this.setBounds(x,y,width,height);
        this.setLayout(null);
        createTimerFont();
        createTimerLabel();
        createGameOverLabel();
        createHomeTeam();
        createAwayTeam();
        readImages();
        createStartButton();

    }

    private void createTimerLabel(){
        this.timer=new JLabel("", JLabel.CENTER);
        this.timer.setBounds(Constants.LABEL_X, Constants.LABEL_Y,Constants.TIMER_WIDTH, Constants.TIMER_HEIGHT);
        this.timer.setFont(this.myFont.deriveFont(Font.BOLD,100f));
        this.add(this.timer);
        this.timer.setVisible(false);
    }

    private void createGameOverLabel(){
        this.gameOver=new JLabel("", JLabel.CENTER);
        this.gameOver.setBounds(Constants.LABEL_X, Constants.LABEL_Y,Constants.GAME_OVER_WIDTH,Constants.GAME_OVER_HEIGHT);
        this.gameOver.setFont(this.myFont.deriveFont(Font.BOLD,300f));
        this.add(this.gameOver);
        this.gameOver.setVisible(false);
    }

    private void createHomeTeam(){
        this.homeTeam=new JLabel("Home Team", JLabel.CENTER);
        this.homeTeam.setBounds(95,400,300,60);
        this.homeTeam.setFont(this.myFont);
        this.add(this.homeTeam);
        this.homeTeam.setVisible(false);
    }

    private void createAwayTeam(){
        this.awayTeam=new JLabel("Away Team", JLabel.CENTER);
        this.awayTeam.setBounds(775,400,300,60);
        this.awayTeam.setFont(this.myFont);
        this.add(this.awayTeam);
        this.awayTeam.setVisible(false);
    }

    private Font createTimerFont(){
        Font myFont;
        try {
            myFont = Font.createFont(Font.TRUETYPE_FONT, new File("res/Act_Of_Rejection.ttf"));
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
        return myFont;
    }

    public void paintComponent (Graphics graphics){
        super.paintComponent(graphics);
        graphics.drawImage(this.backGround,0,0,Constants.WIDTH,Constants.HEIGHT,null);
        graphics.drawImage(this.versusLogo,Constants.VERSUS_X+Constants.VERSUS_SPACING,Constants.VERSUS_Y,null);
        graphics.drawImage(this.real,this.homeTeam.getX(),this.homeTeam.getY()-100,300,200,null);
        graphics.drawImage(this.barcelona,this.awayTeam.getX(),this.awayTeam.getY()-100,300,200,null);
    }

    public void readImages(){
        try {
            this.barcelona = ImageIO.read(new File("res/teams/barcelona.png"));
            this.real = ImageIO.read(new File("res/teams/real.png"));

            this.backGround = ImageIO.read(new File(Constants.BACK_GROUND));
            this.versusLogo = ImageIO.read(new File(Constants.VERSUS_LOGO));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setTimer() {
        new Thread(()->{
            this.counter = 10;
            while (this.counter >= 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                if(counter>9){
                    this.timer.setBounds(555, 100,120,120);
                } else{
                    this.timer.setBounds(550, 100,120,120);

                }
                this.timer.setText(Integer.toString(this.counter));
                this.counter--;
            }
            if (this.counter==-1){
                this.timer.setVisible(false);
                this.gameOver.setVisible(true);
            }
        }).start();
    }

    private void createStartButton() {
        this.startButton=new JButton("Start!");
        this.startButton.setBounds(Constants.START_BUTTON_X, Constants.START_BUTTON_Y, Constants.START_BUTTON_WIDTH,Constants.START_BUTTON_HEIGHT);
        this.startButton.setFont(this.myFont);
        this.startButton.setVisible(true);
        this.add(this.startButton);
        this.startButton.addActionListener(e->{
            this.startButton.setVisible(false);
            this.timer.setVisible(true);
            this.homeTeam.setVisible(true);
            this.awayTeam.setVisible(true);
            setTimer();
        });
    }
}
