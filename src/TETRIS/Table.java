package TETRIS;

public class Table {
    public BlockShape mino;
    public boolean isVisible;
    public Table(BlockShape mino, boolean isVisible) {
        this.mino = mino;
        this.isVisible = isVisible;
    }
}
