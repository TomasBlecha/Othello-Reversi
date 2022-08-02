package sk.stuba.fei.uim.oop.board;

import sk.stuba.fei.uim.oop.Player;



public class Node  {
    private final int x;
    private final int y;
    private Player owner;
    protected Board2 board2;
    private boolean visited = false;
    private boolean highlighted =false;



    public Node(int x ,int y, Board2 board2){
        this.x = x;
        this.y = y;
        this.board2 = board2;
    }

    public int nextNeighbor(int previousX, int previousY ,int xdir ,int ydir, int counter){
        if(previousY+ydir >= board2.getMaxSize() || previousY+ydir < 0 || previousX+xdir >= board2.getMaxSize() || previousX+xdir < 0){
            return -1;
        }
        if(board2.getGrid2()[previousY+ydir][previousX+xdir].getOwner() != null){
            if(board2.getGrid2()[previousY+ydir][previousX+xdir].getOwner().isWhite() != board2.isTurnWhite()){
                counter++;
                return nextNeighbor(previousX+xdir,previousY+ydir, xdir, ydir ,counter);
            }
            else{
                return counter;
            }
        }else{
            return -1;
        }

    }
    public void checkNeighbors(int x, int y){
        for(int i = -1 ; i < 2 ; i++){
            for(int j = -1 ; j < 2 ; j++){
                if (i==0 && j==0){
                    continue;
                }
                if(y+j >= board2.getMaxSize() || y+j < 0 || x+i >= board2.getMaxSize() || x+i < 0){
                    continue;
                }
                if(board2.getGrid2()[y + j][x + i].getOwner() == null){
                    int counter = 0;
                    int result = nextNeighbor(x, y,-i,-j,counter);


                    if (result > -1) {
                        board2.getGrid2()[y + j][x + i].setVisited(true);
                    }
                }
            }
        }
    }
    public void setNextTarget(int previousX, int previousY ,int xdir ,int ydir, int counter){


        if(counter > -1) {
            counter--;
            board2.getGrid2()[previousY + ydir][previousX + xdir].getOwner().setWhite(board2.isTurnWhite());

            setNextTarget(previousX + xdir, previousY + ydir, xdir, ydir, counter);
        }


    }
    public void checkTargets(int x, int y){

        for(int i = -1 ; i < 2 ; i++) {
            for (int j = -1; j < 2; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                if (y + j >= board2.getMaxSize() || y + j < 0 || x + i >= board2.getMaxSize() || x + i < 0) {
                    continue;
                }
                if(board2.getGrid2()[y + j][x + i].getOwner() != null) {
                    if (board2.getGrid2()[y+j][x+i].getOwner().isWhite() != board2.isTurnWhite()){
                        int counter = 0;
                        int result = nextNeighbor(x, y,i,j,counter);
                        if(result > 0) {
                            setNextTarget(x, y, i, j, result);

                        }
                    }

                }
            }
        }
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean isHighlighted() {
        return highlighted;
    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }
}
