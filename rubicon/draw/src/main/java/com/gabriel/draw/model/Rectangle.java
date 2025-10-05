package com.gabriel.draw.model;

import com.gabriel.draw.service.RectangleRendererService;
import com.gabriel.drawfx.model.Shape;

import java.awt.*;

public class Rectangle extends Shape {

    public Rectangle(Point start, Point end){
        super(start, end);
        this.setColor(Color.RED);
        this.setFill(new Color(0,0,0,0));
        this.setRendererService(new RectangleRendererService());
    }
}
