package com.example.battleship.utils;

import android.app.Activity;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowMetrics;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.example.battleship.models.OnCellBombard;
import com.example.battleship.models.Position;
import com.example.battleship.models.OnCellTypeChange;
import com.example.battleship.models.ScreenMetrics;
import com.example.battleship.views.CellView;

public class BoardGetter {
    private final static int boardSize = 10;
    private final static int boardMarginsDp = 20;

    public static void getBoard(TableLayout boardTL, Activity activity, OnCellTypeChange onCellTypeChange) {
        final ScreenMetrics screenMetrics = getScreenMetrics(activity);
        final int cellViewSize = getCellViewPxSize(activity, screenMetrics);
        for (int y = 0; y < boardSize; y++) {
            TableRow cellsRow = new TableRow(activity);

            for (int x = 0; x < boardSize; x++) {
                CellView cellView = new CellView(activity);

                cellView.setLayoutParams(new TableRow.LayoutParams(cellViewSize, cellViewSize));
                cellView.setPosition(new Position(x, y));
                cellView.setOnClickListener(view -> {
                    CellView cell = (CellView) view;
                    if (cell.isEmpty()) {
                        onCellTypeChange.allocateShip(cell.getPosition());
                    } else {
                        onCellTypeChange.removeShip(cell.getPosition());
                    }
                });

                cellsRow.addView(cellView);
            }
            boardTL.addView(cellsRow);
        }
    }

    public static void getBoard(TableLayout boardTL, Activity activity, OnCellBombard onCellBombard) {
        final ScreenMetrics screenMetrics = getScreenMetrics(activity);
        final int cellViewSize = getCellViewPxSize(activity, screenMetrics);
        for (int y = 0; y < boardSize; y++) {
            TableRow cellsRow = new TableRow(activity);

            for (int x = 0; x < boardSize; x++) {
                CellView cellView = new CellView(activity);

                cellView.setLayoutParams(new TableRow.LayoutParams(cellViewSize, cellViewSize));
                cellView.setPosition(new Position(x, y));
                cellView.setOnClickListener(view -> {
                    CellView cell = (CellView) view;
                    onCellBombard.bombardCell(cell.getPosition());
                });

                cellsRow.addView(cellView);
            }
            boardTL.addView(cellsRow);
        }
    }


    public static void restoreBoard(TableLayout boardTL, Activity activity, Boolean[][] chipPositions) {
        final ScreenMetrics screenMetrics = getScreenMetrics(activity);
        final int cellViewSize = getCellViewPxSize(activity, screenMetrics);
        for (int y = 0; y < boardSize; y++) {
            TableRow cellsRow = new TableRow(activity);

            for (int x = 0; x < boardSize; x++) {
                CellView cellView = new CellView(activity);
                cellView.setLayoutParams(new TableRow.LayoutParams(cellViewSize, cellViewSize));
                cellsRow.addView(cellView);
                if (chipPositions[y][x] != null && chipPositions[y][x]) {
                    cellView.setShip();
                }
            }
            boardTL.addView(cellsRow);
        }
    }

    public static void drawChipOnBoard(Position position, TableLayout boardTL) {
        CellView cellView = getCellViewOnPosition(position, boardTL);
        cellView.setShip();
    }

    public static void removeShipOnBoard(Position position, TableLayout boardTL) {
        CellView cellView = getCellViewOnPosition(position, boardTL);
        cellView.setEmpty();
    }

    public static CellView getCellViewOnPosition(Position position, TableLayout boardTL) {
        TableRow cellsRow = (TableRow) boardTL.getChildAt(position.getY());
        return (CellView) cellsRow.getChildAt(position.getX());
    }

    public static Boolean isCellEmpty(TableLayout boardTL, Position position) {
        return getCellViewOnPosition(position, boardTL).isEmpty();
    }

    public static void setCellMissed(TableLayout boardTL, Position position) {
        getCellViewOnPosition(position, boardTL).setMissed();
    }

    public static void setCellInjured(TableLayout boardTL, Position position) {
        getCellViewOnPosition(position, boardTL).setInjured();
    }

    @SuppressWarnings("deprecation")
    private static ScreenMetrics getScreenMetrics(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowMetrics windowMetrics = activity.getWindowManager().getCurrentWindowMetrics();
            return new ScreenMetrics(windowMetrics.getBounds().width(), windowMetrics.getBounds().height());
        } else {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            return new ScreenMetrics(displayMetrics.widthPixels, displayMetrics.heightPixels);
        }
    }

    private static int getCellViewPxSize(Activity activity, ScreenMetrics screenMetrics) {
        int freeSquareHeightSpace = (screenMetrics.getHeightPx() - convertDpToPx(activity, boardMarginsDp) * 2) / 2;
        int freeSquareWidthSpace = screenMetrics.getWidthPx() - convertDpToPx(activity, boardMarginsDp) * 2;
        return freeSquareHeightSpace < freeSquareWidthSpace ? freeSquareHeightSpace / 10 : freeSquareWidthSpace / 10;
    }

    private static int convertDpToPx(Activity activity, int pxToConvert) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                (float) pxToConvert, activity.getResources().getDisplayMetrics());
    }
}
