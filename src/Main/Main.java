package Main;

public class Main {
    public static AI.AI_Tetris AI = new AI.AI_Tetris();
    public static void main(String[] args) {
        new Window();
        AI.gameStart();
    }
}
