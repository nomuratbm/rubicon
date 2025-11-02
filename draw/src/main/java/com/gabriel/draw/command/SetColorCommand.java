package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.service.AppService;
import com.gabriel.drawfx.model.Shape;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SetColorCommand implements Command {
    Color color;
    Color prevColor;
    Map<Shape, Color> prevColors;
    AppService appService;
    boolean affectsShapes;

    public SetColorCommand(AppService appService, Color color) {
        this.appService = appService;
        this.color = color;
        this.prevColors = new HashMap<>();

        this.prevColor = appService.getColor();
        List<Shape> selectedShapes = appService.getSelectedShapes();
        this.affectsShapes = !selectedShapes.isEmpty();

        for (Shape shape : selectedShapes) {
            prevColors.put(shape, shape.getColor());
        }
    }

    @Override
    public void execute() {
        if (affectsShapes) {
            for (Shape shape : prevColors.keySet()) {
                shape.setColor(color);
            }
        }
        else {
            appService.getDrawing().setColor(color);
        }
    }

    @Override
    public void undo() {
        if (affectsShapes) {
            for (Map.Entry<Shape, Color> entry : prevColors.entrySet()) {
                entry.getKey().setColor(entry.getValue());
            }
        }
        else {
            appService.getDrawing().setColor(prevColor);
        }
    }

    @Override
    public void redo() {
        execute();
    }
}
