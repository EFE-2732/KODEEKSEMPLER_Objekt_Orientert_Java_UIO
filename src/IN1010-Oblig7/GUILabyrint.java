import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;

public class GUILabyrint {
    private JPanel guiGrid;
    private Labyrint labyrint;
    private ArrayList<ArrayList<Tuppel>> stier = null;
    //fordi getComponents ikke er thread safe?
    private ArrayList<JButton> compList = new ArrayList<>();
    private BasicArrowButton left;
    private BasicArrowButton right;
    private JLabel label;
    private int current = 0;

    public GUILabyrint(Labyrint labyrint) {
        this.labyrint = labyrint;

        //teksten med informasjon om løsningen.
        label = new JLabel();

        //den grafiske representasjonen av labyrinten
        guiGrid = new JPanel();
        guiGrid.setLayout(new GridLayout(labyrint.getYmax(), labyrint.getXmax()));
        guiGrid.setPreferredSize(new Dimension(50 * labyrint.getXmax(), 50 * labyrint.getYmax()));
        guiGrid.setMaximumSize(new Dimension(50 * labyrint.getXmax(), 50 * labyrint.getYmax()));

        //knappen som tegner forrige løsning
        left = new BasicArrowButton(BasicArrowButton.WEST){
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(100, 100);
            }
        };
        left.addActionListener(new AbstractAction("Forrige løsninge") {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUILabyrint.this.tegnForrige();
            }
        });


        //knappen som tegner neste løsning
        right = new BasicArrowButton(BasicArrowButton.EAST){
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(100, 100);
            }
        };
        right.addActionListener(new AbstractAction("Neste løsning") {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUILabyrint.this.tegnNeste();
            }
        });

        //tegner alle rutene
        for (Rute[] y : labyrint.getGrid()) {
            for (Rute x : y) {
                if (x instanceof HvitRute) {
                    HvitGUIRute tmp = new HvitGUIRute((HvitRute) x, this);
                    compList.add(tmp);
                    guiGrid.add(tmp);
                } else if (x instanceof SortRute) {
                    SortGUIRute tmp = new SortGUIRute((SortRute) x, this);
                    compList.add(tmp);
                    guiGrid.add(tmp);
                }
            }
        }
    }

    //returnerer den grafiske representasjonen av labyrinten
    public JPanel getGuiGrid() {
        return guiGrid;
    }

    //returnerer teksten med informasjon om løsningen
    public JLabel getLabel(){
        return label;
    }

    //fjerner tegning av løsning
    ArrayList<Tuppel> gammelSti;
    private void clearDrawing(){
        if (gammelSti != null){
            for (Tuppel coords : gammelSti) {
                HvitGUIRute comp = (HvitGUIRute) compList.get(coords.getY() * labyrint.getXmax() + coords.getX());
                comp.removeDrawingHandler();
            }
        }
    }

    //tegner løsning
    public void drawPath(ArrayList<Tuppel> sti) {
        clearDrawing();

        gammelSti = sti;

        if (sti != null) {
            try {
                //sjekker retning på trinn
                for (int stepIndex = 0; stepIndex < sti.size() - 1; stepIndex++) {
                    int step1Dir = stepDirection(sti.get(stepIndex), sti.get(stepIndex + 1));
                    Tuppel coords = sti.get(stepIndex);
                    HvitGUIRute comp = (HvitGUIRute) compList.get(coords.getY() * labyrint.getXmax() + coords.getX());
                    if (stepIndex <= sti.size() - 2 && stepIndex >= 1) {
                        int step2Dir = Math.floorMod(stepDirection(sti.get(stepIndex - 1), sti.get(stepIndex)) - step1Dir, 4);
                        System.out.println(step2Dir);
                        if (step2Dir == 3) {
                            comp.setDrawingHandler(new RightTurn(step1Dir));
                        } else if (step2Dir == 1) {
                            comp.setDrawingHandler(new LeftTurn(step1Dir));
                        } else {
                            comp.setDrawingHandler(new Straight(step1Dir));
                        }

                    } else {
                        comp.setDrawingHandler(new Straight(step1Dir));
                    }

                }

                //tegner siste steg.
                Tuppel coords = sti.get(sti.size() - 1);
                HvitGUIRute comp = (HvitGUIRute) compList.get(coords.getY() * labyrint.getXmax() + coords.getX());
                if (sti.get(sti.size() - 1).getX() == 0) {
                    comp.setDrawingHandler(new Straight(3));
                } else if (sti.get(sti.size() - 1).getY() == 0) {
                    comp.setDrawingHandler(new Straight(0));
                } else if (sti.get(sti.size() - 1).getY() == labyrint.getYmax() - 1) {
                    comp.setDrawingHandler(new Straight(2));
                } else if (sti.get(sti.size() - 1).getX() == labyrint.getXmax() - 1) {
                    comp.setDrawingHandler(new Straight(1));
                }
                getGuiGrid().repaint();
            } catch (IOException e){
                System.exit(1);
            }
        }
    }

    //tegner neste løsning
    public void tegnNeste() {
        if (stier != null && stier.size()>0) {
            current = Math.floorMod(current + 1, stier.size());
            setLabel("<html>Utvei "+(current+1)+" av "+stier.size()+"<br>"+stier.get(current).size()+" trinn</html>");
            drawPath(stier.get(current));
        }
    }

    //tegner forrige løsning
    public void tegnForrige() {
        if (stier != null && stier.size()>0) {
            current = Math.floorMod(current - 1, stier.size());
            setLabel("<html>Utvei "+(current+1)+" av "+stier.size()+"<br>"+stier.get(current).size()+" trinn</html>");
            drawPath(stier.get(current));
        }
    }

    //finner løsning, tegner løsning og skriver tekst
    public void finnUtveiFra(int x, int y) {
        stier = labyrint.finnUtveiFra(x, y);
        current = 0;
        if (stier.size() > 0) {
            setLabel("<html>Utvei 1 av "+stier.size()+"<br>"+stier.get(current).size()+" trinn</html>");
            drawPath(stier.get(0));
        } else {
            setLabel("");
            clearDrawing();
            getGuiGrid().repaint();
        }
    }

    //setter teksten.
    private void setLabel(String text){
        label.setText(text);
        label.repaint();
    }

    //regner ut retningen fra step1 til step2
    private static int stepDirection(Tuppel step1, Tuppel step2) {
        if (step1.getX() != step2.getX()) {
            return Math.floorMod((step2.getX() - step1.getX()), 4);
        } else {
            return step2.getY() - step1.getY() + 1;
        }
    }

    //returnerer knapp som tegner forrige løsning
    public BasicArrowButton getLeftButton() {
        return left;
    }

    //returnerer knapp som tegner neste løsning
    public BasicArrowButton getRightButton(){
        return right;
    }
}


