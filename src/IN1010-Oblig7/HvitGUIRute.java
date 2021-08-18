import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

public class HvitGUIRute extends JButton implements ActionListener {
    GUILabyrint labyrint;
    Tuppel coords;
    StepDrawing drawingHandler = null;


    public HvitGUIRute(HvitRute x, GUILabyrint labyrint) {
        addActionListener(this);
        this.labyrint = labyrint;
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(50, 50));
        setMaximumSize(new Dimension(50, 50));

        coords = x.getCoords();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (drawingHandler != null){
            AffineTransform oldTransform = g2.getTransform();
            drawingHandler.draw(g2);
            g2.setTransform(oldTransform);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        labyrint.finnUtveiFra(coords.getX(), coords.getY());
    }

    public void removeDrawingHandler() {
        this.drawingHandler = null;
    }
    public void setDrawingHandler(StepDrawing drawingHandler){
        this.drawingHandler = drawingHandler;
    }
}
