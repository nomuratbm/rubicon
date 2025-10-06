package com.gabriel.draw.service;

import com.gabriel.drawfx.Handle;
import com.gabriel.drawfx.model.Shape;

import java.awt.*;
import java.util.List;

public class SelectionRendererService {
    private static final int HANDLE_SIZE = 8;
    private static final Color SELECTION_COLOR = new Color(0, 120, 215);
    private static final Color HANDLE_COLOR = Color.WHITE;

    public static class BoundingBox {
        public int x, y, width, height;

        public BoundingBox(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        public Rectangle[] getHandles() {
            Rectangle[] handles = new Rectangle[8];
            int hw = HANDLE_SIZE;
            int hh = HANDLE_SIZE;

            handles[0] = new Rectangle(x - hw/2, y - hh/2, hw, hh); // NW
            handles[1] = new Rectangle(x + width/2 - hw/2, y - hh/2, hw, hh); // N
            handles[2] = new Rectangle(x + width - hw/2, y - hh/2, hw, hh); // NE
            handles[3] = new Rectangle(x + width - hw/2, y + height/2 - hh/2, hw, hh); // E
            handles[4] = new Rectangle(x + width - hw/2, y + height - hh/2, hw, hh); // SE
            handles[5] = new Rectangle(x + width/2 - hw/2, y + height - hh/2, hw, hh); // S
            handles[6] = new Rectangle(x - hw/2, y + height - hh/2, hw, hh); // SW
            handles[7] = new Rectangle(x - hw/2, y + height/2 - hh/2, hw, hh); // W

            return handles;
        }

        public Handle getHandleAt(Point p) {
            Rectangle[] handles = getHandles();
            Handle[] handleTypes = {
                    Handle.NW, Handle.N, Handle.NE, Handle.E,
                    Handle.SE, Handle.S, Handle.SW, Handle.W
            };

            for (int i = 0; i < handles.length; i++) {
                if (handles[i].contains(p)) {
                    return handleTypes[i];
                }
            }
            return Handle.NONE;
        }

        public boolean contains(Point p) {
            return p.x >= x && p.x <= x + width && p.y >= y && p.y <= y + height;
        }
    }

    public static BoundingBox getBoundingBox(Shape shape) {
        int x = shape.getLocation().x;
        int y = shape.getLocation().y;
        int width = shape.getWidth();
        int height = shape.getHeight();

        // Normalize negative dimensions
        if (width < 0) {
            x += width;
            width = -width;
        }
        if (height < 0) {
            y += height;
            height = -height;
        }

        return new BoundingBox(x, y, width, height);
    }

    public static BoundingBox getBoundingBox(List<Shape> shapes) {
        if (shapes.isEmpty()) return null;

        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (Shape shape : shapes) {
            BoundingBox box = getBoundingBox(shape);
            minX = Math.min(minX, box.x);
            minY = Math.min(minY, box.y);
            maxX = Math.max(maxX, box.x + box.width);
            maxY = Math.max(maxY, box.y + box.height);
        }

        return new BoundingBox(minX, minY, maxX - minX, maxY - minY);
    }

    public static void renderSelection(Graphics g, Shape shape) {
        BoundingBox box = getBoundingBox(shape);
        renderBoundingBox(g, box);
    }

    public static void renderSelection(Graphics g, List<Shape> shapes) {
        if (shapes.isEmpty()) return;
        BoundingBox box = getBoundingBox(shapes);
        if (box != null) {
            renderBoundingBox(g, box);
        }
    }

    private static void renderBoundingBox(Graphics g, BoundingBox box) {
        Graphics2D g2d = (Graphics2D) g;
        Stroke oldStroke = g2d.getStroke();
        Color oldColor = g.getColor();

        g2d.setColor(SELECTION_COLOR);
        g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                0, new float[]{5}, 0));
        g2d.drawRect(box.x, box.y, box.width, box.height);

        Rectangle[] handles = box.getHandles();
        for (Rectangle handle : handles) {
            g2d.setColor(HANDLE_COLOR);
            g2d.fillRect(handle.x, handle.y, handle.width, handle.height);
            g2d.setColor(SELECTION_COLOR);
            g2d.setStroke(new BasicStroke(1));
            g2d.drawRect(handle.x, handle.y, handle.width, handle.height);
        }

        g2d.setStroke(oldStroke);
        g2d.setColor(oldColor);
    }
}