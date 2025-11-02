package com.gabriel.drawfx.model;

import com.gabriel.drawfx.DrawMode;
import com.gabriel.drawfx.ShapeMode;
import lombok.Data;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
@Data
public class Drawing {
    Point location;
    int id;
    private String filename = null;
    private String imageFilename = null;
    private Color color;
    private Color fill;

    private Color startColor = Color.WHITE;
    private Color endColor = Color.RED;
    boolean isGradient = false;
    boolean visible = false;
    Point start;
    Point end;
    int r;
    int thickness  = 1;
    private ShapeMode shapeMode = ShapeMode.Rectangle;
    private DrawMode drawMode = DrawMode.Idle;
    private int SearchRadius = 5;
    private Font font = null;
    int width = 0;
    int height = 0;
    List<Shape> shapes;
    private Shape selectedShape = null;
    private String text = "Default text";
    public Drawing(){
        location  = new Point(0,0);
        color = Color.RED;
        fill = Color.WHITE;
        font = new Font("Serif", Font.BOLD, 24);
        shapes = new ArrayList<>();
        this.start = new Point(0,0);
        this.end = new Point(100,0);
    }
}
