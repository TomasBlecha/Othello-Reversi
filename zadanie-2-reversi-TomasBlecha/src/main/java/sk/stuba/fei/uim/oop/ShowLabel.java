package sk.stuba.fei.uim.oop;



import javax.swing.*;

public class ShowLabel extends JLabel {

    public ShowLabel(){

        setFocusable(true);
    }
    public void playerTurnShower(boolean whiteOnTurn){
        if(whiteOnTurn){
            setText("Player on turn: White");
        }
        else {
            setText("Player on turn: Black");
        }
    }
    public void showWinner(int player){
        if(player == 0){
            setText("Black Wins!!!");
        }
        else if(player == 1){
            setText("White Wins!!!");
        }
        else if(player == 2){
            setText("Draw!!!");
        }
    }
    public void maxSizeShower(int maxSize){
        setText("Board size: "+maxSize+" x "+maxSize+"  ");
    }


}
