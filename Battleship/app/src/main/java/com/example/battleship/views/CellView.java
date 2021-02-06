package com.example.battleship.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.example.battleship.R;
import com.example.battleship.models.CellType;
import com.example.battleship.models.Position;

public class CellView extends View {
    Paint borderPaint;
    Paint fillPaint;
    private int fillColor;
    private Position position;
    private CellType cellType;

    public CellView(Context context) {
        super(context);
        cellType = CellType.EMPTY;
        initBorderPaint();
        initFillPaint();

    }

    public void setPosition(Position position) {
        this.position = position;
    }

    private void initBorderPaint() {
        borderPaint = new Paint();
        borderPaint.setColor(Color.BLACK);
        borderPaint.setStrokeWidth(10);
        borderPaint.setStyle(Paint.Style.STROKE);
    }

    private void initFillPaint() {
        fillPaint = new Paint();
        fillColor = Color.WHITE;
        fillPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        fillPaint.setColor(fillColor);
        canvas.drawRect(0, getHeight(), getWidth(), 0, fillPaint);
        canvas.drawRect(0, getHeight(), getWidth(), 0, borderPaint);
    }

    public void setEmpty() {
        fillColor = Color.WHITE;
        cellType = CellType.EMPTY;
        invalidate();
    }

    public void setMissed() {
        fillColor = getContext().getResources().getColor(R.color.ship_missed);
        cellType = CellType.MISSED;
        invalidate();
    }

    public void setInjured() {
        fillColor = Color.RED;
        cellType = CellType.MISSED;
        invalidate();
    }

    public void setShip() {
        fillColor = Color.GREEN;
        cellType = CellType.SHIP;
        invalidate();
    }

    public CellType getCellType() {
        return cellType;
    }

    public boolean isEmpty() {
        return cellType == CellType.EMPTY;
    }

    public Position getPosition() {
        return position;
    }

}
