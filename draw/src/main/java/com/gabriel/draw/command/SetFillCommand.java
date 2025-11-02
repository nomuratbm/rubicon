package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SetFillCommand implements Command {
    Color fill;
    Color prevFill;
    Map<Shape, Color> prevFills;
    boolean affectsShapes;
    AppService appService;

    public SetFillCommand(AppService appService, Color fill) {
        this.appService = appService;
        this.fill = fill;
        this.prevFills = new HashMap<>();

        this.prevFill = appService.getFill();
        List<Shape> selectedShapes = appService.getSelectedShapes();
        this.affectsShapes = !selectedShapes.isEmpty();

        for (Shape shape : selectedShapes) {
            prevFills.put(shape, shape.getFill());
        }
    }

    @Override
    public void execute() {
        if (affectsShapes) {
            for (Shape shape : prevFills.keySet()) {
                shape.setFill(fill);
            }
        } else {
            appService.getDrawing().setFill(fill);
        }
    }

    @Override
    public void undo() {
        if (affectsShapes) {
            for (Map.Entry<Shape, Color> entry : prevFills.entrySet()) {
                entry.getKey().setFill(entry.getValue());
            }
        } else {
            appService.getDrawing().setFill(prevFill);
        }
    }

    @Override
    public void redo() {
        execute();
    }
}
