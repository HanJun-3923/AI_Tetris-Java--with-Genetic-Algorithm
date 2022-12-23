package AI;

import Main.Window;
import TETRIS.BlockShape;
import TETRIS.CrntBlock;
import TETRIS.Direction;
import TETRIS.NextBlock;
import TETRIS.Position;
import TETRIS.Table;

import java.util.Vector;


public class AI_Tetris {
    public Table[][] mainTable = new Table[Window.AI_MainBoard.heightInt][Window.AI_MainBoard.widthInt];
    
    private CrntBlock crntBlock = new CrntBlock();
    private NextBlock nextBlock = new NextBlock();
    private int numOfUsedBlocks = 0;
    private Vector<Cost> costVector = new Vector<Cost>();

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
        crntBlock.setBlockShape(nextBlock.list[numOfUsedBlocks % 7]);
        crntBlock.initPos();
        crntBlock.upload(mainTable);
        
        putBlock();
    }

    private void putBlock() {
        crntBlock.moveMax(Direction.LEFT);
        crntBlock.moveMax(Direction.DOWN);
        
        costVector.clear();
        costVector.add(getCost());
        getCost();


    }

    private Cost getCost() {
        Cost resultCost = new Cost();
        

        return resultCost;
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
}
