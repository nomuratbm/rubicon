package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

import java.awt.*;

public class SetFontCommand implements Command {
    private Shape targetShape;
    private Font oldFont;
    private Font newFont;
    private boolean isDefaultChange;
    private AppService appService;

    public SetFontCommand(AppService appService, Font newFont) {
        this.appService = appService;
        this.targetShape = appService.getSelectedShape();
        this.newFont = newFont;

        if (targetShape == null) {
            this.isDefaultChange = true;
            this.oldFont = appService.getDrawing().getFont();
        } else {
            this.isDefaultChange = false;
            this.oldFont = targetShape.getFont();
        }
    }

    @Override
    public void execute() {
        if (isDefaultChange) {
            appService.getDrawing().setFont(newFont);
        } else {
            targetShape.setFont(newFont);
        }
    }

    @Override
    public void undo() {
        if (isDefaultChange) {
            appService.getDrawing().setFont(oldFont);
        } else {
            targetShape.setFont(oldFont);
        }
    }

    @Override
    public void redo() {
        execute();
    }
}
