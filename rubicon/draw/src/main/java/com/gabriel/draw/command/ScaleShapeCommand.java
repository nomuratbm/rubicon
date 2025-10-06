package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

import java.awt.*;

public class ScaleShapeCommand implements Command {
    private final AppService appService;
    private final Shape shape;
    private final Point newEnd;
    private Point originalLocation;
    private int originalWidth;
    private int originalHeight;

    public ScaleShapeCommand(AppService appService, Shape shape, Point newEnd) {
        this.appService = appService;
        this.shape = shape;
        this.newEnd = newEnd;
    }

    @Override
    public void execute() {
        // Save original state
        originalLocation = new Point(shape.getLocation());
        originalWidth = shape.getWidth();
        originalHeight = shape.getHeight();

        appService.scale(shape, newEnd);
    }

    @Override
    public void undo() {
        shape.setLocation(originalLocation);
        shape.setWidth(originalWidth);
        shape.setHeight(originalHeight);
    }

    @Override
    public void redo() {
        appService.scale(shape, newEnd);
    }
}