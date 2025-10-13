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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DrawingController implements MouseListener, MouseMotionListener {
    private final DrawingView drawingView;
    private Shape currentShape;
    private final AppService appService;

    private Point start;
    private Point lastDragPoint;
    private Handle activeHandle = Handle.NONE;
    private boolean isDraggingShape = false;
    private Shape scaleTargetShape = null;

    private Map<Shape, Point> moveStartPositions = new HashMap<>();

    private Point scaleOriginalLocation;
    private int scaleOriginalWidth;
    private int scaleOriginalHeight;

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

                    scaleOriginalLocation = new Point(scaleTargetShape.getLocation());
                    scaleOriginalWidth = scaleTargetShape.getWidth();
                    scaleOriginalHeight = scaleTargetShape.getHeight();
                    appService.setDrawMode(DrawMode.MousePressed);
                    return;
                } else if (boundingBox.contains(point)) {
                    isDraggingShape = true;

                    moveStartPositions.clear();
                    for (Shape shape : selectedShapes) {
                        moveStartPositions.put(shape, new Point(shape.getLocation()));
                    }
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
            scaleTargetShape.setLocation(new Point(scaleOriginalLocation));
            scaleTargetShape.setWidth(scaleOriginalWidth);
            scaleTargetShape.setHeight(scaleOriginalHeight);

            Point newEnd = calculateScaleEndPoint(scaleOriginalLocation, scaleOriginalWidth,
                    scaleOriginalHeight, currentPoint, activeHandle);
            appService.scale(scaleTargetShape, newEnd);
            appService.repaint();
        } else if (isDraggingShape) {

            appService.repaint();
        }
    }

    private void handleSelectionMouseReleased(MouseEvent e) {
        if (activeHandle != Handle.NONE && scaleTargetShape != null) {
            Point currentPoint = e.getPoint();

            scaleTargetShape.setLocation(new Point(scaleOriginalLocation));
            scaleTargetShape.setWidth(scaleOriginalWidth);
            scaleTargetShape.setHeight(scaleOriginalHeight);

            Point newEnd = calculateScaleEndPoint(scaleOriginalLocation, scaleOriginalWidth,
                    scaleOriginalHeight, currentPoint, activeHandle);

            ScaleShapeCommand scaleCommand = new ScaleShapeCommand(
                    appService, scaleTargetShape, newEnd
            );
            CommandService.ExecuteCommand(scaleCommand);

            scaleTargetShape = null;
            scaleOriginalLocation = null;
            scaleOriginalWidth = 0;
            scaleOriginalHeight = 0;
            activeHandle = Handle.NONE;
        }
        else if (isDraggingShape) {
            List<Shape> selectedShapes = appService.getSelectedShapes();
            if (!selectedShapes.isEmpty()) {
                Point delta = new Point(
                        e.getPoint().x - start.x,
                        e.getPoint().y - start.y
                );

                if (delta.x != 0 || delta.y != 0) {
                    MoveShapeCommand moveCommand = new MoveShapeCommand(
                            appService, selectedShapes, delta
                    );
                    CommandService.ExecuteCommand(moveCommand);
                }
            }
            isDraggingShape = false;
            moveStartPositions.clear();
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

    private Point calculateScaleEndPoint(Point originalLoc, int originalWidth, int originalHeight,
                                         Point mousePoint, Handle handle) {

        int left = originalLoc.x;
        int top = originalLoc.y;
        int right = originalLoc.x + originalWidth;
        int bottom = originalLoc.y + originalHeight;

        if (originalWidth < 0) {
            left = originalLoc.x + originalWidth;
            right = originalLoc.x;
        }
        if (originalHeight < 0) {
            top = originalLoc.y + originalHeight;
            bottom = originalLoc.y;
        }

        switch (handle) {
            case NW:

                scaleTargetShape.setLocation(mousePoint);
                return new Point(right, bottom);
            case N:

                scaleTargetShape.setLocation(new Point(left, mousePoint.y));
                return new Point(right, bottom);
            case NE:

                scaleTargetShape.setLocation(new Point(left, mousePoint.y));
                return new Point(mousePoint.x, bottom);
            case E:

                return new Point(mousePoint.x, bottom);
            case SE:

                return new Point(mousePoint.x, mousePoint.y);
            case S:

                return new Point(right, mousePoint.y);
            case SW:

                scaleTargetShape.setLocation(new Point(mousePoint.x, top));
                return new Point(right, mousePoint.y);
            case W:

                scaleTargetShape.setLocation(new Point(mousePoint.x, top));
                return new Point(right, bottom);
            default:
                return mousePoint;
        }
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