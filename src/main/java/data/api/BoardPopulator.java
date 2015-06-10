package data.api;

import data.model.Field;

import java.util.ArrayList;

/**
 * Fills the board with both players' checkers. Represents a starting formation.
 * Created by Ivo on 06/05/15.
 */
public interface BoardPopulator {
    void populateBoard(ArrayList<ArrayList<Field>> fields);
}
