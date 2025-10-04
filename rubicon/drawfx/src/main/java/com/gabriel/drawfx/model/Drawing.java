package com.gabriel.drawfx.model;

import com.gabriel.drawfx.DrawMode;
import com.gabriel.drawfx.ShapeMode;
import lombok.Data;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
@Data
public class Drawing {

    private Color color;
    private Color fill;
    private ShapeMode shapeMode = ShapeMode.Rectangle;
    private DrawMode drawMode = DrawMode.Idle;
    List<Shape> shapes;
    List<Shape> selectedShapes;
    public Drawing(){
        color = Color.RED;
        fill = Color.WHITE;
        selectedShapes = new ArrayList<>();
        shapes = new ArrayList<>();
    }
}
