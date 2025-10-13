package com.gabriel.draw.service;

import com.gabriel.drawfx.DrawMode;
import com.gabriel.drawfx.Handle;
import com.gabriel.drawfx.ShapeMode;
import com.gabriel.drawfx.model.Drawing;
import com.gabriel.drawfx.model.Shape;
import com.gabriel.drawfx.service.AppService;
import com.gabriel.drawfx.service.MoverService;
import com.gabriel.drawfx.service.ScalerService;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class DrawingAppService implements AppService {

    final private Drawing drawing;;
    MoverService moverService;
    ScalerService scalerService;
    JPanel drawingView;
    public DrawingAppService(){
        drawing = new Drawing();
        moverService = new MoverService();
        scalerService = new ScalerService();
        drawing.setDrawMode(DrawMode.Idle);
        drawing.setShapeMode(ShapeMode.Line);
        drawing.setColor(Color.RED);
        drawing.setFill(new Color(0, 0, 0, 0));
    }

    @Override
    public void undo() {

    }

    @Override
    public void redo() {

    }

    @Override
    public ShapeMode getShapeMode() {
        return drawing.getShapeMode();
    }

    @Override
    public void setShapeMode(ShapeMode shapeMode) {
        drawing.setShapeMode(shapeMode);
    }

    @Override
    public DrawMode getDrawMode() {
        return drawing.getDrawMode();
    }

    @Override
    public void setDrawMode(DrawMode drawMode) {
        this.drawing.setDrawMode(drawMode);
    }

    @Override
    public Color getColor() {
        return drawing.getColor();
    }

    @Override
    public void setColor(Color color) {
        drawing.setColor(color);
    }

    @Override
    public Color getFill(){
        return drawing.getFill();
    }

    @Override
    public void setFill(Color color) {
        drawing.setFill(color);
    }

    @Override
    public void move(Shape shape, Point newLoc) {
        moverService.move(shape, newLoc);}

    @Override
    public void scale(Shape shape, Point originalLocation, int originalWidth,
                      int originalHeight, Point mousePoint, Handle handle) {
        scalerService.scaleWithHandle(shape, originalLocation, originalWidth, originalHeight, mousePoint, handle);
    }

    @Override
    public void create(Shape shape) {
        shape.setId(this.drawing.getShapes().size());
        this.drawing.getShapes().add(shape);
    }

    @Override
    public void delete(Shape shape) {
        drawing.getShapes().remove(shape);
    }

    @Override
    public void select(Shape shape) {
        if(shape != null && !drawing.getSelectedShapes().contains(shape)) {
            drawing.getSelectedShapes().add(shape);
        }
    }

    @Override
    public void clear() {
        drawing.getSelectedShapes().clear();
    }

    @Override
    public List<Shape> getSelectedShapes() {
        return new ArrayList<>(drawing.getSelectedShapes());
    }

    @Override
    public Shape findShapeAt(Point point) {
        List<Shape> shapes = drawing.getShapes();
        for (int i = shapes.size() - 1; i >= 0; i--) {
            Shape shape = shapes.get(i);
            if (isPointInShape(shape, point)) {
                return shape;
            }
        }
        return null;
    }

    @Override
    public void close() {
        System.exit(0);
    }

    @Override
    public Object getModel() {
        return drawing;
    }

    @Override
    public JPanel getView() {
        return drawingView;
    }

    @Override
    public void setView(JPanel panel) {
        this.drawingView = panel;
    }

    @Override
    public void repaint() {
        drawingView.repaint();
    }

    private boolean isPointInShape(Shape shape, Point point) {
        int x = shape.getLocation().x;
        int y = shape.getLocation().y;
        int width = shape.getWidth();
        int height = shape.getHeight();

        if (width < 0) {
            x += width;
            width = -width;
        }
        if (height < 0) {
            y += height;
            height = -height;
        }

        int tolerance = 5;
        return point.x >= x - tolerance && point.x <= x + width + tolerance &&
                point.y >= y - tolerance && point.y <= y + height + tolerance;
    }
}
