package com.gabriel.draw.command;

import com.gabriel.drawfx.command.Command;
import com.gabriel.drawfx.service.AppService;

import java.awt.*;

public class SetFillCommand implements Command {
    Color fill;
    Color prevFill;
    AppService appService;

    public SetFillCommand(AppService appService, Color fill){
        this.appService = appService;
        this.fill = fill;
    }

    @Override
    public void execute() {
        prevFill = appService.getFill();
        appService.setFill(fill);
        if(appService.getView() != null) {
            appService.repaint();
        }
    }

    @Override
    public void undo() {
        appService.setFill(prevFill);
        appService.repaint();
    }

    @Override
    public void redo() {
        appService.setFill(fill);
        appService.repaint();
    }
}