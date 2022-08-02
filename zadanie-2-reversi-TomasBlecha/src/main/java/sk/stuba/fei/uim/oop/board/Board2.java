package sk.stuba.fei.uim.oop.board;


import sk.stuba.fei.uim.oop.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;

public class Board2 extends JPanel implements MouseListener, MouseMotionListener {

    private final Game game;
    private Node[][] grid2;
    private int maxSize = 6;
    private int tmpMaxSize = 6;
    private final int nodeSize = 50;
    private int clicked_x;
    private int clicked_y;
    private boolean turnWhite = true;
    private int hlX = 0;
    private int hlY = 0;
    private boolean clickedMouse = false;
    private int moveCounter = 4;
    private int turnSkip = 0;
    private int potentialCounter = 0;
    private boolean paintAfterSkip = false;
    private boolean outOfOrder = false;
    private boolean someoneWon = false;

    public Board2(Game game){
        this.game = game;
        this.addMouseListener(this);
        this.addMouseMotionListener(this);


    }


    public int winCond(){

            int blackCount = 0;
            int whiteCount = 0;
            for(int y = 0 ; y < maxSize ; y++) {
                for (int x = 0; x < maxSize; x++) {
                    if(grid2[y][x].getOwner() != null) {
                        if (grid2[y][x].getOwner().isWhite()) {
                            whiteCount++;
                        } else {
                            blackCount++;
                        }
                    }

                }
            }
            if(blackCount > whiteCount){
                System.out.println("Black Wins!!!");
                return 0;
            }
            else if(whiteCount > blackCount){
                System.out.println("White Wins!!!");
                return 1;
            }
            else{
                System.out.println("It is a Draw!!!");
                return 2;
            }
        }


    public void start(){
        grid2[maxSize/2-1][maxSize/2-1].setOwner(new Player(maxSize/2-1,maxSize/2-1, false));
        grid2[maxSize/2][maxSize/2].setOwner(new Player(maxSize/2,maxSize/2, false));
        grid2[maxSize/2][maxSize/2-1].setOwner(new Player(maxSize/2,maxSize/2-1, true));
        grid2[maxSize/2-1][maxSize/2].setOwner(new Player(maxSize/2-1,maxSize/2, true));
    }

