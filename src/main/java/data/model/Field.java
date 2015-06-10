package data.model;

import lombok.Data;

/**
 * Represents a field on a game board.
 * Created by Ivo on 06/05/15.
 */
@Data
public class Field {
    private Checker occupant;
    private int rowIndex;
    private int columnIndex;

    /**
     * @param rowIndex
     * @param columnIndex
     */
    public Field(int rowIndex, int columnIndex) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
    }

    private Field() {
    }

    public Checker removeOccupant() {
        Checker checker = occupant;
        occupant = null;
        return checker;
    }

    public boolean isEmpty() {
        return occupant == null;
    }
}
