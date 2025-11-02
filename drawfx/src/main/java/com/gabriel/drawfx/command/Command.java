package com.gabriel.drawfx.command;

public interface Command {
    void execute();
    void undo();
    void redo();
}
