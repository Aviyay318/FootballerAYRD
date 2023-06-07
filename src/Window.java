import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Window extends JFrame {
    private Image icon;


    public Window() {
        this.setSize(Constants.WIDTH, Constants.HEIGHT);
        this.setLayout(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Football!");
        this.setLocationRelativeTo(null);
        setIcon();
        this.setIconImage(this.icon);
        this.add(new Panel(0,0,Constants.WIDTH,Constants.HEIGHT));
        this.setVisible(true);
    }



    private void setIcon () {
        try {
            this.icon= ImageIO.read(new File("res/ayrdIcon.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
