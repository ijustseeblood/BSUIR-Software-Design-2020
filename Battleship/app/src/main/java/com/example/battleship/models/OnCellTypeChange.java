package com.example.battleship.models;

public interface OnCellTypeChange {
    void allocateShip(Position position);

    void removeShip(Position position);
}
