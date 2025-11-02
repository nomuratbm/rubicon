package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

import java.awt.*;

public class ScaleShapeCommand implements Command {
    private Shape shape;
    private Point startPoint;
    private Point endPoint;
    private AppService appService;

    private Point originalLocation;
    private int originalWidth;
    private int originalHeight;
    private Point originalStart;
    private Point originalEnd;

    public ScaleShapeCommand(AppService appService, Shape shape, Point startPoint, Point endPoint) {
        this.appService = appService;
        this.shape = shape;
        this.startPoint = new Point(startPoint);
        this.endPoint = new Point(endPoint);

        this.originalLocation = new Point(shape.getLocation());
        this.originalWidth = shape.getWidth();
        this.originalHeight = shape.getHeight();

        if (shape.getStart() != null) {
            this.originalStart = new Point(shape.getStart());
        }
        if (shape.getEnd() != null) {
            this.originalEnd = new Point(shape.getEnd());
        }
    }

    @Override
    public void execute() {
        appService.scale(shape, startPoint, endPoint);
    }

    @Override
    public void undo() {
        shape.setLocation(new Point(originalLocation));
        shape.setWidth(originalWidth);
        shape.setHeight(originalHeight);

        if (originalStart != null) {
            shape.setStart(new Point(originalStart));
        }
        if (originalEnd != null) {
            shape.setEnd(new Point(originalEnd));
        }
    }

    @Override
    public void redo() {
        execute();
    }
}
