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
import java.io.BufferedReader;
import java.io.FileReader;

public class AI_Tetris {
    public class InformationBasedLocation {
        Position position;
        double cost;
        int rotation;

        InformationBasedLocation() {
            position = new Position(0, 0);
        }
    }
    public static class Score {
        public static final int Single = 100;
        public static final int Double = 300;
        public static final int Triple = 500;
        public static final int Tetris = 800;
        public static final int T_Spin_Mini_no_lines = 100;
        public static final int T_Spin_Mini_Single = 200;
        public static final int T_Spin_no_lines = 400;
        public static final int T_Spin_Single = 800;
        public static final int T_Spin_Double = 1200;
        public static final int T_Spin_Triple = 1600;
        public static final int Combo = 50;
        public static final int Hard_drop_per_cell = 2;
        public static final int Sofr_drop_per_cell = 1;
    }

    public Table[][] mainTable = new Table[Window.AI_MainBoard.heightInt][Window.AI_MainBoard.widthInt];
    public GameState gameState = GameState.GAME_RESUME;
    public long score = 0;
    public double PPS = 50;
    public int numOfUsedBlocks = 0;

    protected CrntBlock crntBlock = new CrntBlock(this);
    protected NextBlock nextBlock = new NextBlock();
    private Weight weight;
    private Vector<InformationBasedLocation> costVector = new Vector<InformationBasedLocation>();


