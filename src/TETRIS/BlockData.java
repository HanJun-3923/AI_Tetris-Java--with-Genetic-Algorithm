package TETRIS;

public class BlockData {
    private static int[][][] ITetromino = { // 1
        {   
            {0, 0, 0, 0},
            {1, 1, 1, 1},
            {0, 0, 0, 0},
            {0, 0, 0, 0}    
        }, 
        {   
            {0, 0, 1, 0},
            {0, 0, 1, 0},
            {0, 0, 1, 0},
            {0, 0, 1, 0}
        }, 
        {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {1, 1, 1, 1},
            {0, 0, 0, 0}
        }, 
        {
            {0, 1, 0, 0},
            {0, 1, 0, 0},
            {0, 1, 0, 0},
            {0, 1, 0, 0}
        }
    };
    private static int[][][] TTetromino = { // 2
        {
            {0, 2, 0, 0},
            {2, 2, 2, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
        }, 
        {
            {0, 2, 0, 0},
            {0, 2, 2, 0},
            {0, 2, 0, 0},
            {0, 0, 0, 0}
        }, 
        {
            {0, 0, 0, 0},
            {2, 2, 2, 0},
            {0, 2, 0, 0},
            {0, 0, 0, 0}
        }, 
        {
            {0, 2, 0, 0},
            {2, 2, 0, 0},
            {0, 2, 0, 0},
            {0, 0, 0, 0}
        }
    };
    private static int[][][] OTetromino = { // 3
        {
            {0, 3, 3, 0},
            {0, 3, 3, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
        }, 
        {
            {0, 3, 3, 0},
            {0, 3, 3, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
        }, 
        {
            {0, 3, 3, 0},
            {0, 3, 3, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
        }, 
        {
            {0, 3, 3, 0},
            {0, 3, 3, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
        }
    };
    private static int[][][] STetromino = { // 4
        {
            {0, 4, 4, 0},
            {4, 4, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
        }, 
        {
            {0, 4, 0, 0},
            {0, 4, 4, 0},
            {0, 0, 4, 0},
            {0, 0, 0, 0}
        }, 
        {
            {0, 0, 0, 0},
            {0, 4, 4, 0},
            {4, 4, 0, 0},
            {0, 0, 0, 0}
        }, 
        {
            {4, 0, 0, 0},
            {4, 4, 0, 0},
            {0, 4, 0, 0},
            {0, 0, 0, 0}
        }
    };
    public static int[][][] ZTetromino = { // BlockShape Z = int 5
        {
            {5, 5, 0, 0},
            {0, 5, 5, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
        }, 
        {
            {0, 0, 5, 0},
            {0, 5, 5, 0},
            {0, 5, 0, 0},
            {0, 0, 0, 0}
        }, 
        {
            {0, 0, 0, 0},
            {5, 5, 0, 0},
            {0, 5, 5, 0},
            {0, 0, 0, 0}
        }, 
        {
            {0, 5, 0, 0},
            {5, 5, 0, 0},
            {5, 0, 0, 0},
            {0, 0, 0, 0}
        }
    };
    private static int[][][] LTetromino = {
        {
            {0, 0, 6, 0},
            {6, 6, 6, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
        }, 
        {
            {0, 6, 0, 0},
            {0, 6, 0, 0},
            {0, 6, 6, 0},
            {0, 0, 0, 0}
        }, 
        {
            {0, 0, 0, 0},
            {6, 6, 6, 0},
            {6, 0, 0, 0},
            {0, 0, 0, 0}
        }, 
        {
            {6, 6, 0, 0},
            {0, 6, 0, 0},
            {0, 6, 0, 0},
            {0, 0, 0, 0}
        }
    };
    private static int[][][] JTetromino = {
        {
            {7, 0, 0, 0},
            {7, 7, 7, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
        }, 
        {
            {0, 7, 7, 0},
            {0, 7, 0, 0},
            {0, 7, 0, 0},
            {0, 0, 0, 0}
        }, 
        {
            {0, 0, 0, 0},
            {7, 7, 7, 0},
            {0, 0, 7, 0},
            {0, 0, 0, 0}
        }, 
        {
            {0, 7, 0, 0},
            {0, 7, 0, 0},
            {7, 7, 0, 0},
            {0, 0, 0, 0}
        }
    };
    public static int[][] fetch(BlockShape blockShape, int rot) {
        if(blockShape == BlockShape.I) {
            return ITetromino[rot];
        } else if(blockShape == BlockShape.T) {
            return TTetromino[rot];
        } else if(blockShape == BlockShape.O) {
            return OTetromino[rot];
        } else if(blockShape == BlockShape.S) {
            return STetromino[rot];
        } else if(blockShape == BlockShape.Z) {
            return ZTetromino[rot];
        } else if(blockShape == BlockShape.L) {
            return LTetromino[rot];
        } else {
            return JTetromino[rot];
        }
    }
    public static BlockShape intToLiquidBlockShape(int num) {
        switch(num) {
            case 1:
                return BlockShape.I;
            case 2:
                return BlockShape.T;
            case 3:
                return BlockShape.O;
            case 4:
                return BlockShape.S;
            case 5:
                return BlockShape.Z;
            case 6:
                return BlockShape.L;
            case 7:
                return BlockShape.J;
            default:
                return BlockShape.NONE;
        }
    }
}
