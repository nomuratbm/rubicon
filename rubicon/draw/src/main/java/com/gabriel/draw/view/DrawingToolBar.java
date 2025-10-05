package com.gabriel.draw.view;

import com.gabriel.draw.controller.ActionController;
import com.gabriel.drawfx.ActionCommand;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class DrawingToolBar extends JToolBar {
    ActionListener actionListener;
    public DrawingToolBar(ActionListener actionListener){
        this.actionListener = actionListener;

        JButton undoButton = new JButton();
        JButton redoButton = new JButton();
        JButton lineButton = new JButton();
        JButton rectButton = new JButton();
        JButton ovalButton = new JButton();
        JButton colorButton = new JButton();
        JButton fillButton = new JButton();

        String undoLocation = "images/"
                + "undo"
                + ".png";
        URL undoURL = DrawingToolBar.class.getResource(undoLocation);

        undoButton.setActionCommand(ActionCommand.UNDO);
        undoButton.setFocusable(false);
        undoButton.setBorderPainted(false);
        undoButton.setToolTipText("Undo");
        undoButton.addActionListener(actionListener);
        undoButton.setIcon(new ImageIcon(undoURL, "Undo"));
        undoButton.setEnabled(false);

        String redoLocation = "images/"
                + "redo"
                + ".png";
        URL redoURL = DrawingToolBar.class.getResource(redoLocation);

        redoButton.setActionCommand(ActionCommand.REDO);
        redoButton.setFocusable(false);
        redoButton.setBorderPainted(false);
        redoButton.setToolTipText("Redo");
        redoButton.addActionListener(actionListener);
        redoButton.setIcon(new ImageIcon(redoURL, "Redo"));
        redoButton.setEnabled(false);

        String lineLocation = "images/"
                + "line"
                + ".png";
        URL lineURL = DrawingToolBar.class.getResource(lineLocation);

        lineButton.setActionCommand(ActionCommand.LINE);
        lineButton.setFocusable(false);
        lineButton.setBorderPainted(false);
        lineButton.setToolTipText("Line");
        lineButton.addActionListener(actionListener);
        lineButton.setIcon(new ImageIcon(lineURL, "Line"));

        String rectLocation = "images/"
                + "rect"
                + ".png";
        URL rectURL = DrawingToolBar.class.getResource(rectLocation);

        rectButton.setActionCommand(ActionCommand.RECT);
        rectButton.setFocusable(false);
        rectButton.setBorderPainted(false);
        rectButton.setToolTipText("Rectangle");
        rectButton.addActionListener(actionListener);
        rectButton.setIcon(new ImageIcon(rectURL, "Rectangle"));

        String ovalLocation = "images/"
                + "oval"
                + ".png";
        URL ovalURL = DrawingToolBar.class.getResource(ovalLocation);

        ovalButton.setActionCommand(ActionCommand.ELLIPSE);
        ovalButton.setFocusable(false);
        ovalButton.setBorderPainted(false);
        ovalButton.setToolTipText("Ellipse");
        ovalButton.addActionListener(actionListener);
        ovalButton.setIcon(new ImageIcon(ovalURL, "Ellipse"));

        String colorLocation = "images/"
                + "setcolor"
                + ".png";
        URL colorURL = DrawingToolBar.class.getResource(colorLocation);

        colorButton.setActionCommand(ActionCommand.SETCOLOR);
        colorButton.setFocusable(false);
        colorButton.setBorderPainted(false);
        colorButton.setToolTipText("Set Color");
        colorButton.addActionListener(actionListener);
        colorButton.setIcon(new ImageIcon(colorURL, "Set Color"));

        String fillLocation = "images/"
                + "setfill"
                + ".png";
        URL fillURL = DrawingToolBar.class.getResource(fillLocation);

        fillButton.setActionCommand(ActionCommand.SETFILL);
        fillButton.setFocusable(false);
        fillButton.setBorderPainted(false);
        fillButton.setToolTipText("Set Color");
        fillButton.addActionListener(actionListener);
        fillButton.setIcon(new ImageIcon(fillURL, "Set Fill"));

        add(undoButton);
        add(redoButton);
        add(lineButton);
        add(rectButton);
        add(ovalButton);
        add(colorButton);
        add(fillButton);

        ActionController.addUndoButton(undoButton);
        ActionController.addRedoButton(redoButton);
    }
}
