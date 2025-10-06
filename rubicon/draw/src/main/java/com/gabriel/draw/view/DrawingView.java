package com.gabriel.draw.view;

import com.gabriel.draw.service.SelectionRendererService;
import com.gabriel.drawfx.model.Drawing;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DrawingView extends JPanel {
    AppService appService;

    public DrawingView(AppService appService) {
        this.appService = appService;
        appService.setView(this);
        setBackground(Color.WHITE);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Drawing drawing = (Drawing) appService.getModel();

        for (Shape shape : drawing.getShapes()) {
            shape.getRendererService().render(g, shape, false);
        }
        List<Shape> selectedShapes = appService.getSelectedShapes();
        if (!selectedShapes.isEmpty()) {
            if (selectedShapes.size() == 1) {
                SelectionRendererService.renderSelection(g, selectedShapes.get(0));
            } else {
                SelectionRendererService.renderSelection(g, selectedShapes);
            }
        }
    }
}