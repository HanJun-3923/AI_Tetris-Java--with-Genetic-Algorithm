package TETRIS;



public class NextBlock {
    public static final int BAG = 7;
    
    public BlockShape[] list = new BlockShape[2 * BAG];
    public NextBlock() {
        for(int n = 0; n < 2; n++) {
            for(int i = 0 + (BAG * n); i < BAG  + (BAG * n); i++) {
                boolean re;
                do {
                    Double randNum = Math.random() * 10;
                    list[i] = BlockData.intToLiquidBlockShape(randNum.intValue() % 7 + 1);

                    re = false;
                    for(int j = 0 + (BAG * n); j < BAG  + (BAG * n); j++) {
                        if(i == j) continue;
                        else if(list[i] == list[j]) re = true;
                    }
                } while(re);
            }
        }
    }

    public void setNextArray() {
        for(int i = 0; i < BAG; i++) list[i] = list[i + BAG];
        for(int i = BAG; i < BAG * 2; i++) {
                boolean re;
                do {
                    Double randNum = Math.random() * 10;
                    list[i] = BlockData.intToLiquidBlockShape(randNum.intValue() % 7 + 1);

                    re = false;
                    for(int j = BAG; j < BAG * 2; j++) {
                        if(i == j) continue;
                        else if(list[i] == list[j]) re = true;
                    }
                } while(re);
        }
 
    }
}
