package com.gabriel.draw.controller;

import com.gabriel.draw.component.PropertySheet;
import com.gabriel.draw.model.*;
import com.gabriel.draw.model.Image;
import com.gabriel.draw.model.Rectangle;
import com.gabriel.draw.view.DrawingStatusPanel;
import com.gabriel.drawfx.DrawMode;
import com.gabriel.drawfx.model.Drawing;
import com.gabriel.drawfx.util.Normalizer;
import com.gabriel.drawfx.SelectionMode;
import com.gabriel.drawfx.ShapeMode;
import com.gabriel.draw.view.DrawingView;
import com.gabriel.drawfx.service.AppService;
import com.gabriel.drawfx.model.Shape;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;

public class DrawingController  implements MouseListener, MouseMotionListener, KeyListener {
    Point start;
    private Point end;
    private Point click;

    private Map<Shape, Point> originalLocations = new HashMap<>();
    private Point originalShapeLocation;
    private int originalShapeWidth;
    private int originalShapeHeight;
    private Point originalShapeStart;
    private Point originalShapeEnd;
    private boolean isDragging = false;

    private final AppService appService;
    private final Drawing drawing;

    @Setter
    private DrawingView drawingView;

    @Setter
    private DrawingStatusPanel drawingStatusPanel;

    @Setter
    private PropertySheet propertySheet;

    private Shape currentShape = null;

