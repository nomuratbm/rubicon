package com.gabriel.draw.view;

import com.gabriel.draw.controller.ActionController;
import com.gabriel.drawfx.ActionCommand;
import com.gabriel.drawfx.ShapeMode;
import com.gabriel.drawfx.service.AppService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class DrawingMenuBar extends JMenuBar {
    public DrawingMenuBar(ActionListener actionListener ){
        super();
        JMenuItem lineMenuItem = new JMenuItem("Line");
        JMenuItem rectangleMenuItem = new JMenuItem("Rectangle");
        JMenuItem ellipseMenuItem = new JMenuItem("Ellipse");

        JMenuItem undoMenuItem = new JMenuItem("Undo");
        JMenuItem redoMenuItem = new JMenuItem("Redo");
        JMenuItem selectMenuItem = new JMenuItem("Select");
        JMenuItem colorMenuItem = new JMenuItem("Color");
        JMenuItem fillMenuItem = new JMenuItem("Fill");

        JMenu editMenu = new JMenu("Edit");
        editMenu.setMnemonic(KeyEvent.VK_E);
        add(editMenu);
        undoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
        undoMenuItem.addActionListener(actionListener);
        undoMenuItem.setActionCommand(ActionCommand.UNDO);
        undoMenuItem.setEnabled(false);
        editMenu.add(undoMenuItem);
        redoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));;
        redoMenuItem.addActionListener(actionListener);
        redoMenuItem.setActionCommand(ActionCommand.REDO);
        redoMenuItem.setEnabled(false);
        editMenu.add(redoMenuItem);
        selectMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        selectMenuItem.addActionListener(actionListener);
        selectMenuItem.setActionCommand(ActionCommand.SELECT);
        editMenu.add(selectMenuItem);
        ActionController.addUndoMenuItem(undoMenuItem);
        ActionController.addRedoMenuItem(redoMenuItem);


        JMenu drawMenu = new JMenu("Draw");
        drawMenu.setMnemonic(KeyEvent.VK_D);
        editMenu.add(drawMenu);
        drawMenu.add(lineMenuItem);
        lineMenuItem.setActionCommand(ActionCommand.LINE);
        lineMenuItem.addActionListener(actionListener);
        drawMenu.add(rectangleMenuItem);
        rectangleMenuItem.setActionCommand(ActionCommand.RECT);
        rectangleMenuItem.addActionListener(actionListener);
        drawMenu.add(ellipseMenuItem);
        ellipseMenuItem.setActionCommand(ActionCommand.ELLIPSE);
        ellipseMenuItem.addActionListener(actionListener);

        JMenu propMenu = new JMenu("Properties");
        propMenu.setMnemonic(KeyEvent.VK_P);
        propMenu.add(colorMenuItem);
        add(propMenu);
        colorMenuItem.setActionCommand(ActionCommand.SETCOLOR);
        colorMenuItem.addActionListener(actionListener);
        propMenu.add(fillMenuItem);
        fillMenuItem.setActionCommand(ActionCommand.SETFILL);
        fillMenuItem.addActionListener(actionListener);
    }
}
