package AI;

import TETRIS.BlockShape;
import TETRIS.Position;
import TETRIS.Table;
import TETRIS.CrntBlock;

import Main.Main;
import Main.Window;

public class Rule extends AI_Tetris {
    CrntBlock crntBlock = Main.AI.crntBlock;
    Table[][] mainTable = Main.AI.mainTable;
    public double getCost() {
        double resultCost = 0;
        resultCost += heightCost();
        resultCost += doMakeHole();

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
    private double doMakeHole() {
        final double doMakeHole = 50;
        int deepOfHole = 0;
        double cost = 0;
        for(int r = 0; r < 4; r++) {
            for(int c = 0; c < 4; c++) {
                if(crntBlock.blockArray[r][c] != BlockShape.NONE) {
                    while(true) {
                        Position testPos = new Position(crntBlock.position.r + r + deepOfHole + 1, crntBlock.position.c + c);
                        if(getMainTableBlockType(testPos) == BlockShape.LIQUID) {
                            deepOfHole++;
                        } else break;
                    }
                }
            }
        }
        cost += doMakeHole * deepOfHole;
        return cost;
    }

}
