package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoveShapeCommand implements Command {
    private final AppService appService;
    private final List<Shape> shapes;
    private final Point delta;
    private final Map<Shape, Point> originalLocations;

    public MoveShapeCommand(AppService appService, List<Shape> shapes, Point delta) {
        this.appService = appService;
        this.shapes = shapes;
        this.delta = delta;
        this.originalLocations = new HashMap<>();
    }

    @Override
    public void execute() {
        for (Shape shape : shapes) {
            if (!originalLocations.containsKey(shape)) {
                originalLocations.put(shape, new Point(shape.getLocation()));
            }
            Point newLoc = new Point(
                    shape.getLocation().x + delta.x,
                    shape.getLocation().y + delta.y
            );
            appService.move(shape, newLoc);
        }
    }

    @Override
    public void undo() {
        for (Shape shape : shapes) {
            Point originalLoc = originalLocations.get(shape);
            if (originalLoc != null) {
                appService.move(shape, new Point(originalLoc));
            }
        }
    }

    @Override
    public void redo() {
        for (Shape shape : shapes) {
            Point originalLoc = originalLocations.get(shape);
            if (originalLoc != null) {
                Point newLoc = new Point(
                        originalLoc.x + delta.x,
                        originalLoc.y + delta.y
                );
                appService.move(shape, newLoc);
            }
        }
    }
}
