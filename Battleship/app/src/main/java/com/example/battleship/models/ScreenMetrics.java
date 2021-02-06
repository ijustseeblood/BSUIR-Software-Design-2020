package com.example.battleship.models;

public class ScreenMetrics {
    private final int widthPx;
    private final int heightPx;

    public ScreenMetrics(int widthPx, int heightPx) {
        this.widthPx = widthPx;
        this.heightPx = heightPx;
    }

    public int getWidthPx() {
        return widthPx;
    }

    public int getHeightPx() {
        return heightPx;
    }
}
