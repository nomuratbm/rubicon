package com.gabriel.draw.controller;

import com.gabriel.draw.command.MoveShapeCommand;
import com.gabriel.draw.command.ScaleShapeCommand;
import com.gabriel.draw.command.SelectShapeCommand;
import com.gabriel.draw.model.Ellipse;
import com.gabriel.draw.model.Line;
import com.gabriel.draw.model.Rectangle;
import com.gabriel.drawfx.DrawMode;
import com.gabriel.drawfx.Handle;
import com.gabriel.draw.view.DrawingView;
import com.gabriel.drawfx.ShapeMode;
import com.gabriel.drawfx.command.CommandService;
import com.gabriel.drawfx.service.AppService;
import com.gabriel.drawfx.model.Shape;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class DrawingController  implements MouseListener, MouseMotionListener {
    private final DrawingView drawingView;
    private Shape currentShape;
    private final AppService appService;

    private Point start;
    private Point end;
    private Handle activeHandle = Handle.NONE;
    private boolean isDraggingShape = false;
    private Shape scaleTargetShape = null;

    public DrawingController(AppService appService, DrawingView drawingView){
        this.appService = appService;
        this.drawingView = drawingView;
     }
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        start = e.getPoint();
        end = e.getPoint();

        if (appService.getShapeMode() == ShapeMode.Select) {
            handleSelectionMousePressed(e);
        } else {
            handleDrawingMousePressed(e);
        }
    }

    /* @Override
    public void mousePressed(MouseEvent e) {
        Point start;
        if(appService.getDrawMode() == DrawMode.Idle) {
            start = e.getPoint();
            switch (appService.getShapeMode()){
                case Line:  currentShape = new Line(start, start);
                    currentShape.setColor(appService.getColor());
                    currentShape.setFill(appService.getFill());
                    currentShape.getRendererService().render(drawingView.getGraphics(), currentShape,false );
                    appService.setDrawMode(DrawMode.MousePressed);
                    break;
                case Rectangle:
                    currentShape = new Rectangle(start, start);
                    currentShape.setColor(appService.getColor());
                    currentShape.setFill(appService.getFill());
                    currentShape.getRendererService().render(drawingView.getGraphics(), currentShape,false );
                    appService.setDrawMode(DrawMode.MousePressed);
                    break;
                case  Ellipse:
                    currentShape = new Ellipse(start, start);
                    currentShape.setColor(appService.getColor());
                    currentShape.setFill(appService.getFill());
                    currentShape.getRendererService().render(drawingView.getGraphics(), currentShape,false );
                    appService.setDrawMode(DrawMode.MousePressed);
                    break;
                default:
                    return;
            }


        }
    } */

    @Override
    public void mouseReleased(MouseEvent e) {
        if (appService.getDrawMode() == DrawMode.MousePressed) {
            if (appService.getShapeMode() == ShapeMode.Select) {
                handleSelectionMouseReleased(e);
            } else {
                handleDrawingMouseReleased(e);
            }
        }
    }

    /*@Override
    public void mouseReleased(MouseEvent e) {
         if(appService.getDrawMode() == DrawMode.MousePressed){
             end = e.getPoint();
             currentShape.getRendererService().render(drawingView.getGraphics(), currentShape,true );
             appService.scale(currentShape,end);
             currentShape.getRendererService().render(drawingView.getGraphics(), currentShape,false );
             appService.create(currentShape);
             appService.setDrawMode(DrawMode.Idle);
           }
    }*/

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (appService.getDrawMode() == DrawMode.MousePressed) {
            Point currentPoint = e.getPoint();

            if (appService.getShapeMode() == ShapeMode.Select) {
                handleSelectionMouseDragged(currentPoint);
            } else {
                handleDrawingMouseDragged(currentPoint);
            }

            end = currentPoint;
        }
    }

    /*@Override
    public void mouseDragged(MouseEvent e) {
        if(appService.getDrawMode() == DrawMode.MousePressed) {
                end = e.getPoint();
                currentShape.getRendererService().render(drawingView.getGraphics(), currentShape,true );
                appService.scale(currentShape,end);
                currentShape.getRendererService().render(drawingView.getGraphics(), currentShape,true );
           }
    }*/

    @Override
    public void mouseMoved(MouseEvent e) {
        if (appService.getShapeMode() == ShapeMode.Select) {
            updateCursor(e.getPoint());
        }
    }

    private void handleSelectionMousePressed(MouseEvent e) {
        Point point = e.getPoint();
        List<Shape> selectedShapes = appService.getSelectedShapes();

        if (!selectedShapes.isEmpty()) {
            SelectionRenderer.BoundingBox boundingBox =
                    selectedShapes.size() == 1
                            ? SelectionRenderer.getBoundingBox(selectedShapes.get(0))
                            : SelectionRenderer.getBoundingBox(selectedShapes);

            if (boundingBox != null) {
                activeHandle = boundingBox.getHandleAt(point);

                if (activeHandle != Handle.NONE && selectedShapes.size() == 1) {
                    scaleTargetShape = selectedShapes.get(0);
                    appService.setDrawMode(DrawMode.MousePressed);
                    return;
                } else if (boundingBox.contains(point)) {
                    isDraggingShape = true;
                    appService.setDrawMode(DrawMode.MousePressed);
                    return;
                }
            }
        }

        Shape clickedShape = appService.findShapeAt(point);
        boolean isCtrlPressed = e.isControlDown() || e.isMetaDown();

        SelectShapeCommand selectCommand = new SelectShapeCommand(
                appService, clickedShape, isCtrlPressed
        );
        CommandService.ExecuteCommand(selectCommand);
        appService.repaint();
    }

    private void handleSelectionMouseReleased(MouseEvent e) {
        // Complete scaling operation
        if (activeHandle != Handle.NONE && scaleTargetShape != null) {
            Point newEnd = calculateScalePoint(scaleTargetShape, endPoint, activeHandle);
            ScaleShapeCommand scaleCommand = new ScaleShapeCommand(
                    appService, scaleTargetShape, newEnd
            );
            CommandService.ExecuteCommand(scaleCommand);

            scaleTargetShape = null;
            activeHandle = Handle.NONE;
        }
        // Complete move operation
        else if (isDraggingShape) {
            List<Shape> selectedShapes = appService.getSelectedShapes();
            if (!selectedShapes.isEmpty()) {
                Point delta = new Point(
                        end.x - start.x,
                        end.y - start.y
                );

                // Only create command if there was actual movement
                if (delta.x != 0 || delta.y != 0) {
                    MoveShapeCommand moveCommand = new MoveShapeCommand(
                            appService, selectedShapes, delta
                    );
                    CommandService.ExecuteCommand(moveCommand);
                }
            }
            isDraggingShape = false;
        }

        appService.setDrawMode(DrawMode.Idle);
        appService.repaint();
    }
}
