package Main;
import javax.swing.*;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Container;
import java.awt.Point;


public class Window implements KeyListener {
    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 720;
    public static final int BLOCK_SIZE = 30;

    public static GameBoard AI_MainBoard = new GameBoard();
    
    public static JFrame mainFrame = new JFrame();
    public static Paint paint = new Paint();

    public static AI.AI_Tetris ai;

    public Window(AI.AI_Tetris ai) {
        this.ai = ai;
        Container contentPane = mainFrame.getContentPane();

        mainFrame.setTitle("Tetris");
        mainFrame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        mainFrame.setResizable(true);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(null);

        AI_MainBoard.widthPixel = AI_MainBoard.widthInt * BLOCK_SIZE;
        AI_MainBoard.heightPixel = AI_MainBoard.heightInt * BLOCK_SIZE;
        AI_MainBoard.coord = new Coord((Paint.WIDTH / 2) - (AI_MainBoard.widthPixel / 2), 0);
        
        paint.setAI(ai);

        contentPane.add(paint);
        Point paintPanelPoint = new Point((SCREEN_WIDTH / 2) - (Paint.WIDTH / 2), (SCREEN_HEIGHT / 2) - (Paint.HEIGHT / 2));
        paint.setLocation(paintPanelPoint);


        

        mainFrame.setVisible(true);
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
 