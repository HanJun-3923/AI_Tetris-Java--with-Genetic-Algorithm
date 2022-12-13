package TETRIS;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

public class Paint extends JPanel {
    
    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);

        paintGrid(g);
    }   

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    private void paintGrid(Graphics g) { 
        g.setColor(Color.BLACK);

        // border line
        g.drawRect(Window.AI_MainBoard.coord.x, Window.AI_MainBoard.coord.y, Window.AI_MainBoard.widthPixel, Window.AI_MainBoard.heightPixel); 
        
        // Grid
        for(int i = 1; i < 10; i++) {
            g.drawLine(Window.AI_MainBoard.coord.x + i * (Window.AI_MainBoard.widthPixel / 10), Window.AI_MainBoard.coord.y, Window.AI_MainBoard.coord.x + i * (Window.AI_MainBoard.widthPixel / 10), Window.AI_MainBoard.coord.y + Window.AI_MainBoard.heightPixel);
        }
        for(int i = 1; i < 20; i++) {
            g.drawLine(Window.AI_MainBoard.coord.x, Window.AI_MainBoard.coord.y + i * (Window.AI_MainBoard.heightPixel / 20), Window.AI_MainBoard.coord.x + Window.AI_MainBoard.widthPixel, Window.AI_MainBoard.coord.y + i * (Window.AI_MainBoard.heightPixel / 20));
        }
    }
}
