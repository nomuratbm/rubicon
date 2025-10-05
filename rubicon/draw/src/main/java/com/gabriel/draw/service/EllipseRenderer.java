package com.gabriel.draw.service;

import com.gabriel.draw.model.Ellipse;
import com.gabriel.draw.model.Rectangle;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.RendererService;

import java.awt.*;

public class EllipseRenderer implements RendererService {
    @Override
    public void render(Graphics g, Shape shape, boolean xor) {
        Ellipse ellipse = (Ellipse) shape;
        if (xor) {
            g.setXORMode(shape.getColor());
        } else {
            g.setColor(shape.getColor());
        }
        if(!xor && shape.getFill() != null && shape.getFill().getAlpha() > 0) {
            g.setColor(shape.getFill());
            g.fillOval(ellipse.getLocation().x, ellipse.getLocation().y,
                    shape.getWidth(), shape.getHeight());
            g.setColor(shape.getColor());
        }
        g.drawOval(ellipse.getLocation().x, ellipse.getLocation().y,
                shape.getWidth(), shape.getHeight());
    }
}