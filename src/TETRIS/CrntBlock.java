package TETRIS;

import Main.Main;

public class CrntBlock {
    public BlockShape[][] blockArray = new BlockShape[4][4];
    public BlockShape blockShape = BlockShape.NONE;
    public int rotation = 0;
    protected Position INITIAL_POS = new Position(0, 3);
    public Position position = new Position(INITIAL_POS.r, INITIAL_POS.c);

    public CrntBlock() {
        for(int r = 0; r < 4; r++) {
            for(int c = 0; c < 4; c++) {
                blockArray[r][c] = BlockShape.NONE;
            }
        }
    }
    public void rotate(Direction direction) {
        if(direction == Direction.CLOCKWISE) {
            rotation++;
            if(rotation >= 4) rotation -= 4;
        }
        else {
            rotation--;
            if(rotation < 0) rotation += 4;
        }
        setBlockArray();
    }
    public void setBlockShape(BlockShape blockShape) {
        this.blockShape = blockShape;
        setBlockArray();
    }
    public void setRotation(int rot) {
        rotation = rot;
    }
    private void setBlockArray() {
        int[][] temp = BlockData.fetch(blockShape, rotation);
        for(int r = 0; r < 4; r++) {
            for(int c = 0; c < 4; c++) {
                blockArray[r][c] = BlockData.intToLiquidBlockShape(temp[r][c]);
            }
        }
    }
    public void upload(Table[][] mainTable) {
        for(int r = 0; r < 4; r++) {
            for(int c = 0; c < 4; c++) {
                if(blockArray[r][c] != BlockShape.NONE) {
                    mainTable[position.r + r][position.c + c].mino = blockArray[r][c];
                    mainTable[position.r + r][position.c + c].isVisible = true;
                }
            }
        }
    }
    public void initPos() {
        position.r = INITIAL_POS.r;
        position.c = INITIAL_POS.c;
    }
    public boolean movable(Direction direction) {
        if(direction == Direction.LEFT) {
            for (int r = 0; r < 4; r++) {
                for (int c = 0; c < 4; c++) {
                    if(blockArray[r][c] == blockShape) { // 4x4 행렬 속에서 블럭이 존재할 때
                        Position testPos = new Position(r + position.r, c + position.c - 1);
                        BlockShape testResult = Main.AI.getMainTableBlockType(testPos);
                        if(testResult == BlockShape.SOLID) return false;
                    }
                }
            }
        }
        else if(direction == Direction.RIGHT) {
            for (int r = 0; r < 4; r++) {
                for (int c = 0; c < 4; c++) {
                    if(blockArray[r][c] == blockShape) { // 4x4 행렬 속에서 블럭이 존재할 때
                        Position testPos = new Position(r + position.r, c + position.c + 1);
                        BlockShape testResult = Main.AI.getMainTableBlockType(testPos);
                        if(testResult == BlockShape.SOLID) return false;
                    }
                }
            }
        }
        else if(direction == Direction.DOWN) {
            for (int r = 0; r < 4; r++) {
                for (int c = 0; c < 4; c++) {
                    if(blockArray[r][c] == blockShape) { // 4x4 행렬 속에서 블럭이 존재할 때
                        Position testPos = new Position(r + position.r + 1, c + position.c);
                        BlockShape testResult = Main.AI.getMainTableBlockType(testPos);
                        if(testResult == BlockShape.SOLID) return false;
                    }
                }
            }
        }
        else if(direction == Direction.UP) {
            for (int r = 0; r < 4; r++) {
                for (int c = 0; c < 4; c++) {
                    if(blockArray[r][c] == blockShape) { // 4x4 행렬 속에서 블럭이 존재할 때
                        Position testPos = new Position(r + position.r - 1, c + position.c);
                        BlockShape testResult = Main.AI.getMainTableBlockType(testPos);
                        if(testResult == BlockShape.SOLID) return false;
                    }
                }
            }
        }
        else { // direction == Direction.NONE
            for (int r = 0; r < 4; r++) {
                for (int c = 0; c < 4; c++) {
                    if(blockArray[r][c] == blockShape) { // 4x4 행렬 속에서 블럭이 존재할 때
                        Position testPos = new Position(r + position.r, c + position.c);
                        BlockShape testResult = Main.AI.getMainTableBlockType(testPos);
                        if(testResult == BlockShape.SOLID) return false;
                    }
                }
            }
        }

        // 모든 조건 통과
        return true;
    }
    public void move(Direction direction) {
        if(direction == Direction.LEFT) {
            position.c--;
        } else if (direction == Direction.RIGHT) {
            position.c++;
        } else if (direction == Direction.DOWN) {
            position.r++;
        } else if(direction == Direction.UP) {
            position.r--;
        }
    }
    public void moveMax(Direction direction) {
        int n = 0; // test
        if(direction == Direction.LEFT) {
            while(movable(direction)) { position.c--; n++; }
            System.out.println("movaMax Left, number of movement = " + n); // test
        } else if (direction == Direction.RIGHT) {
            while(movable(direction)) { position.c++; n++; }
            System.out.println("movaMax RIGHT, number of movement = " + n); // test
        } else if (direction == Direction.DOWN) {
            while(movable(direction)) { position.r++; n++; }
            System.out.println("movaMax DOWN, number of movement = " + n); // test
        } else if(direction == Direction.UP) {
            while(movable(direction)) { position.r--; n++; }
            System.out.println("movaMax UP, number of movement = " + n); // test
        }
    }
    public void solidification() {
        // change liquid blocks into solid blocks
        BlockShape solidBlockShape; // 변경될 solid 블록
        
        if(blockShape == BlockShape.I) solidBlockShape = BlockShape.SLD_I;
        else if(blockShape == BlockShape.T) solidBlockShape = BlockShape.SLD_T;
        else if(blockShape == BlockShape.O) solidBlockShape = BlockShape.SLD_O;
        else if(blockShape == BlockShape.S) solidBlockShape = BlockShape.SLD_S;
        else if(blockShape == BlockShape.Z) solidBlockShape = BlockShape.SLD_Z;
        else if(blockShape == BlockShape.L) solidBlockShape = BlockShape.SLD_L;
        else solidBlockShape = BlockShape.SLD_J;

        // solidification
        for(int r = 0; r < 4; r++) {
            for(int c = 0; c < 4; c++) {
                if(blockArray[r][c] == blockShape) {
                    blockArray[r][c] = solidBlockShape;
                }
            }
        }
    }
}
