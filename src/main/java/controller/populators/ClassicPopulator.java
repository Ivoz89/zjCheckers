package controller.populators;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import controller.ai.AIPlayer;
import data.annotations.HumanPlayer;
import data.annotations.CPUPlayer;
import data.annotations.CheckerRows;
import data.api.BoardPopulator;
import data.model.Checker;
import data.model.Field;
import data.model.Player;

import java.util.ArrayList;
import java.util.stream.IntStream;

/**
 * Arranges checkers according to the classic layout
 * Created by Ivo on 06/05/15.
 */
@Singleton
public class ClassicPopulator implements BoardPopulator {

    private final Player firstPlayer;
    private final Player secondPlayer;
    private final int checkerRows;

    @Inject
    public ClassicPopulator(@HumanPlayer Player firstPlayer, @CPUPlayer AIPlayer secondPlayer,
            @CheckerRows int checkerRows) {
        this.checkerRows = checkerRows;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }

    @Override
    public void populateBoard(ArrayList<ArrayList<Field>> fields) {
        IntStream.range(0, checkerRows).forEach(
                row -> IntStream.range(0, fields.get(0).size()).forEach(
                        column -> {
                            if ((row + column) % 2 == 1) {
                                fields.get(row).get(column).setOccupant(new Checker(firstPlayer));
                            }
                        }
                ));
        IntStream.range(fields.size() - checkerRows, fields.size()).forEach(
                row -> IntStream.range(0, fields.get(0).size()).forEach(
                        column -> {
                            if ((row + column) % 2 == 1) {
                                fields.get(row).get(column).setOccupant(new Checker(secondPlayer));
                            }
                        }
                ));
    }
}
