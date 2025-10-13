package com.gabriel.drawfx.service;

import com.gabriel.drawfx.model.Shape;

import java.awt.*;

public final class ScalerService {
    public void scale(Shape shape, Point newEnd) {
        shape.setWidth(newEnd.x - shape.getLocation().x);
        shape.setHeight(newEnd.y - shape.getLocation().y);
    }

    public void scale(Shape shape, Point start, Point end) {
        int dx = end.x - start.x;
        int dy = end.y - start.y;
        shape.getLocation().x += dx;
        shape.getLocation().y += dy;
        shape.setWidth(shape.getWidth() + dx);
        shape.setHeight(shape.getHeight() + dy);
    }
}