    public void reset(){

        outOfOrder = true;
        maxSize = tmpMaxSize;
        grid2 = new Node[maxSize][maxSize];

        for(int y = 0 ; y < maxSize ; y++){
            for (int x = 0 ; x < maxSize ; x++){
                grid2[y][x] = new Node(x,y,this);
            }
        }
        potentialCounter = 0;
        someoneWon = false;
        turnWhite = true;
        clickedMouse = false;
        hlX = 0;
        hlY = 0;
        moveCounter = 4;
        turnSkip = 0;
        paintAfterSkip = false;
        game.getBoardSize().maxSizeShower(maxSize);
        game.getPlayerTurn().playerTurnShower(turnWhite);

        start();
        SwingUtilities.updateComponentTreeUI(this);

        outOfOrder = false;
    }
    public void resetVisited(){
        for(int y = 0 ; y < maxSize ; y++){
            for (int x = 0 ; x < maxSize ; x++) {
                if(grid2[y][x].isVisited()) {
                    grid2[y][x].setVisited(false);
                }
            }
        }
        //set potential nodes as visited

    }
    public void visitColor(boolean isWhiteColor){

        for(int y = 0 ; y < maxSize ; y++){
            for (int x = 0 ; x < maxSize ; x++) {
                if(grid2[y][x].getOwner() != null){
                    if(grid2[y][x].getOwner().isWhite() != isWhiteColor){
                        grid2[y][x].checkNeighbors(x,y);
                    }
                }

            }
        }
    }
    public void drawNodes(Graphics g){
        for(int y = 0 ; y < maxSize ; y++){
            for (int x = 0 ; x < maxSize ; x++){
                int xLoc = grid2[y][x].getX() * nodeSize ;
                int yLoc = grid2[y][x].getY() * nodeSize ;
                if((x+y)%2 == 0) {
                    g.setColor(Color.GREEN);
                }
                else{
                    g.setColor(new Color(15,112,23,160));
                }
                g.fillRect(xLoc , yLoc , nodeSize, nodeSize);

                g.setColor(Color.BLACK);
                g.drawRect(xLoc , yLoc , nodeSize, nodeSize);

            }
        }
    }
    public void drawChips(Graphics g){
        for(int y = 0 ; y < maxSize ; y++){
            for (int x = 0 ; x < maxSize ; x++) {
                if(!(grid2[y][x].getOwner() == null)){
                    if(grid2[y][x].getOwner().isWhite()){
                        g.setColor(Color.WHITE);
                    }
                    else {
                        g.setColor(Color.BLACK);
                    }
                    int xLoc = grid2[y][x].getX() * nodeSize ;
                    int yLoc = grid2[y][x].getY() * nodeSize ;
                    g.fillOval(xLoc+5,yLoc+5, nodeSize-10, nodeSize-10);
                }

            }
        }
    }
    public void drawPotential(Graphics g){
        g.setColor(Color.BLUE);
        for(int y = 0 ; y < maxSize ; y++) {
            for (int x = 0; x < maxSize; x++) {
                if(grid2[y][x].isVisited()){
                    if(moveCounter != maxSize * maxSize){
                        turnSkip = 0;
                        potentialCounter ++;
                    }

                    int xLoc = grid2[y][x].getX() * (nodeSize) ;
                    int yLoc = grid2[y][x].getY() * (nodeSize) ;
                    g.fillOval(xLoc+10,yLoc+10, nodeSize-20, nodeSize-20);
                }


            }
        }
    }
    public void highlightPotential(Graphics g){
        g.setColor(Color.RED);
        if (grid2[hlY][hlX].isHighlighted()) {

            int xLoc = hlX * nodeSize;
            int yLoc = hlY * nodeSize;
            g.fillOval(xLoc + 15, yLoc + 15, nodeSize - 30, nodeSize - 30);
        }
    }
    public void botLogic(){
        if(!someoneWon && !turnWhite ){
            //System.out.println(potentialCounter);
            boolean change = false;
            if(potentialCounter != 0) {
                int result = new Random().nextInt(potentialCounter);

                //System.out.println("result: " + result);
                for (int y = 0; y < maxSize; y++) {
                    for (int x = 0; x < maxSize; x++) {
                        if (grid2[y][x].isVisited()) {
                            if (result == 0 && !change) {
                                grid2[y][x].setOwner(new Player(x, y, false));
                                grid2[y][x].checkTargets(x, y);
                                change = true;
                            } else {
                                result--;
                            }
                        }
                    }
                }
                clickedMouse = true;
                turnWhite = !turnWhite;
                game.getPlayerTurn().playerTurnShower(turnWhite);
                potentialCounter = 0;
                moveCounter++;
                repaint();
                System.out.println("black nom: "+moveCounter);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g){

        super.paintComponent(g);
        // set all nodes visited = false
        if(clickedMouse) {
            if(turnWhite){
                System.out.println("White`s turn.");
            }
            else{
                System.out.println("Black`s turn.");
            }
            resetVisited();

        }
        visitColor(turnWhite);


        //draw board nodes
        drawNodes(g);
        //draw player chip
        drawChips(g);

        turnSkip++;



        //draw potential placements
        drawPotential(g);

        //highlight potential Node
        if(!outOfOrder) {
            highlightPotential(g);
        }

        //set highlighted off
        grid2[hlY][hlX].setHighlighted(false);

        // if two skips ,find winner
        if(turnSkip == 2) {
            game.getPlayerTurn().showWinner(winCond());
            someoneWon = true;
        }
        //skipping turns

        if(turnSkip > 0 && !someoneWon){
            System.out.println("skip");
            turnWhite = !turnWhite;
            paintAfterSkip = true;
        }
        clickedMouse = false;
        //win conditions


        if (moveCounter == maxSize*maxSize && !someoneWon){
            game.getPlayerTurn().showWinner(winCond());
            someoneWon = true;
        }

        if(paintAfterSkip){
            paintAfterSkip = false;
            repaint();
        }

        botLogic(); // if you want player vs player ,just delete this line
        potentialCounter = 0;


    }

    public Node[][] getGrid2() {
        return grid2;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public boolean isTurnWhite() {
        return turnWhite;
    }

    public void setTmpMaxSize(int tmpMaxSize) {
        this.tmpMaxSize = tmpMaxSize;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        clicked_x = (e.getPoint().x ) / (nodeSize) ;
        clicked_y = (e.getPoint().y ) / (nodeSize) ;
        if(!(clicked_x >= maxSize || clicked_x < 0 || clicked_y >= maxSize || clicked_y < 0)) {
            if (grid2[clicked_y][clicked_x].isVisited()) {

                if (!turnWhite) {
                    grid2[clicked_y][clicked_x].setOwner(new Player(clicked_x, clicked_y, false));
                    grid2[clicked_y][clicked_x].checkTargets(clicked_x, clicked_y);
                    turnWhite = true;
                } else {
                    grid2[clicked_y][clicked_x].setOwner(new Player(clicked_x, clicked_y, true));
                    grid2[clicked_y][clicked_x].checkTargets(clicked_x, clicked_y);
                    turnWhite = false;
                }
                game.getPlayerTurn().playerTurnShower(turnWhite);


                moveCounter++;
                System.out.println("white nom: "+moveCounter);
                clickedMouse = true;
                repaint();
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!outOfOrder){
            clicked_x = (e.getPoint().x) / (nodeSize);
            clicked_y = (e.getPoint().y) / (nodeSize);

            if (!(clicked_x >= maxSize || clicked_x < 0 || clicked_y >= maxSize || clicked_y < 0)) {
                if (grid2[clicked_y][clicked_x].isVisited()) {
                    grid2[clicked_y][clicked_x].setHighlighted(true);
                    hlX = clicked_x;
                    hlY = clicked_y;
                }
            }
            repaint();
        }
    }
}