    public AI_Tetris(Weight weight) {
        // 객체 할당
        for(int r = 0; r < Window.AI_MainBoard.heightInt; r++) {
            for(int c = 0; c < Window.AI_MainBoard.widthInt; c++) {
                mainTable[r][c] = new Table(BlockShape.NONE, false);
            }
        }
        this.weight = weight;
        
    }
    public AI_Tetris() {
        // 객체 할당
        for(int r = 0; r < Window.AI_MainBoard.heightInt; r++) {
            for(int c = 0; c < Window.AI_MainBoard.widthInt; c++) {
                mainTable[r][c] = new Table(BlockShape.NONE, false);
            }
        }
        weight = getWeight();
    }
    private Weight getWeight() {
        try {
        Weight weight = new Weight();

        String filePath = "src/PlayData/PlayData.txt";
        BufferedReader input = new BufferedReader(new FileReader(filePath));
        
        String str = input.readLine();
        int[] tempArray = new int[weight.numberOfWeight];
        for(int n = 0; n < weight.numberOfWeight; n++) {
            for(int i = 0; i < str.length(); i++) {
                if(str.charAt(i) == ':') {
                    String stringNum = "";
                    for(int j = i + 2; j < str.length(); j++) {
                        stringNum += str.charAt(j);
                    }
                    tempArray[n] = Integer.parseInt(stringNum);
                    break;
                }
            }
        }
        input.close();

        weight.heightWeight = tempArray[0];
        weight.doMakeHoleWeight = tempArray[1];

        return weight;
        } catch (Exception e) { e.getStackTrace(); return null; }
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
                crntBlock.setRotation(0);

                if(isGameOver()) {
                    gameState = GameState.GAME_OVER;
                    Window.paint.repaint();
                    timer.cancel();
                }
                else {
                    upload();
                    Window.paint.repaint();
                }
            }
        };

        crntBlock.setBlock(nextBlock.list[numOfUsedBlocks % 7]);
        crntBlock.initPos();
        crntBlock.setRotation(0);
        upload();

        long delay = (long) ((1.0 / PPS) * 1000);
        timer.scheduleAtFixedRate(putBlock, delay, delay);
    }
    
    private void setBlock() {
        for(int rot = 0; rot < 4; rot++) {
            crntBlock.setRotation(rot);
            crntBlock.setBlock(crntBlock.blockShape);

            crntBlock.moveMax(Direction.LEFT);
            crntBlock.moveMax(Direction.DOWN);
            
            while(true) {
                InformationBasedLocation tempCost = new InformationBasedLocation();
                
                Rule rule = new Rule(mainTable, crntBlock, weight);
                tempCost.cost = rule.getCost();
                // if(numOfUsedBlocks != 0) 
                //      try { Thread.sleep(5000); } catch (InterruptedException e) { e.printStackTrace(); }
                tempCost.position.r = crntBlock.position.r;
                tempCost.position.c = crntBlock.position.c;
                tempCost.rotation = rot;

                costVector.add(tempCost);

                crntBlock.moveMax(Direction.UP);
                if(crntBlock.movable(Direction.RIGHT)) {
                    crntBlock.move(Direction.RIGHT);
                    crntBlock.moveMax(Direction.DOWN);
                    continue;
                } else break;
            }
            crntBlock.initPos();
        }
        Collections.sort(costVector, new costComparator());
        crntBlock.setRotation(costVector.get(0).rotation);
        crntBlock.position = costVector.get(0).position;
        crntBlock.setBlock(crntBlock.blockShape);
    }

    private void putBlock() {
        crntBlock.solidification();
        upload();
        numOfUsedBlocks++;
        score += Score.Hard_drop_per_cell * (crntBlock.position.r - 1);
        lineClear();

        if(numOfUsedBlocks % NextBlock.BAG == 0) nextBlock.setNextArray();
        clearLiquidBlock();
        crntBlock.setRotation(0);

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
        BlockShape result = getBlockType(mino);
        return result;
    }
    public BlockShape getBlockType(BlockShape mino) {
        if(mino == BlockShape.SLD_I || mino == BlockShape.SLD_J || mino == BlockShape.SLD_L || mino == BlockShape.SLD_O || mino == BlockShape.SLD_S || mino == BlockShape.SLD_J || mino == BlockShape.SLD_T || mino == BlockShape.SLD_Z)
            return BlockShape.SOLID;
        else if(mino == BlockShape.I || mino == BlockShape.J || mino == BlockShape.L || mino == BlockShape.O || mino == BlockShape.S || mino == BlockShape.J || mino == BlockShape.T || mino == BlockShape.Z)
            return BlockShape.LIQUID;
        else
            return BlockShape.NONE;
    }
    private void lineClear() {
        int removedLine = 0;
        for(int r = 0; r < Main.Window.AI_MainBoard.heightInt; r++) {
            if(isLineFull(r)) { // 라인이 다 찼다면
                removedLine++;
                for(int upperRow = r - 1; upperRow >= 0; upperRow--) { // upperRow + 1 로 인해 MAIN_BOARD.INT_HEIGHT - 1 까지 반복
                    for(int c = 0; c < Main.Window.AI_MainBoard.widthInt; c++) {
                        mainTable[upperRow + 1][c].mino = mainTable[upperRow][c].mino;
                        mainTable[upperRow + 1][c].isVisible = mainTable[upperRow][c].isVisible;
                        if(upperRow == 0) {
                            mainTable[upperRow][c].mino = BlockShape.NONE;
                            mainTable[upperRow][c].isVisible = false;
                        }
                    }
                }
            }
        }
        if(removedLine == 1) score += Score.Single;
        else if(removedLine == 2) score += Score.Double;
        else if(removedLine == 3) score += Score.Triple;
        else if(removedLine == 4) score += Score.Tetris;

    }
    private void upload() {
        for(int r = 0; r < 4; r++) {
            for(int c = 0; c < 4; c++) {
                if(crntBlock.blockArray[r][c] != BlockShape.NONE) {
                    mainTable[crntBlock.position.r + r][crntBlock.position.c + c].mino = crntBlock.blockArray[r][c];
                    mainTable[crntBlock.position.r + r][crntBlock.position.c + c].isVisible = true;
                }
            }
        }
    }
    private boolean isLineFull(int r) {
        for(int c = 0; c < Main.Window.AI_MainBoard.widthInt; c++) {
            // 하나라도 블럭이 비었다면 return false
            if(mainTable[r][c].isVisible == false) return false; 
        }
        // 모두 블럭이 차있으므로 return true
        return true;
    }
    private boolean isGameOver() {
        for(int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                if(crntBlock.blockArray[r][c] != BlockShape.NONE) {
                    if(mainTable[r + crntBlock.position.r][c + crntBlock.position.c].isVisible == true) return true;
                    else continue;
                }
            }
        }
        return false;
    }
    
    class costComparator implements Comparator<InformationBasedLocation> {
        public int compare(InformationBasedLocation cost1, InformationBasedLocation cost2) {
            if (cost1.cost > cost2.cost) {
                return 1;
            } 
            else if(cost1.cost == cost2.cost) return 0;
            else return -1;
        }
    }
}
