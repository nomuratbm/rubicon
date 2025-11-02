package com.gabriel.drawfx.command;

import java.util.Stack;
public class CommandService {
    static Stack<Command> undoStack = new Stack<Command>();
    static Stack<Command> redoStack = new Stack<Command>();

    public static void ExecuteCommand(Command command) {
        command.execute();
        undoStack.push(command);
    }

    public static void undo() {
        if (undoStack.empty())
            return;
        Command command = undoStack.pop();
        command.undo();
        redoStack.push(command);
    }

    public static void redo() {
        if (redoStack.empty())
            return;
        Command command = redoStack.pop();
        command.execute();
        undoStack.push(command);
    }
}