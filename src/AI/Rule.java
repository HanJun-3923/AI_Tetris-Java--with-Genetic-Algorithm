package AI;

import TETRIS.BlockShape;
import TETRIS.Position;
import Main.Window;

public class Rule extends AI_Tetris {

    public double getCost() {
        double resultCost = 0;
        resultCost += heightCost();

        return resultCost;
    }

    public Position getPosition() {
        Position position = new Position(0, 0);
        position.r = super.crntBlock.position.r;
        position.c = super.crntBlock.position.c;
        // don't share its own address

        return position;
    }
    private double heightCost() {
        final double heightWeight = 1;
        double cost = 0;
        for(int r = 0; r < 4; r++) {
            for(int c = 0; c < 4; c++) {
                if(super.crntBlock.blockArray[r][c] != BlockShape.NONE) {
                    double height = Window.AI_MainBoard.heightInt - (super.crntBlock.position.r + r); // 1 ~ 20
                    cost += heightWeight * height;
                }
            }
        }
        return cost;
    }
}
