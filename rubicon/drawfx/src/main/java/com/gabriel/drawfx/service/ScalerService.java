package com.gabriel.drawfx.service;

import com.gabriel.drawfx.Handle;
import com.gabriel.drawfx.model.Shape;

import java.awt.*;

public final class ScalerService {
    public static void scaleWithHandle(Shape shape, Point originalLocation, int originalWidth,
                                       int originalHeight, Point mousePoint, Handle handle) {

        int left = originalLocation.x;
        int top = originalLocation.y;
        int right = originalLocation.x + originalWidth;
        int bottom = originalLocation.y + originalHeight;

        if (originalWidth < 0) {
            left = originalLocation.x + originalWidth;
            right = originalLocation.x;
        }
        if (originalHeight < 0) {
            top = originalLocation.y + originalHeight;
            bottom = originalLocation.y;
        }

        int newLeft = left;
        int newTop = top;
        int newRight = right;
        int newBottom = bottom;

        switch (handle) {
            case NW:
                newLeft = mousePoint.x;
                newTop = mousePoint.y;
                break;
            case N:
                newTop = mousePoint.y;
                break;
            case NE:
                newRight = mousePoint.x;
                newTop = mousePoint.y;
                break;
            case E:
                newRight = mousePoint.x;
                break;
            case SE:
                newRight = mousePoint.x;
                newBottom = mousePoint.y;
                break;
            case S:
                newBottom = mousePoint.y;
                break;
            case SW:
                newLeft = mousePoint.x;
                newBottom = mousePoint.y;
                break;
            case W:
                newLeft = mousePoint.x;
                break;
        }

        shape.setLocation(new Point(newLeft, newTop));
        shape.setWidth(newRight - newLeft);
        shape.setHeight(newBottom - newTop);
    }
}
