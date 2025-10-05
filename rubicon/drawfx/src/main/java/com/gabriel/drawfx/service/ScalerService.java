package com.gabriel.drawfx.service;

import com.gabriel.drawfx.model.Shape;

import java.awt.*;

public final class  ScalerService {
     public void scale(Shape shape, Point newEnd){
        shape.setWidth(newEnd.x - shape.getLocation().x);
        shape.setHeight(newEnd.y - shape.getLocation().y);
    }
}
