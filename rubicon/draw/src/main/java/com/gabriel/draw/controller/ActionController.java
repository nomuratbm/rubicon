package com.gabriel.draw.controller;

import com.gabriel.draw.command.SetColorCommand;
import com.gabriel.draw.command.SetFillCommand;
import com.gabriel.drawfx.ActionCommand;
import com.gabriel.drawfx.ShapeMode;
import com.gabriel.drawfx.command.CommandService;
import com.gabriel.drawfx.service.AppService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ActionController implements ActionListener, CommandService.StackStateListener {
    AppService appService;
    static List<JMenuItem> undoMenuItems = new ArrayList<>();
    static List<JMenuItem> redoMenuItems = new ArrayList<>();
    static List<JButton> undoButtons = new ArrayList<>();
    static List<JButton> redoButtons = new ArrayList<>();

    public  ActionController(AppService appService){
        this.appService = appService;
        CommandService.addStackStateListener(this);
    }

    public static void addUndoMenuItem(JMenuItem undoItem) {
        undoMenuItems.add(undoItem);
    }

    public static void addRedoMenuItem(JMenuItem redoItem) {
        redoMenuItems.add(redoItem);
    }

    public static void addUndoButton(JButton undoButton) {
        undoButtons.add(undoButton);
    }

    public static void addRedoButton(JButton redoButton) {
        redoButtons.add(redoButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if(ActionCommand.UNDO.equals(cmd)){
            appService.undo();
        }
        if(ActionCommand.REDO.equals(cmd)) {
            appService.redo();
        }
        if(ActionCommand.SELECT.equals(cmd)) {
            appService.setShapeMode(ShapeMode.Select);
            appService.clear();
            appService.repaint();
        }
        if(ActionCommand.LINE.equals(cmd)){
            appService.setShapeMode( ShapeMode.Line);
            appService.clear();
            appService.repaint();
            resetCursor();
        }
        if(ActionCommand.RECT.equals(cmd)){
            appService.setShapeMode( ShapeMode.Rectangle);
            appService.clear();
            appService.repaint();
            resetCursor();
        }
        if(ActionCommand.ELLIPSE.equals(cmd)){
            appService.setShapeMode( ShapeMode.Ellipse);
            appService.clear();
            appService.repaint();
            resetCursor();
        }
        if(ActionCommand.SETCOLOR.equals(cmd)) {
            Color color = JColorChooser.showDialog(null, "Choose outline color", appService.getColor());
            if(color != null) {
                appService.setColor(color);
            }
        }
        if(ActionCommand.SETFILL.equals(cmd)) {
            Color fill = JColorChooser.showDialog(null, "Choose fill color", appService.getFill());
            if(fill != null) {
                appService.setFill(fill);
            }
        }
    }

    @Override
    public void stackStateChanged(boolean canUndo, boolean canRedo) {
        for(JMenuItem item : undoMenuItems) {
            item.setEnabled(canUndo);
        }
        for(JButton button : undoButtons) {
            button.setEnabled(canUndo);
        }
        for(JMenuItem item : redoMenuItems) {
            item.setEnabled(canRedo);
        }
        for(JButton button : redoButtons) {
            button.setEnabled(canRedo);
        }
    }

    private void resetCursor() {
        JPanel view = appService.getView();
        if (view != null) {
            view.setCursor(Cursor.getDefaultCursor());
        }
    }
}
