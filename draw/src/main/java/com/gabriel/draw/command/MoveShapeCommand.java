package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MoveShapeCommand implements Command {
    private Point startPoint;
    private Point endPoint;
    private Map<Shape, Point> originalLocations;
    private List<Shape> movedShapes;
    private int deltaX;
    private int deltaY;
    private AppService appService;

    public MoveShapeCommand(AppService appService, Point startPoint, Point endPoint) {
        this.appService = appService;
        this.startPoint = new Point(startPoint);
        this.endPoint = new Point(endPoint);
        this.originalLocations = new HashMap<>();

        this.deltaX = endPoint.x - startPoint.x;
        this.deltaY = endPoint.y - startPoint.y;

        this.movedShapes = new ArrayList<>(appService.getSelectedShapes());
        for (Shape shape : movedShapes) {
            originalLocations.put(shape, new Point(shape.getLocation()));
        }
    }

    @Override
    public void execute() {
        for (Shape shape : movedShapes) {
            Point loc = shape.getLocation();
            loc.x += deltaX;
            loc.y += deltaY;
        }
    }

    @Override
    public void undo() {
        for (Shape shape : movedShapes) {
            Point originalLocation = originalLocations.get(shape);
            if (originalLocation != null) {
                shape.setLocation(new Point(originalLocation));
            }
        }
    }

    @Override
    public void redo() {
        execute();
    }
}
