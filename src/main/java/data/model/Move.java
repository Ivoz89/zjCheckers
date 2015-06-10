package data.model;

import lombok.Data;

/**
 * Represents Player's move within the board.
 */
@Data
public class Move {

    private final Player player;
    private final Field sourceField;
    private final Field destinationField;
    private boolean beating;

    public Move(Player player, Field sourceField, Field destinationField) {
        this.player = player;
        this.sourceField = sourceField;
        this.destinationField = destinationField;
    }
}
