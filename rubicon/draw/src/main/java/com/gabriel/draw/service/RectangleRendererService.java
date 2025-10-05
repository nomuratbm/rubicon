package com.gabriel.draw.service;

import com.gabriel.draw.model.Rectangle;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.RendererService;

import java.awt.*;

public class RectangleRendererService implements RendererService {
    @Override
    public void render(Graphics g, Shape shape, boolean xor) {
        Rectangle rectangle = (Rectangle) shape;
        if(xor) {
            g.setXORMode(shape.getColor());
        }
        else {
            g.setColor(shape.getColor());
        }
        if(!xor && shape.getFill() != null && shape.getFill().getAlpha() > 0) {
            g.setColor(shape.getFill());
            g.fillRect(rectangle.getLocation().x, rectangle.getLocation().y,
                    shape.getWidth(), shape.getHeight());
            g.setColor(shape.getColor());
        }

        int x = rectangle.getLocation().x;
        int y = rectangle.getLocation().y;
        int width = shape.getWidth();
        int height = shape.getHeight();

        if(width < 0) {
            x = x + width;
            width = -width;
        }

        if(height < 0) {
            y = y + height;
            height = -height;
        }
        g.drawRect(x, y, width, height);
    }
}
