package SetWeight;

import Main.Window;
import AI.*;

public class GA {
    public static AI.AI_Tetris ai = new AI.AI_Tetris();
    public static void main(String[] args) {
        new Window(ai);
        ai.PPS = 50;
        ai.gameStart();
        while(true) {
            if(ai.gameState == GameState.GAME_OVER) {

                
            }
        }
    }

    private void record() {
        
    }
}
