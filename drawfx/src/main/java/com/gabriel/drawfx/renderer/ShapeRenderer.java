package com.gabriel.drawfx.renderer;

import com.gabriel.drawfx.model.Shape;

import java.awt.*;

public class ShapeRenderer implements Renderer {

    @Override
    public void render(Graphics g,  Shape shape, boolean xor) {
        if(shape.isSelected()){
            Point loc = shape.getLocation();
            int width = shape.getWidth();
            int height = shape.getHeight();
            int r = 5;

            if(xor){
                 g.setXORMode(shape.getColor());
            }
            else {
                g.setColor(shape.getColor());
            }

            g.drawRect(loc.x-r,loc.y-r, 2*r,2*r);
            g.drawRect(loc.x-r,loc.y+height-r, 2*r, 2*r);
            g.drawRect(loc.x + width -r,loc.y -r, 2*r, 2*r);
            g.drawRect(loc.x + width -r,loc.y+height-r, 2*r, 2*r);

            g.drawRect(loc.x + width/2 -r,loc.y-r, 2*r, 2*r);
            g.drawRect(loc.x -r,loc.y+height/2-r, 2*r, 2*r);
            g.drawRect(loc.x + width -r,loc.y+height/2-r, 2*r, 2*r);
            g.drawRect(loc.x + width/2 -r,loc.y+height-r, 2*r, 2*r);
            g.drawRect(loc.x + width -r,loc.y+height/2-r, 2*r, 2*r);
        }
    }
}
