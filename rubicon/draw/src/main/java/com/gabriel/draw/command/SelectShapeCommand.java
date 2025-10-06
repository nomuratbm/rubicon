package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

import java.util.ArrayList;
import java.util.List;

public class SelectShapeCommand implements Command {
    private final AppService appService;
    private final Shape shape;
    private final boolean addToSelection;
    private List<Shape> previousSelection;

    public SelectShapeCommand(AppService appService, Shape shape, boolean addToSelection) {
        this.appService = appService;
        this.shape = shape;
        this.addToSelection = addToSelection;
    }

    @Override
    public void execute() {
        previousSelection = new ArrayList<>(appService.getSelectedShapes());

        if(!addToSelection) {
            appService.clear();
        }
        if(shape != null){
            appService.select(shape);
        }
    }

    @Override
    public void undo() {
        appService.clear();
        for (Shape s : previousSelection) {
            appService.select(s);
        }
    }

    @Override
    public void redo() {
        execute();
    }
}
