package com.gabriel.draw.command;

import com.gabriel.drawfx.service.ScalerService;
import com.gabriel.drawfx.Handle;
import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

import java.awt.*;

public class ScaleShapeCommand implements Command {
    private final AppService appService;
    private final Shape shape;
    private final Point originalLocation;
    private final int originalWidth;
    private final int originalHeight;
    private final Point mousePoint;
    private final Handle handle;

    private Point newLocation;
    private int newWidth;
    private int newHeight;
    private boolean hasExecuted = false;

    public ScaleShapeCommand(AppService appService, Shape shape,
                             Point originalLocation, int originalWidth, int originalHeight,
                             Point mousePoint, Handle handle) {
        this.appService = appService;
        this.shape = shape;
        this.originalLocation = new Point(originalLocation);
        this.originalWidth = originalWidth;
        this.originalHeight = originalHeight;
        this.mousePoint = mousePoint;
        this.handle = handle;
    }

    @Override
    public void execute() {

        shape.setLocation(new Point(originalLocation));
        shape.setWidth(originalWidth);
        shape.setHeight(originalHeight);

        ScalerService.scaleWithHandle(shape, originalLocation,
                originalWidth, originalHeight, mousePoint, handle);

        if (!hasExecuted) {
            newLocation = new Point(shape.getLocation());
            newWidth = shape.getWidth();
            newHeight = shape.getHeight();
            hasExecuted = true;
        }

        appService.repaint();
    }

    @Override
    public void undo() {
        shape.setLocation(new Point(originalLocation));
        shape.setWidth(originalWidth);
        shape.setHeight(originalHeight);
        appService.repaint();
    }

    @Override
    public void redo() {
        shape.setLocation(new Point(newLocation));
        shape.setWidth(newWidth);
        shape.setHeight(newHeight);
        appService.repaint();
    }
}