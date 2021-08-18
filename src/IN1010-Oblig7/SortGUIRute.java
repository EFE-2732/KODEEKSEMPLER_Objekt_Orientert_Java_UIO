import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SortGUIRute extends JButton{
    GUILabyrint labyrint;
    Tuppel coords;

    public SortGUIRute(SortRute x, GUILabyrint labyrint) {
        this.labyrint = labyrint;
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(50, 50));
        setMaximumSize(new Dimension(50, 50));

        setEnabled(false);
        coords = x.getCoords();
    }
}
