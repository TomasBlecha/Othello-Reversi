package sk.stuba.fei.uim.oop;

import sk.stuba.fei.uim.oop.board.Board2;


import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class SizeSlider extends JSlider implements ChangeListener {
    protected Board2 board2;
    protected JFrame frame;
    public SizeSlider(int orientation, int min, int max, int value, Board2 board2, JFrame frame){
        super(orientation, min, max, value);
        this.board2 = board2;
        this.frame = frame;
        setMajorTickSpacing(2);
        //setMinorTickSpacing(2);
        setPaintTicks(true);
        setPaintLabels(true);
        setSnapToTicks(true);
        this.addChangeListener(this);
    }


    @Override
    public void stateChanged(ChangeEvent e) {

        int setValue = getValue();
        board2.setTmpMaxSize(setValue);
        this.frame.setFocusable(true);
        this.frame.requestFocus();
    }
}
