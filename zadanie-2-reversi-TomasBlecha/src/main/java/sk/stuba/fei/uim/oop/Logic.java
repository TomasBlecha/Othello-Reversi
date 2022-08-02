package sk.stuba.fei.uim.oop;

import sk.stuba.fei.uim.oop.board.Board2;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Logic implements KeyListener {
    private final Board2 board2;
    private JFrame frame;

    public Logic(Board2 board2, JFrame frame){
        this.board2 = board2;
        this.frame = frame;

    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch(e.getKeyCode()) {
            case KeyEvent.VK_R:
                board2.reset();
                break;
            case KeyEvent.VK_ESCAPE:
                frame.dispose();
                System.exit(0);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }




}