     public DrawingController(AppService appService, DrawingView drawingView){
       this.appService = appService;
       this.drawing = appService.getDrawing();
       this.drawingView = drawingView;
       drawingView.addMouseListener(this);
       drawingView.addMouseMotionListener(this);
     }
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(appService.getDrawMode() == DrawMode.Idle) {
            start = e.getPoint();
            click = new Point(start);
            isDragging = false;

            ShapeMode currentShapeMode = appService.getShapeMode();
            if(currentShapeMode == ShapeMode.Select) {
                appService.search(start, !e.isControlDown());
                originalLocations.clear();
                Shape selectedShape = drawing.getSelectedShape();
                if (selectedShape != null) {
                    if (selectedShape.getSelectionMode() == SelectionMode.None) {
                        List<Shape> shapes = drawing.getShapes();
                        for (Shape shape : shapes) {
                            if (shape.isSelected()) {
                                originalLocations.put(shape, new Point(shape.getLocation()));
                            }
                        }
                    } else {
                        originalShapeLocation = new Point(selectedShape.getLocation());
                        originalShapeWidth = selectedShape.getWidth();
                        originalShapeHeight = selectedShape.getHeight();
                        if (selectedShape.getStart() != null) {
                            originalShapeStart = new Point(selectedShape.getStart());
                        }
                        if (selectedShape.getEnd() != null) {
                            originalShapeEnd = new Point(selectedShape.getEnd());
                        }
                    }
                }
            }
            else {
                if(currentShape!=null){
                    currentShape.setSelected(false);
                }
                switch (currentShapeMode) {
                    case Line:
                        currentShape = new Line(start);
                        currentShape.setColor(appService.getColor());
                        currentShape.getRendererService().render(drawingView.getGraphics(), currentShape, false);
                        break;
                    case Rectangle:
                        currentShape = new Rectangle(start);
                        currentShape.setColor(appService.getColor());
                        currentShape.getRendererService().render(drawingView.getGraphics(), currentShape, false);
                        break;
                    case Text:
                        currentShape = new Text(start);
                        currentShape.setColor(appService.getColor());
                        currentShape.setText(drawing.getText());
                        currentShape.getRendererService().render(drawingView.getGraphics(), currentShape, true);
                        appService.setDrawMode(DrawMode.MousePressed);
                        break;
                    case Ellipse:
                        currentShape = new Ellipse(start);
                        currentShape.setColor(appService.getColor());
                        currentShape.getRendererService().render(drawingView.getGraphics(), currentShape, false);
                        break;
                    case Image:
                        currentShape = new Image(start);
                        currentShape.setImageFilename(drawing.getImageFilename());
                        currentShape.setColor(appService.getColor());
                        currentShape.setThickness(appService.getThickness());
                }

/*                if(currentShape!=null) {
                    currentShape.getRendererService().render(drawingView.getGraphics(), currentShape, false);
                }
  */          }
            appService.setDrawMode(DrawMode.MousePressed);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        end = e.getPoint();
        if(appService.getDrawMode() == DrawMode.MousePressed) {
            if (appService.getShapeMode() == ShapeMode.Select) {
                Shape selectedShape = drawing.getSelectedShape();
                if (selectedShape != null && isDragging) {
                    if (selectedShape.getSelectionMode() == SelectionMode.None) {
                        for (Map.Entry<Shape, Point> entry : originalLocations.entrySet()) {
                            entry.getKey().setLocation(new Point(entry.getValue()));
                        }
                        appService.move(click, end);
                    } else {
                        selectedShape.setLocation(new Point(originalShapeLocation));
                        selectedShape.setWidth(originalShapeWidth);
                        selectedShape.setHeight(originalShapeHeight);
                        if (originalShapeStart != null) {
                            selectedShape.setStart(new Point(originalShapeStart));
                        }
                        if (originalShapeEnd != null) {
                            selectedShape.setEnd(new Point(originalShapeEnd));
                        }
                        appService.scale(selectedShape, click, end);
                        Normalizer.normalize(selectedShape);
                    }
                    drawingView.repaint();
                }
            }
            else {
                currentShape.getRendererService().render(drawingView.getGraphics(), currentShape, true);
                appService.scale(currentShape, end);
                currentShape.setText(drawing.getText());
                currentShape.setFont(drawing.getFont());
                currentShape.setGradient(drawing.isGradient());
                currentShape.setFill(drawing.getFill());
                currentShape.setStartColor(drawing.getStartColor());
                currentShape.setEndColor(drawing.getEndColor());
                Normalizer.normalize(currentShape);
                appService.create(currentShape);
                currentShape.setSelected(true);
//              currentShape.getRendererService().render(drawingView.getGraphics(), currentShape, false);
                drawing.setSelectedShape(currentShape);
                drawing.setShapeMode(ShapeMode.Select);
                drawingView.repaint();
            }
            appService.setDrawMode(DrawMode.Idle);
            isDragging = false;
        }
        propertySheet.populateTable(appService);
        drawingView.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(appService.getDrawMode() == DrawMode.MousePressed) {
            isDragging = true;
            end = e.getPoint();

            if(drawing.getShapeMode() == ShapeMode.Select){
                Shape selectedShape = drawing.getSelectedShape();
                if(selectedShape != null){
                    if(selectedShape.getSelectionMode() == SelectionMode.None){
                        List<Shape> shapes = drawing.getShapes();
                        int dx = end.x - start.x;
                        int dy = end.y - start.y;

                        for(Shape shape : shapes) {
                            if (shape.isSelected()) {
                                shape.getRendererService().render(drawingView.getGraphics(), shape, true);
                                shape.getLocation().x += dx;
                                shape.getLocation().y += dy;
                                shape.getRendererService().render(drawingView.getGraphics(), shape, true);
                            }
                        }
                    }
                    else {
                        int dx = end.x - start.x;
                        int dy = end.y - start.y;
                        int height = selectedShape.getHeight();
                        int width = selectedShape.getWidth();

                        if(selectedShape.getSelectionMode() == SelectionMode.UpperLeft) {
                            selectedShape.getLocation().x += dx;
                            selectedShape.getLocation().y += dy;
                            selectedShape.setWidth(width - dx);
                            selectedShape.setHeight(height - dy);
                        } else if(selectedShape.getSelectionMode() == SelectionMode.LowerLeft) {
                            selectedShape.getLocation().x += dx;
                            selectedShape.setWidth(width -dx);
                            selectedShape.setHeight(height + dy);
                        } else if(selectedShape.getSelectionMode() == SelectionMode.UpperRight){
                            selectedShape.getLocation().y += dy;
                            selectedShape.setWidth(width + dx);
                            selectedShape.setHeight(height - dy);
                        } else if(selectedShape.getSelectionMode() == SelectionMode.LowerRight){
                            selectedShape.setWidth(width + dx);
                            selectedShape.setHeight(height+ dy);
                        } else if(selectedShape.getSelectionMode() == SelectionMode.MiddleRight){
                            selectedShape.setWidth(width + dx);
                        } else if(selectedShape.getSelectionMode() == SelectionMode.MiddleLeft){
                            selectedShape.setWidth(width - dx);
                            selectedShape.getLocation().x += dx;
                        } else if(selectedShape.getSelectionMode() == SelectionMode.MiddleTop) {
                            selectedShape.setHeight(height - dy);
                            selectedShape.getLocation().y += dy;
                        } else if(selectedShape.getSelectionMode() == SelectionMode.MiddleBottom){
                            selectedShape.setHeight(height + dy);
                        }
                        drawingView.repaint();
                    }
                }
                start = end;
            }
            else {
                currentShape.getRendererService().render(drawingView.getGraphics(), currentShape, true);
                appService.scale(currentShape, end);
                currentShape.getRendererService().render(drawingView.getGraphics(), currentShape, true);
            }
       }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        drawingStatusPanel.setPoint(e.getPoint());
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
//        if (e.getKeyCode() == KeyEvent.VK_DELETE) {
//          appService.delete();
//        }
    }


    @Override
    public void keyReleased(KeyEvent e) {

    }
}
