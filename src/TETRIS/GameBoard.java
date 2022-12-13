package TETRIS;

import javax.swing.JPanel;

public class GameBoard extends JPanel {
    public Coord coord;
    public int widthPixel;
    public int heightPixel;
    public int widthInt = 10;
    public int heightInt = 20;


    public Table[][] table = new Table[widthInt][heightInt];

}
