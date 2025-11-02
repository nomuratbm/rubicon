package com.gabriel.draw.renderer;

import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.renderer.ShapeRenderer;

import java.awt.*;


public class LineRenderer extends ShapeRenderer {

    @Override
    public void render(Graphics g, Shape shape, boolean xor) {
        if(!shape.isVisible()){
            return;
        }

        int x = shape.getLocation().x;
        int y = shape.getLocation().y;
        int width = shape.getWidth() ;
        int height = shape.getHeight();

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(shape.getThickness()));

        if(xor) {
            g2.setXORMode(shape.getColor());
        }
        else {
            g2.setColor(shape.getColor());
            g2.drawLine(x,y,x+width, y+height);
            super.render(g, shape, xor);
        }
    }
}