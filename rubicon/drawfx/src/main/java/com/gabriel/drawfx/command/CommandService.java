package com.gabriel.drawfx.command;

import java.util.Stack;
import java.util.ArrayList;
import java.util.List;
public class CommandService {
    static Stack<Command> undoStack = new Stack<Command>();
    static Stack<Command> redoStack = new Stack<Command>();
    static List<StackStateListener> listeners = new ArrayList<>();

    public interface StackStateListener {
        void stackStateChanged(boolean canUndo, boolean canRedo);
    }

    public static void addStackStateListener(StackStateListener listener) {
        listeners.add(listener);
    }

    public static void notifyStackStateChanged() {
        boolean canUndo = !undoStack.empty();
        boolean canRedo = !redoStack.empty();
        for (StackStateListener listener : listeners) {
            listener.stackStateChanged(canUndo, canRedo);
        }
    }

    public static void ExecuteCommand(Command command) {
        command.execute();
        undoStack.push(command);
        redoStack.clear();
        notifyStackStateChanged();
    }

    public static void undo() {
        if (undoStack.empty())
            return;
        Command command = undoStack.pop();
        command.undo();
        redoStack.push(command);
        notifyStackStateChanged();
    }

    public static void redo() {
        if (redoStack.empty())
            return;
        Command command = redoStack.pop();
        command.execute();
        undoStack.push(command);
        notifyStackStateChanged();
    }

    public static boolean canUndo() {
        return !undoStack.empty();
    }

    public static boolean canRedo() {
        return !redoStack.empty();
    }
}