package data.api;

import data.exceptions.InvalidMoveException;
import data.model.Move;

/**
 * Represents an element of the move processing chain of responsibility.
 * Created by Ivo on 06/05/15.
 */
public interface MoveProcessor {

    /**
     * Performs a part of move execution
     * @param move
     * @throws InvalidMoveException
     */
    void process(Move move) throws InvalidMoveException;
}
