package AI;

import Main.Window;
import TETRIS.BlockShape;
import TETRIS.CrntBlock;
import TETRIS.Direction;
import TETRIS.NextBlock;
import TETRIS.Position;
import TETRIS.Table;

import java.util.Vector;
import java.util.Collections;
import java.util.Comparator;
import java.util.Timer;
import java.util.TimerTask;


public class AI_Tetris {
    public class InformationBasedLocation {
        Position position;
        double cost;
        InformationBasedLocation() {
            position = new Position(0, 0);
        }
    }
    
    public Table[][] mainTable = new Table[Window.AI_MainBoard.heightInt][Window.AI_MainBoard.widthInt];
    
    protected CrntBlock crntBlock = new CrntBlock();
    protected NextBlock nextBlock = new NextBlock();
    private int numOfUsedBlocks = 0;
    private Vector<InformationBasedLocation> costVector = new Vector<InformationBasedLocation>();

    public AI_Tetris() {
        // 객체 할당
        for(int r = 0; r < Window.AI_MainBoard.heightInt; r++) {
            for(int c = 0; c < Window.AI_MainBoard.widthInt; c++) {
                mainTable[r][c] = new Table(BlockShape.NONE, false);
            }
        }
    }

    // **** Main Logic Function ****
    public void gameStart() {
        Timer timer = new Timer();
        TimerTask putBlock = new TimerTask() {
            @Override
            public void run() {
                setBlock();
                putBlock();
                costVector.clear();

                crntBlock.setBlock(nextBlock.list[numOfUsedBlocks % 7]);
                crntBlock.initPos();
                crntBlock.upload(mainTable);

                Window.paint.repaint();
            }
        };

        crntBlock.setBlock(nextBlock.list[numOfUsedBlocks % 7]);
        crntBlock.initPos();
        crntBlock.upload(mainTable);

        timer.scheduleAtFixedRate(putBlock, 1000, 1000);
    }

    private void setBlock() {
        Rule rule = new Rule();

        crntBlock.moveMax(Direction.LEFT);
        crntBlock.moveMax(Direction.DOWN);
 
        while(true) {
            InformationBasedLocation tempCost = new InformationBasedLocation();
            tempCost.cost = rule.getCost();
            tempCost.position.r = crntBlock.position.r;
            tempCost.position.c = crntBlock.position.c;

            costVector.add(tempCost);

            crntBlock.moveMax(Direction.UP);
            if(crntBlock.movable(Direction.RIGHT)) {
                crntBlock.move(Direction.RIGHT);
                crntBlock.moveMax(Direction.DOWN);
                continue;
            } else break;
        }
        Collections.sort(costVector, new costComparator());
        crntBlock.position = costVector.get(0).position;
    }



    private void putBlock() {
        crntBlock.solidification();
        crntBlock.upload(mainTable);
        numOfUsedBlocks++;
        // lineClear();

        if(numOfUsedBlocks % NextBlock.BAG == 0) nextBlock.setNextArray();
        clearLiquidBlock();

    }
    private void clearLiquidBlock() {
        for(int r = 0; r < 20; r ++) {
            for (int c = 0; c < 10; c++) {
                Position testPos = new Position(r, c);
                if(getMainTableBlockType(testPos) == BlockShape.LIQUID) {
                    mainTable[r][c].mino = BlockShape.NONE;
                    mainTable[r][c].isVisible = false;
                }
            }
        }
    }
    public BlockShape getMainTableBlockType(Position testPos) {
        // 테트리스 화면 밖 모든 블럭은 Solid 블록이다.

        // Array Index Out Of Bounds Exception
        if(testPos.c < 0 || testPos.c >= Window.AI_MainBoard.widthInt || testPos.r >= Window.AI_MainBoard.heightInt || testPos.r < 0) 
            return BlockShape.SOLID; 
        
        BlockShape mino = mainTable[testPos.r][testPos.c].mino;
        return getBlockType(mino);
    }
    public BlockShape getBlockType(BlockShape mino) {
        if (mino == BlockShape.NONE) return BlockShape.NONE;
        else if(mino == BlockShape.SLD_I || mino == BlockShape.SLD_J || mino == BlockShape.SLD_L || mino == BlockShape.SLD_O || mino == BlockShape.SLD_S || mino == BlockShape.SLD_J || mino == BlockShape.SLD_T || mino == BlockShape.SLD_Z)
            return BlockShape.SOLID;
        else if(mino == BlockShape.I || mino == BlockShape.J || mino == BlockShape.L || mino == BlockShape.O || mino == BlockShape.S || mino == BlockShape.J || mino == BlockShape.T || mino == BlockShape.Z)
            return BlockShape.LIQUID;
        else
            return BlockShape.NONE;
    }
    class costComparator implements Comparator<InformationBasedLocation> {
        public int compare(InformationBasedLocation cost1, InformationBasedLocation cost2) {
            if (cost1.cost > cost2.cost) {
                return 1;
            } else if(cost1.cost == cost2.cost) return 0;
            else return -1;
        }
    }
}
