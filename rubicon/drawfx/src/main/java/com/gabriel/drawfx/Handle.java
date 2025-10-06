package com.gabriel.drawfx;

import java.awt.*;

public enum Handle   {
    NONE(Cursor.DEFAULT_CURSOR),
    NW(Cursor.NW_RESIZE_CURSOR),
    N(Cursor.N_RESIZE_CURSOR),
    NE(Cursor.NE_RESIZE_CURSOR),
    E(Cursor.E_RESIZE_CURSOR),
    SE(Cursor.SE_RESIZE_CURSOR),
    S(Cursor.S_RESIZE_CURSOR),
    SW(Cursor.SW_RESIZE_CURSOR),
    W(Cursor.W_RESIZE_CURSOR);

    private final int cursorType;

    Handle(int cursorType) {
        this.cursorType = cursorType;
    }

    public Cursor getCursor() {
        return Cursor.getPredefinedCursor(cursorType);
    }
}