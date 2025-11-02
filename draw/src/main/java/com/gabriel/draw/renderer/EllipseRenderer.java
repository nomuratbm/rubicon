package com.gabriel.draw.renderer;

import com.gabriel.draw.model.Ellipse;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.renderer.ShapeRenderer;

import java.awt.*;

public class EllipseRenderer extends ShapeRenderer {

    @Override
    public void render(Graphics g,  Shape shape, boolean xor) {
        if(!shape.isVisible()){
            return;
        }
        Ellipse ellipse = (Ellipse) shape;
        int x = shape.getLocation().x;
        int y = shape.getLocation().y;
        int width = shape.getWidth() ;
        int height = shape.getHeight();

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(shape.getThickness()));

        if (xor) {
            g2.setXORMode(shape.getColor());
        } else {
            g2.setColor(shape.getColor());
            if(shape.getFill() != null){
                if(shape.isGradient()) {
                    GradientPaint gp = new GradientPaint(shape.getLocation().x + shape.getStart().x, shape.getLocation().y + shape.getStart().y, shape.getStartColor(), shape.getLocation().x + width + shape.getEnd().x, shape.getLocation().y + shape.getEnd().y + shape.getHeight(), shape.getEndColor());
                    g2.setPaint(gp);
                }
                else{
                    g2.setColor(shape.getFill());
                }
                g2.fillOval(x,y,width, height);
                g2.setColor(shape.getColor());
            }
        }
        g2.drawOval(x, y, width, height);
        super.render(g, shape, xor);
    }
}