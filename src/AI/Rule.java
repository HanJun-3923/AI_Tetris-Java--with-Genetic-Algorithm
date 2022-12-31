package AI;

import TETRIS.BlockShape;
import TETRIS.CrntBlock;
import Main.Main;
import Main.Window;

public class Rule {
    CrntBlock crntBlock = Main.AI.crntBlock;

    public double getCost() {
        double resultCost = 0;
        resultCost += heightCost();

        return resultCost;
    }

    private double heightCost() {
        final double heightWeight = 1;
        double cost = 0;
        for(int r = 0; r < 4; r++) {
            for(int c = 0; c < 4; c++) {
                if(crntBlock.blockArray[r][c] != BlockShape.NONE) {
                    double height = Window.AI_MainBoard.heightInt - (crntBlock.position.r + r); // 1 ~ 20
                    cost += heightWeight * height;
                }
            }
        }
        return cost;
    }
    
}
