package Main;
import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Color;
import TETRIS.BlockShape;

public class Paint extends JPanel {
    
    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);
        paintBlock(g);
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

    private void paintBlock(Graphics g) {
        for (int r = 0; r < Window.AI_MainBoard.heightInt; r++) {
            for (int c = 0; c < Window.AI_MainBoard.widthInt; c++) {
                if(Main.AI.mainTable[r][c].isVisible) { // 블럭이 존재한다면
                    setColorAccordingToMino(g, Main.AI.mainTable[r][c].mino); // Set Graphcis Color according to Table's mino
                } else { // 블럭이 존재하지 않는다면
                    g.setColor(Color.WHITE);
                }
                g.fillRect(Window.AI_MainBoard.coord.x + Window.BLOCK_SIZE * c, Window.AI_MainBoard.coord.y + Window.BLOCK_SIZE * r, Window.BLOCK_SIZE, Window.BLOCK_SIZE);
            }
        }
    }

    // Set Graphcis Color according to Table's mino
    private void setColorAccordingToMino(Graphics g, BlockShape mino) {
        // Color Profile from Jstris

        //LIQUID or SOLID
        if(mino == BlockShape.I || mino == BlockShape.SLD_I) {
            g.setColor(new Color(71, 153, 210)); // RGB Color SkyBlue
        } else if (mino == BlockShape.T || mino == BlockShape.SLD_T) { 
            g.setColor(new Color(161, 53, 134)); // RGB Color Purple
        } else if (mino == BlockShape.O || mino == BlockShape.SLD_O) { 
            g.setColor(new Color(217, 162, 55)); // RGB Color Yellow
        } else if (mino == BlockShape.S || mino == BlockShape.SLD_S) { 
            g.setColor(new Color(111, 175, 52)); // RGB Color Green
        } else if (mino == BlockShape.Z || mino == BlockShape.SLD_Z) { 
            g.setColor(new Color(144, 34, 44)); // RGB Color Red
        } else if (mino == BlockShape.L || mino == BlockShape.SLD_L) { 
            g.setColor(new Color(211, 100, 40)); // RGB Color Orange
        } else if (mino == BlockShape.J || mino == BlockShape.SLD_J) {
            g.setColor(new Color(29, 45, 133)); // RGB Color Blue
        } 
    }
}
