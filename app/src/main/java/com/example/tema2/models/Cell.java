package com.example.tema2.models;

public class Cell {
    private CellType cellType;

    public Cell(CellType cellType) {
        this.cellType = cellType;
    }

    public CellType getCellType() {
        return cellType;
    }
}
