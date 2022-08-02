package sk.stuba.fei.uim.oop;

import sk.stuba.fei.uim.oop.board.Board2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Button extends JButton implements ActionListener {
    protected Board2 board2;
    protected JFrame frame;
    public Button(String label, Board2 board2, JFrame frame){
        super(label);
        this.board2 = board2;
        this.frame = frame;
        setFocusable(true);
        this.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("RESET")){
            board2.reset();
            this.frame.setFocusable(true);
            this.frame.requestFocus();
        }
    }
}
