package com.gabriel.draw.controller;

import com.gabriel.draw.command.AddShapeCommand;
import com.gabriel.draw.command.MoveShapeCommand;
import com.gabriel.draw.command.ScaleShapeCommand;
import com.gabriel.draw.command.SelectShapeCommand;
import com.gabriel.draw.model.Ellipse;
import com.gabriel.draw.model.Line;
import com.gabriel.draw.model.Rectangle;
import com.gabriel.draw.service.SelectionRendererService;
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
import java.util.List;

public class DrawingController implements MouseListener, MouseMotionListener {
    private final DrawingView drawingView;
    private Shape currentShape;
    private final AppService appService;

    private Point start;
    private Point lastDragPoint;
    private Handle activeHandle = Handle.NONE;
    private boolean isDraggingShape = false;
    private Shape scaleTargetShape = null;
    private Point scaleStartPoint = null;

    public DrawingController(AppService appService, DrawingView drawingView) {
        this.appService = appService;
        this.drawingView = drawingView;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        start = e.getPoint();
        lastDragPoint = e.getPoint();

        if (appService.getShapeMode() == ShapeMode.Select) {
            handleSelectionMousePressed(e);
        } else {
            handleDrawingMousePressed(e);
        }
    }

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

            lastDragPoint = currentPoint;
        }
    }

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
            SelectionRendererService.BoundingBox boundingBox =
                    selectedShapes.size() == 1
                            ? SelectionRendererService.getBoundingBox(selectedShapes.get(0))
                            : SelectionRendererService.getBoundingBox(selectedShapes);

            if (boundingBox != null) {
                activeHandle = boundingBox.getHandleAt(point);

                if (activeHandle != Handle.NONE && selectedShapes.size() == 1) {
                    scaleTargetShape = selectedShapes.get(0);
                    scaleStartPoint = new Point(scaleTargetShape.getLocation());
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

    private void handleSelectionMouseDragged(Point currentPoint) {
        if (activeHandle != Handle.NONE && scaleTargetShape != null) {
            Point newEnd = calculateScalePoint(scaleTargetShape, start, currentPoint, activeHandle);
            appService.scale(scaleTargetShape, newEnd);
            appService.repaint();
        } else if (isDraggingShape) {
            List<Shape> selectedShapes = appService.getSelectedShapes();
            Point delta = new Point(currentPoint.x - lastDragPoint.x, currentPoint.y - lastDragPoint.y);

            for (Shape shape : selectedShapes) {
                Point currentLoc = shape.getLocation();
                appService.move(shape, new Point(currentLoc.x + delta.x, currentLoc.y + delta.y));
            }
            appService.repaint();
        }
    }

    private void handleSelectionMouseReleased(MouseEvent e) {
        if (activeHandle != Handle.NONE && scaleTargetShape != null) {
            Point currentPoint = e.getPoint();
            Point newEnd = calculateScalePoint(scaleTargetShape, start, currentPoint, activeHandle);
            ScaleShapeCommand scaleCommand = new ScaleShapeCommand(
                    appService, scaleTargetShape, newEnd
            );
            CommandService.ExecuteCommand(scaleCommand);

            scaleTargetShape = null;
            scaleStartPoint = null;
            activeHandle = Handle.NONE;
        }
        else if (isDraggingShape) {
            List<Shape> selectedShapes = appService.getSelectedShapes();
            if (!selectedShapes.isEmpty()) {
                Point delta = new Point(
                        lastDragPoint.x - start.x,
                        lastDragPoint.y - start.y
                );

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

    private void updateCursor(Point point) {
        List<Shape> selectedShapes = appService.getSelectedShapes();

        if (!selectedShapes.isEmpty()) {
            SelectionRendererService.BoundingBox boundingBox =
                    selectedShapes.size() == 1
                            ? SelectionRendererService.getBoundingBox(selectedShapes.get(0))
                            : SelectionRendererService.getBoundingBox(selectedShapes);

            if (boundingBox != null) {
                Handle handle = boundingBox.getHandleAt(point);

                if (handle != Handle.NONE && selectedShapes.size() == 1) {
                    drawingView.setCursor(handle.getCursor());
                    return;
                } else if (boundingBox.contains(point)) {
                    drawingView.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                    return;
                }
            }
        }

        drawingView.setCursor(Cursor.getDefaultCursor());
    }

    private Point calculateScalePoint(Shape shape, Point startPoint, Point currentPoint, Handle handle) {
        Point location = shape.getLocation();
        int width = shape.getWidth();
        int height = shape.getHeight();

        int x = location.x;
        int y = location.y;

        int left = x;
        int top = y;
        int right = x + width;
        int bottom = y + height;

        if (width < 0) {
            left = x + width;
            right = x;
        }
        if (height < 0) {
            top = y + height;
            bottom = y;
        }

        return switch (handle) {
            case NW -> new Point(right, bottom);
            case N -> new Point(right, bottom);
            case NE -> new Point(left, bottom);
            case E -> new Point(left, bottom);
            case SE -> new Point(left, top);
            case S -> new Point(right, top);
            case SW -> new Point(right, top);
            case W -> new Point(right, bottom);
            default -> currentPoint;
        };
    }

    private void handleDrawingMousePressed(MouseEvent e) {
        Point startPoint = e.getPoint();

        switch (appService.getShapeMode()) {
            case Line:
                currentShape = new Line(startPoint, startPoint);
                break;
            case Rectangle:
                currentShape = new Rectangle(startPoint, startPoint);
                break;
            case Ellipse:
                currentShape = new Ellipse(startPoint, startPoint);
                break;
            default:
                return;
        }

        currentShape.setColor(appService.getColor());
        currentShape.setFill(appService.getFill());
        currentShape.getRendererService().render(drawingView.getGraphics(), currentShape, true);

        appService.setDrawMode(DrawMode.MousePressed);
    }

    private void handleDrawingMouseDragged(Point currentPoint) {
        if (currentShape != null) {
            currentShape.getRendererService().render(drawingView.getGraphics(), currentShape, true);
            appService.scale(currentShape, currentPoint);
            currentShape.getRendererService().render(drawingView.getGraphics(), currentShape, true);
        }
    }

    private void handleDrawingMouseReleased(MouseEvent e) {
        if (currentShape != null) {
            currentShape.getRendererService().render(drawingView.getGraphics(), currentShape, true);

            Point endPoint = e.getPoint();
            appService.scale(currentShape, endPoint);

            AddShapeCommand addCommand = new AddShapeCommand(appService, currentShape);
            CommandService.ExecuteCommand(addCommand);

            currentShape = null;
            appService.setDrawMode(DrawMode.Idle);
            appService.repaint();
        }
    }
}