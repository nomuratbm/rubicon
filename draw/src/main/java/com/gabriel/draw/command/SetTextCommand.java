package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

public class SetTextCommand implements Command {
    private Shape targetShape;
    private String oldText;
    private String newText;
    private boolean isDefaultChange;
    private AppService appService;

    public SetTextCommand(AppService appService, String newText) {
        this.appService = appService;
        this.targetShape = appService.getSelectedShape();
        this.newText = newText;

        if (targetShape == null) {
            this.isDefaultChange = true;
            this.oldText = appService.getDrawing().getText();
        } else {
            this.isDefaultChange = false;
            this.oldText = targetShape.getText();
        }
    }

    @Override
    public void execute() {
        if (isDefaultChange) {
            appService.getDrawing().setText(newText);
        } else {
            targetShape.setText(newText);
        }
    }

    @Override
    public void undo() {
        if (isDefaultChange) {
            appService.getDrawing().setText(oldText);
        } else {
            targetShape.setText(oldText);
        }
    }

    @Override
    public void redo() {
        execute();
    }
}
