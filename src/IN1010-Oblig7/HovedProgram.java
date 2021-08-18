import sun.misc.BASE64Decoder;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageObserver;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class HovedProgram {


    public static void main(String[] args) {

        JFileChooser velger = new JFileChooser(".");
        int resultat = velger.showOpenDialog(null);
        if (resultat != JFileChooser.APPROVE_OPTION){
            System.exit(1);
        }
        File f = velger.getSelectedFile();

        JFrame vindu = new JFrame("Labyrint");
        vindu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //lat måte men fint ikon :)
        try {
            String base64Ikon ="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";
            byte[] bildeBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Ikon);
            vindu.setIconImage(ImageIO.read(new ByteArrayInputStream(bildeBytes)));
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            //jeg liker ikke swing styling

            Labyrint labyrint = new Labyrint(f);
            GUILabyrint gui = new GUILabyrint(labyrint);

            //wrapper som fikser problem med størrelse på rutene
            JPanel sizeWrapper = new JPanel(new FlowLayout());
            sizeWrapper.add(gui.getGuiGrid());

            //wrapper som gjøør labirynten scroll-bar
            JScrollPane scrollWrapper = new JScrollPane(sizeWrapper);
            scrollWrapper.getVerticalScrollBar().setUnitIncrement(16);
            scrollWrapper.getHorizontalScrollBar().setUnitIncrement(16);


            //wrapper som inneholder knappene
            JPanel controlsWrapper = new JPanel();
            controlsWrapper.setLayout(new FlowLayout());
            controlsWrapper.add(gui.getLeftButton());
            controlsWrapper.add(gui.getRightButton());

            //wrapper som inneholder knappene og teksten
            JPanel controlsAndLabelWrapper = new JPanel();
            controlsAndLabelWrapper.add(gui.getLabel());
            controlsAndLabelWrapper.add(controlsWrapper);

            //wrapper som fikser scroll-wrapperen
            JPanel wrapper = new JPanel(new BorderLayout());
            wrapper.add(controlsAndLabelWrapper, BorderLayout.NORTH);
            wrapper.add(scrollWrapper, BorderLayout.CENTER);

            vindu.setMinimumSize(new Dimension(500, 500));
            vindu.add(wrapper);
            vindu.pack();
            vindu.setVisible(true);
        } catch (FileNotFoundException e){
            System.exit(1);
        }

    }
}
