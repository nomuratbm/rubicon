package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

public class AddShapeCommand implements Command{
    Shape shape;
    AppService appService;

    public AddShapeCommand(AppService appService, Shape shape){
        this.shape = shape;
        this.appService = appService;
    }
    @Override
    public void execute() {
        appService.create(shape);
    }

    @Override
    public void undo() {
        appService.delete(shape);
    }

    @Override
    public void redo() {
        appService.create(shape);
    }
}