package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;
import com.gabriel.drawfx.service.ScalerService;

import java.awt.*;

public class ScaleShapeCommand implements Command {
    private final AppService appService;
    private final Shape shape;
    private final Point newEnd;
    private Point originalLocation;
    private int originalWidth;
    private int originalHeight;
    private final ScalerService scalerService;

    public ScaleShapeCommand(AppService appService, Shape shape, Point newEnd) {
        this.appService = appService;
        this.shape = shape;
        this.newEnd = newEnd;
        this.scalerService = new ScalerService();
    }

    @Override
    public void execute() {
        if (originalLocation == null) {
            originalLocation = new Point(shape.getLocation());
            originalWidth = shape.getWidth();
            originalHeight = shape.getHeight();
        }

        scalerService.scale(shape, newEnd);
        appService.repaint();
    }

    @Override
    public void undo() {
        shape.setLocation(originalLocation);
        shape.setWidth(originalWidth);
        shape.setHeight(originalHeight);
        appService.repaint();
    }

    @Override
    public void redo() {
        scalerService.scale(shape, newEnd);
        appService.repaint();
    }
}