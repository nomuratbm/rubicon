package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

public class SetDimensionsCommand implements Command {
    private Shape targetShape;
    private int oldWidth;
    private int oldHeight;
    private Integer newWidth;
    private Integer newHeight;
    private AppService appService;

    // Constructor for width change only
    public static SetDimensionsCommand forWidth(AppService appService, int newWidth) {
        return new SetDimensionsCommand(appService, newWidth, null);
    }

    // Constructor for height change only
    public static SetDimensionsCommand forHeight(AppService appService, int newHeight) {
        return new SetDimensionsCommand(appService, null, newHeight);
    }

    // Constructor for both width and height
    public static SetDimensionsCommand forBoth(AppService appService, int newWidth, int newHeight) {
        return new SetDimensionsCommand(appService, newWidth, newHeight);
    }

    private SetDimensionsCommand(AppService appService, Integer newWidth, Integer newHeight) {
        this.appService = appService;
        this.targetShape = appService.getSelectedShape();
        this.newWidth = newWidth;
        this.newHeight = newHeight;

        if (targetShape == null) {
            this.oldWidth = appService.getDrawing().getWidth();
            this.oldHeight = appService.getDrawing().getHeight();
        } else {
            this.oldWidth = targetShape.getWidth();
            this.oldHeight = targetShape.getHeight();
        }
    }

    @Override
    public void execute() {
        if (targetShape == null) {
            if (newWidth != null) appService.getDrawing().setWidth(newWidth);
            if (newHeight != null) appService.getDrawing().setHeight(newHeight);
        } else {
            if (newWidth != null) targetShape.setWidth(newWidth);
            if (newHeight != null) targetShape.setHeight(newHeight);
        }
    }

    @Override
    public void undo() {
        if (targetShape == null) {
            appService.getDrawing().setWidth(oldWidth);
            appService.getDrawing().setHeight(oldHeight);
        } else {
            targetShape.setWidth(oldWidth);
            targetShape.setHeight(oldHeight);
        }
    }

    @Override
    public void redo() {
        execute();
    }
}
