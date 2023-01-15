package AI;

import TETRIS.BlockShape;
import TETRIS.CrntBlock;
import TETRIS.Position;
import TETRIS.Table;
import Main.Window;

public class Rule {
    Table[][] mainTable;
    CrntBlock crntBlock;
    AI_Tetris ai = Window.ai;
    Weight weight = new Weight();
    
    Rule(Table[][] mainTable, CrntBlock crntBlock, Weight weight) {
        this.mainTable = mainTable;
        this.crntBlock = crntBlock;
        this.weight = weight;
    }

    public double getCost() {
        double resultCost = 0;
        resultCost += heightCost();
        resultCost += doMakeHole();

        return resultCost;
    }

    private double heightCost() {
        final double heightWeight = weight.heightWeight;
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
        final double doMakeHole = weight.doMakeHoleWeight;
        int deepOfHole = 0;
        double cost = 0;
        for(int r = 0; r < 4; r++) {
            for(int c = 0; c < 4; c++) {
                if(crntBlock.blockArray[r][c] != BlockShape.NONE && (r == 3 || crntBlock.blockArray[r + 1][c] == BlockShape.NONE)) {
                    while(true) {
                        Position testPos = new Position(crntBlock.position.r + r + deepOfHole + 1, crntBlock.position.c + c);
                        if(ai.getMainTableBlockType(testPos) == BlockShape.NONE) {
                            deepOfHole++;
                        }
                        else {
                            cost += (doMakeHole * deepOfHole);
                            deepOfHole = 0;
                            break;
                        }
                    }
                }
            }
        }

        return cost;
    }

}
