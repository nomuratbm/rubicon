package com.gabriel.drawfx.model;

import com.gabriel.drawfx.service.RendererService;
import lombok.Data;

import java.awt.*;
@Data
public abstract class Shape {
    int id;
    private Point location;
    // private Point end;
    int width;
    int height;
    private Color color;
    private Color fill;
    private RendererService rendererService;
    public Shape(Point location) {
        this.setLocation(location);
        width = 0;
        height = 0;
    }
    public Shape(Point location, Point end) {
        this.setLocation(location);
        width = end.x - location.x;
        height = end.y - location.y;
    }
}
