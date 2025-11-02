package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

import java.awt.*;

public class SetGradientCommand implements Command {
    private Shape targetShape;
    private boolean oldIsGradient;
    private Color oldStartColor;
    private Color oldEndColor;
    private boolean newIsGradient;
    private Color newStartColor;
    private Color newEndColor;
    private boolean isDefaultChange;
    private AppService appService;

    public SetGradientCommand(AppService appService, boolean isGradient, Color startColor, Color endColor) {
        this.appService = appService;
        this.targetShape = appService.getSelectedShape();
        this.newIsGradient = isGradient;
        this.newStartColor = startColor;
        this.newEndColor = endColor;

        if (targetShape == null) {
            this.isDefaultChange = true;
            this.oldIsGradient = appService.getDrawing().isGradient();
            this.oldStartColor = appService.getDrawing().getStartColor();
            this.oldEndColor = appService.getDrawing().getEndColor();
        } else {
            this.isDefaultChange = false;
            this.oldIsGradient = targetShape.isGradient();
            this.oldStartColor = targetShape.getStartColor();
            this.oldEndColor = targetShape.getEndColor();
        }
    }

    @Override
    public void execute() {
        if (isDefaultChange) {
            appService.getDrawing().setGradient(newIsGradient);
            if (newStartColor != null) appService.getDrawing().setStartColor(newStartColor);
            if (newEndColor != null) appService.getDrawing().setEndColor(newEndColor);
        } else {
            targetShape.setGradient(newIsGradient);
            if (newStartColor != null) targetShape.setStartColor(newStartColor);
            if (newEndColor != null) targetShape.setEndColor(newEndColor);
        }
    }

    @Override
    public void undo() {
        if (isDefaultChange) {
            appService.getDrawing().setGradient(oldIsGradient);
            appService.getDrawing().setStartColor(oldStartColor);
            appService.getDrawing().setEndColor(oldEndColor);
        } else {
            targetShape.setGradient(oldIsGradient);
            targetShape.setStartColor(oldStartColor);
            targetShape.setEndColor(oldEndColor);
        }
    }

    @Override
    public void redo() {
        execute();
    }
}
