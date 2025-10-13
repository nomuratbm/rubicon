package com.gabriel.drawfx.service;

import com.gabriel.drawfx.model.Shape;

import java.awt.*;

public final class  ScalerService {
    void scale(Shape shape, Point newEnd){
        shape.setWidth(newEnd.x - shape.getLocation().y);
        shape.setHeight(newEnd.y - shape.getLocation().y);
    }
    void scale(Shape shape, Point start, Point end){
        int dx = end.x - start.x;
        int dy = end.y - start.y;
        shape.getLocation().x += dx;
        shape.getLocation().y += dy;
        shape.setWidth(shape.getWidth() + dx);
        shape.setHeight(shape.getHeight());
    }
}