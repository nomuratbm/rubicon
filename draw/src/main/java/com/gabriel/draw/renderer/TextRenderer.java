package com.gabriel.draw.renderer;

import com.gabriel.draw.model.Text;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.renderer.ShapeRenderer;

import java.awt.*;

public class TextRenderer extends ShapeRenderer {

    @Override
    public void render(Graphics g, Shape shape, boolean xor) {
        if(!shape.isVisible()){
            return;
        }

        Text text = (Text) shape;

        int x = shape.getLocation().x;
        int y = shape.getLocation().y;
        int width = shape.getWidth() ;
        int height = shape.getHeight();

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(shape.getThickness()));

        if (xor) {
            g2.setXORMode(shape.getColor());
            g2.drawRect(x, y, width, height);
        } else {
//            g2.setColor(shape.getColor());
            GradientPaint gp = new GradientPaint(x, x, shape.getFill(), x+width, y+height, Color.BLACK);
            g2.setPaint(gp);
            g2.setFont(shape.getFont());
            g2.drawString(shape.getText(), shape.getLocation().x, shape.getLocation().y);
        }
        super.render(g, shape, xor);
    }
}
