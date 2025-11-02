package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

import java.awt.*;

public class SetLocationCommand implements Command {
    private Shape targetShape;
    private Point oldLocation;
    private int newX;
    private int newY;
    private boolean isXChange;
    private boolean isYChange;
    private AppService appService;

    public static SetLocationCommand forX(AppService appService, int newX) {
        return new SetLocationCommand(appService, newX, -1, true, false);
    }

    public static SetLocationCommand forY(AppService appService, int newY) {
        return new SetLocationCommand(appService, -1, newY, false, true);
    }

    public static SetLocationCommand forXY(AppService appService, int newX, int newY) {
        return new SetLocationCommand(appService, newX, newY, true, true);
    }

    private SetLocationCommand(AppService appService, int newX, int newY, boolean isXChange, boolean isYChange) {
        this.appService = appService;
        this.targetShape = appService.getSelectedShape();
        this.newX = newX;
        this.newY = newY;
        this.isXChange = isXChange;
        this.isYChange = isYChange;

        if (targetShape == null) {
            this.oldLocation = new Point(appService.getDrawing().getLocation());
        } else {
            this.oldLocation = new Point(targetShape.getLocation());
        }
    }

    @Override
    public void execute() {
        if (targetShape == null) {
            Point loc = appService.getDrawing().getLocation();
            if (isXChange) loc.x = newX;
            if (isYChange) loc.y = newY;
        } else {
            Point loc = targetShape.getLocation();
            if (isXChange) loc.x = newX;
            if (isYChange) loc.y = newY;
        }
    }

    @Override
    public void undo() {
        if (targetShape == null) {
            appService.getDrawing().setLocation(new Point(oldLocation));
        } else {
            targetShape.setLocation(new Point(oldLocation));
        }
    }

    @Override
    public void redo() {
        execute();
    }
}
