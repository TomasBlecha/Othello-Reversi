package sk.stuba.fei.uim.oop.board;

import sk.stuba.fei.uim.oop.Button;
import sk.stuba.fei.uim.oop.Logic;
import sk.stuba.fei.uim.oop.ShowLabel;
import sk.stuba.fei.uim.oop.SizeSlider;

import javax.swing.*;
import java.awt.*;

public class Game {
    private final ShowLabel boardSize;
    private final ShowLabel playerTurn;
    public Game(){
        JFrame frame = new JFrame("Reversi");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(617,700);
        frame.setFocusable(true);
        frame.setResizable(false);

        //frame.setLayout(new BorderLayout());

        JPanel jpanel = new JPanel();
        frame.add(jpanel);
        jpanel.setLayout(new BorderLayout());
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new FlowLayout());
        jpanel.add(infoPanel, BorderLayout.SOUTH);
        Board2 board2 = new Board2(this);

        Logic logic = new Logic(board2,frame);

        frame.addKeyListener(logic);

        jpanel.add(board2);
        Button reset = new Button("RESET",board2,frame);
        infoPanel.add(reset);
        SizeSlider slider = new SizeSlider(JSlider.HORIZONTAL,6,12,6,board2,frame);
        infoPanel.add(slider);
        this.boardSize = new ShowLabel();
        infoPanel.add(boardSize);
        this.playerTurn = new ShowLabel();
        infoPanel.add(playerTurn);





        frame.setVisible(true);


        board2.reset();

    }

    public ShowLabel getBoardSize() {
        return boardSize;
    }

    public ShowLabel getPlayerTurn() {
        return playerTurn;
    }
}
