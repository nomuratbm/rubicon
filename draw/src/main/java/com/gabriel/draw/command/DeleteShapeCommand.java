package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.model.Drawing;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class DeleteShapeCommand implements Command {
    private List<Shape> deletedShapes;
    private Map<Shape, Integer> shapePositions;
    private AppService appService;

    public DeleteShapeCommand(AppService appService, Shape shape) {
        this.appService = appService;
        this.deletedShapes = new ArrayList<>();
        this.deletedShapes.add(shape);
        this.shapePositions = new HashMap<>();

        Drawing drawing = appService.getDrawing();
        shapePositions.put(shape, drawing.getShapes().indexOf(shape));
    }

    public DeleteShapeCommand(AppService appService, List<Shape> shapes) {
        this.appService = appService;
        this.deletedShapes = new ArrayList<>(shapes);
        this.shapePositions = new HashMap<>();

        Drawing drawing = appService.getDrawing();
        for (Shape shape : shapes) {
            shapePositions.put(shape, drawing.getShapes().indexOf(shape));
        }
    }

    @Override
    public void execute() {
        for (Shape shape : deletedShapes) {
            appService.delete(shape);
        }
    }

    @Override
    public void undo() {
        Drawing drawing = appService.getDrawing();
        for (Shape shape : deletedShapes) {
            Integer position = shapePositions.get(shape);
            if (position != null && position >= 0 && position <= drawing.getShapes().size()) {
                drawing.getShapes().add(position, shape);
            } else {
                drawing.getShapes().add(shape);
            }
        }
    }

    @Override
    public void redo() {
        execute();
    }
}
