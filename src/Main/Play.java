package Main;

public class Play {
    public static AI.AI_Tetris AI = new AI.AI_Tetris();
    public static void main(String[] args) {
        new Window(AI);
        AI.gameStart();
    }
}
