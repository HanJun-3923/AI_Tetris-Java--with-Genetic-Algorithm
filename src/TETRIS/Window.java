package TETRIS;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Window extends JFrame implements KeyListener {
    private static final int SCREEN_WIDTH = 1280;
    private static final int SCREEN_HEIGHT = 720;
    static GameBoard AI_MainBoard;
    static GameBoard AI_NextBoard;
    static GameBoard AI_HoldBoard;

    public Window() {
        setTitle("Tetris");
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        AI_MainBoard = new GameBoard();
        AI_MainBoard.widthPixel = 300;
        AI_MainBoard.heightPixel = 600;
        AI_MainBoard.coord = new Coord((SCREEN_WIDTH / 2) - (AI_MainBoard.widthPixel / 2), 30);

        JPanel paint = new Paint();
        setContentPane(paint);
        

        setVisible(true);
        paint.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }
}
 