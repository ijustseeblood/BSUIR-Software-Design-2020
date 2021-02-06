package com.example.battleship.models;

public class Position {
    private int x;
    private int y;

    public Position() {
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }

        if (!(object instanceof Position)) {
            return false;
        }

        Position position = (Position) object;

        return x == position.getX() && y == position.getY();
    }
}
