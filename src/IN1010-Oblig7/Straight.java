import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Straight extends StepDrawing {
    public Straight(int dir) throws IOException {
        super(dir);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.rotate(Math.toRadians(dir*90), 25.5, 25.5);
        g2.drawImage(hoofImg, 6, 4, 13, 13, null);
        g2.drawImage(hoofImg, 31, 29, 13, 13, null);


    }
}
