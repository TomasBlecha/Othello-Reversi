package sk.stuba.fei.uim.oop;

public class Player {
    private final int x;
    private final int y;
    private boolean isWhite = false;

    public Player(int x,int y, boolean wColor){
        this.x = x;
        this.y = y;
        if(wColor){
            isWhite = true;
        }

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public void setWhite(boolean white) {
        isWhite = white;
    }
}
